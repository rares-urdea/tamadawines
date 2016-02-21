package ro.tamadawines.core.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class S3UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3UploadService.class);
    private static final String CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";

    private AmazonS3Client s3Client;

    public S3UploadService(AmazonS3Client s3Client) {
        this.s3Client = s3Client;
    }

    public PutObjectResult uploadFileToS3(File file, String bucketName, String keyName) {
        PutObjectResult result = null;
        try {
            LOGGER.info("Uploading {} to S3...", keyName);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(CONTENT_TYPE_IMAGE_JPEG);
            objectMetadata.setContentLength(file.length());
            result = s3Client.putObject(new PutObjectRequest(bucketName, keyName, file)
                    .withMetadata(objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException ase) {
            LOGGER.error("Amazon S3 Service exception. Request made it to S3, but was rejected.");
            LOGGER.error("Raw response: {}", ase.getRawResponseContent());
            LOGGER.error("Error Message: {}", ase.getMessage());
            LOGGER.error("HTTP Status Code: {}", ase.getStatusCode());
            LOGGER.error("AWS Error Code: {}", ase.getErrorCode());
            LOGGER.error("Error Type: {}", ase.getErrorType());
            LOGGER.error("Request ID: {}", ase.getRequestId());
        } catch (AmazonClientException ace) {
            LOGGER.error("S3 Client Exception. Application failed to communicate with amazon. " +
                    "This is quite bad considering that we are IN amazon");
        }
        return result;
    }

    public boolean objectExists(String bucket, String key) {
        return s3Client.doesObjectExist(bucket, key);
    }

    public String getObjectUrl(String bucket, String key) {
        return s3Client.getResourceUrl(bucket, key);
    }
}
