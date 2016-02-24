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
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.model.Message;
import ro.tamadawines.core.model.MessageWrapper;
import ro.tamadawines.core.model.SellResponseWrapper;
import ro.tamadawines.core.service.S3UploadService;
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.model.Counter;
import ro.tamadawines.persistence.model.Product;

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
    public Response getAllProducts() {
        counterDao.increment(Counter.COUNTER_NAME.LISTINGS.string());
        Long count = counterDao.getByName(Counter.COUNTER_NAME.LISTINGS.string()).getValue();
        if (count % 10 == 0) {
            LOGGER.info("List call count at: {}", count);
        }
        return Response.status(Response.Status.OK).entity(productDao.findAll()).build();
    }

    @GET
    @UnitOfWork
    @Path("/getByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductByName(@QueryParam("productName") String productName) {
        return Response.status(Response.Status.OK).entity(productDao.findByName(productName)).build();
    }

    @POST
    @Path("/addProduct")
    @UnitOfWork
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response addProduct(
            @FormDataParam("product") FormDataBodyPart jsonBodyPart,
            @FormDataParam("image") FormDataBodyPart imageBodyPart) throws WebApplicationException {

        if (imageBodyPart == null || jsonBodyPart == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity(new MessageWrapper(Message.JSON_AND_IMAGE_REQUIRED))
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
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new MessageWrapper(Message.IMAGE_UPLOAD_FAILED)).build();
            }
        }

        Product newEntity = productDao.createOrUpdate(product);
        return Response.status(Response.Status.OK).entity(newEntity).build();
    }

    @POST
    @Path("/updateProduct")
    @UnitOfWork
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response updateProduct(
            @FormDataParam("product") FormDataBodyPart jsonBodyPart,
            @FormDataParam("image") FormDataBodyPart imageBodyPart) {
        return addProduct(jsonBodyPart, imageBodyPart);
    }

    @POST
    @Path("/deleteProduct")
    @UnitOfWork
    public Response removeProduct(Product product) {
        LOGGER.warn("Removing product: {}", product);
        if (productDao.deleteProduct(product)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/sellProducts")
    @UnitOfWork
    public Response sellProducts(ShoppingOrder shoppingOrder) {
        LOGGER.debug("Entered sellProducts with shoppingOrder: {}", shoppingOrder);

        Long count = counterDao.getByName(Counter.COUNTER_NAME.SELL_SUCCESS.string()).getValue();
        if (count % 10 == 0) {
            LOGGER.info("Successful SELL count at: {}", count);
        }

        SellResponseWrapper sellResponseWrapper = new SellResponseWrapper();
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

        if (hasUnavailable || availChange) {
            if (hasUnavailable) {
                sellResponseWrapper.setVerboseMessage(Message.PRODUCT_NOT_FOUND.getValue());
            } else {
                sellResponseWrapper.setVerboseMessage(Message.AVAILABILITY_CHANGE.getValue());
            }
            sellResponseWrapper.setProducts(unavailableProducts);
            counterDao.increment(Counter.COUNTER_NAME.SELL_FAILURE.string());
            return Response.status(Response.Status.CONFLICT).entity(sellResponseWrapper).build();
        }

        // Assuming everything went well, sell the products.
        for (ProductDto p : shoppingOrder.getProducts()) {
            Product current = productDao.findById(p.getId()).get();
            current.setStock(current.getStock() - p.getQuantity());
            productDao.createOrUpdate(current);
        }

        sellResponseWrapper.setVerboseMessage(Message.SUCCESS.getValue());
        counterDao.increment(Counter.COUNTER_NAME.SELL_SUCCESS.string());
        LOGGER.info("SELL successful, exiting with response: {}", sellResponseWrapper);
        return Response.status(Response.Status.OK).entity(sellResponseWrapper).build();
    }
}
