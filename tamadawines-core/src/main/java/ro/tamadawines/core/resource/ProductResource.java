package ro.tamadawines.core.resource;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.tamadawines.core.dto.ProductDto;
import ro.tamadawines.core.dto.ShoppingOrder;
import ro.tamadawines.core.factory.SellResponseFactory;
import ro.tamadawines.core.status.model.CrudResponse;
import ro.tamadawines.core.status.model.CrudStatus;
import ro.tamadawines.core.status.model.SellResponse;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * User controller
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    private ProductDao productDao;

    public ProductResource(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        LOGGER.info("Listing all products");
        return productDao.findAll();
    }

    @GET
    @UnitOfWork
    @Path("/getByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductByName(@QueryParam("productName") String productName) {
        return productDao.findByName(productName);
    }

    @POST
    @Path("/addProduct")
    @UnitOfWork
    public Product addProduct(Product product) {
        LOGGER.debug("Entered addProduct with target product: {}", product);
        return productDao.createProduct(product);
    }

    @POST
    @Path("/updateProduct")
    @UnitOfWork
    public Product updateProduct(Product product) {
        LOGGER.debug("Entered updateProduct with target product: {}", product);
        return productDao.updateProduct(product);
    }

    @POST
    @Path("/deleteProduct")
    @UnitOfWork
    public CrudResponse removeProduct(Product product) {
        LOGGER.debug("Entered removeProduct with target product: {}", product);
        if (productDao.deleteProduct(product)) {
            LOGGER.debug("Success");
            return new CrudResponse(CrudStatus.SUCCESS);
        } else {
            LOGGER.debug("Failure");
            return new CrudResponse(CrudStatus.FAILURE);
        }
    }

    @POST
    @Path("/sellProducts")
    @UnitOfWork
    public SellResponse sellProducts(ShoppingOrder shoppingOrder) {
        LOGGER.debug("Entered sellProducts with shoppingOrder: {}", shoppingOrder);

        SellResponse sellResponse;
        List<ProductDto> unavailableProducts = new ArrayList<>();
        Boolean hasUnavailable = false;
        Boolean availChange = false;

        for (ProductDto p : shoppingOrder.getProducts()) {
            Optional<Product> current = productDao.findById(p.getId());
            if (current.equals(Optional.<Product>absent())) {
                unavailableProducts.add(p);
                hasUnavailable = true;
                LOGGER.warn("Unavailable products found");
            } else if (current.get().getStock() < p.getQuantity()) {
                unavailableProducts.add(new ProductDto(p.getId(), current.get().getName(), current.get().getStock()));
                availChange = true;
                LOGGER.warn("Availability has changed for some products");
            }
        }

        if (hasUnavailable) {
            sellResponse = SellResponseFactory.buildProductsNotFoundResponse(unavailableProducts);
            LOGGER.warn("Some products were unavailable, exiting with response: {}", sellResponse);
            return sellResponse;
        } else if (availChange) {
            sellResponse = SellResponseFactory.buildAvailChangeResponse(unavailableProducts);
            LOGGER.warn("Avail has changed for some products, exiting with response: {}", sellResponse);
            return sellResponse;
        }

        // Assuming everything went well, sell the products.
        for (ProductDto p : shoppingOrder.getProducts()) {
            Product current = productDao.findById(p.getId()).get();
            current.setStock(current.getStock() - p.getQuantity());
            productDao.updateProduct(current);
        }

        sellResponse = SellResponseFactory.buildSuccessResponse(shoppingOrder.getProducts());
        LOGGER.info("SELL successful, exiting with response: {}", sellResponse);
        return sellResponse;
    }
}
