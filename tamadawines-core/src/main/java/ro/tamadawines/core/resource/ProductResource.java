package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User controller
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private ProductDao productDao;

    public ProductResource(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
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
        return productDao.createProduct(product);
    }
}
