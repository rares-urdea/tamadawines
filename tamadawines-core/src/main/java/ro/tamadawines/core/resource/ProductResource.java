package ro.tamadawines.core.resource;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.tamadawines.core.dto.ProductDto;
import ro.tamadawines.core.dto.ShoppingOrder;
import ro.tamadawines.core.factory.SellResponseFactory;
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.service.S3UploadService;
import ro.tamadawines.core.status.model.CrudResponse;
import ro.tamadawines.core.status.model.CrudStatus;
import ro.tamadawines.core.status.model.SellResponse;
import ro.tamadawines.core.status.model.StatusDescriptor;
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Counter;
import ro.tamadawines.persistence.model.Product;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * User controller
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);
    private static final String IMG_TMP = "img.tmp";

    private ProductDao productDao;
    private CounterDao counterDao;
    private TamadawinesConfiguration configuration;
    private S3UploadService s3UploadService;

    public ProductResource(ProductDao productDao, CounterDao counterDao, TamadawinesConfiguration configuration,
                           S3UploadService s3UploadService) {
        this.productDao = productDao;
        this.counterDao = counterDao;
        this.configuration = configuration;
        this.s3UploadService = s3UploadService;
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
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Product addProduct(
            @FormDataParam("product") FormDataBodyPart jsonBodyPart,
            @FormDataParam("image") FormDataBodyPart imageBodyPart) {

        if (imageBodyPart == null || jsonBodyPart == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                    .entity(new StatusDescriptor("image & product form params are mandatory", 205))
                    .build()
            );
        }

        jsonBodyPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        Product product = jsonBodyPart.getValueAs(Product.class);
        LOGGER.info("Adding new product: {}", product);

        String bucket = configuration.getImages().getBucket();
        String key = imageBodyPart.getContentDisposition().getFileName();
        if (s3UploadService.objectExists(bucket, key)) {
            product.setImageUrl(s3UploadService.getObjectUrl(bucket, key));
            LOGGER.warn("Image \"{}\" already exists in S3", key);
        } else {
            try {
                InputStream inputStream = imageBodyPart.getValueAs(InputStream.class);
                File tempFile = new File(IMG_TMP);
                FileUtils.copyInputStreamToFile(inputStream, tempFile);
                s3UploadService.uploadFileToS3(tempFile, bucket, key);
                product.setImageUrl(s3UploadService.getObjectUrl(bucket, key));
            } catch (IOException e) {
                LOGGER.error("IOException while copying image inputStream to file: {}", e.getMessage());
                return null;
            }
        }

        return productDao.createOrUpdate(product);
    }

    @POST
    @Path("/updateProduct")
    @UnitOfWork
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Product updateProduct(
            @FormDataParam("product") FormDataBodyPart jsonBodyPart,
            @FormDataParam("image") FormDataBodyPart imageBodyPart) {
        return addProduct(jsonBodyPart, imageBodyPart);
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
