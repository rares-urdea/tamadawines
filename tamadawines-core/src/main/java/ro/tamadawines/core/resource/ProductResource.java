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
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Counter;
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

    private CounterDao counterDao;

    public ProductResource(ProductDao productDao, CounterDao counterDao) {
        this.productDao = productDao;
        this.counterDao = counterDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        counterDao.increment(Counter.COUNTER_NAME.LISTINGS.string());
        Long count = counterDao.getByName(Counter.COUNTER_NAME.LISTINGS.string()).getValue();
        if (count % 10 == 0) {
            LOGGER.info("List call count at: {}", count);
        }
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
        LOGGER.info("Adding product: {}", product);
        return productDao.createOrUpdate(product);
    }

    @POST
    @Path("/updateProduct")
    @UnitOfWork
    public Product updateProduct(Product product) {
        LOGGER.info("Updating product: {}", product);
        return productDao.createOrUpdate(product);
    }

    @POST
    @Path("/deleteProduct")
    @UnitOfWork
    public CrudResponse removeProduct(Product product) {
        LOGGER.warn("Removing product: {}", product);
        if (productDao.deleteProduct(product)) {
            return new CrudResponse(CrudStatus.SUCCESS);
        } else {
            return new CrudResponse(CrudStatus.FAILURE);
        }
    }

    @POST
    @Path("/sellProducts")
    @UnitOfWork
    public SellResponse sellProducts(ShoppingOrder shoppingOrder) {
        LOGGER.debug("Entered sellProducts with shoppingOrder: {}", shoppingOrder);

        Long count = counterDao.getByName(Counter.COUNTER_NAME.SELL_SUCCESS.string()).getValue();
        if (count % 10 == 0) {
            LOGGER.info("Successful SELL count at: {}", count);
        }

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
            counterDao.increment(Counter.COUNTER_NAME.SELL_FAILURE.string());
            LOGGER.warn("Some products were unavailable, exiting with response: {}", sellResponse);
            return sellResponse;
        } else if (availChange) {
            sellResponse = SellResponseFactory.buildAvailChangeResponse(unavailableProducts);
            counterDao.increment(Counter.COUNTER_NAME.SELL_FAILURE.string());
            LOGGER.warn("Avail has changed for some products, exiting with response: {}", sellResponse);
            return sellResponse;
        }

        // Assuming everything went well, sell the products.
        for (ProductDto p : shoppingOrder.getProducts()) {
            Product current = productDao.findById(p.getId()).get();
            current.setStock(current.getStock() - p.getQuantity());
            productDao.createOrUpdate(current);
        }

        sellResponse = SellResponseFactory.buildSuccessResponse(shoppingOrder.getProducts());
        counterDao.increment(Counter.COUNTER_NAME.SELL_SUCCESS.string());
        LOGGER.info("SELL successful, exiting with response: {}", sellResponse);
        return sellResponse;
    }
}
