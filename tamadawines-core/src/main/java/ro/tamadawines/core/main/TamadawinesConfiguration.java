package ro.tamadawines.core.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Tamadawines configuration class
 */
public class TamadawinesConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    @Valid
    @NotNull
    private EmailStuff emailStuff = new EmailStuff();

    @JsonProperty
    @Valid
    @NotNull
    private Images images = new Images();

    @JsonProperty
    @Valid
    @NotNull
    private AwsCredentials awsCredentials = new AwsCredentials();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    public EmailStuff getEmailStuff() {
        return this.emailStuff;
    }

    public Images getImages() {
        return images;
    }

    public AwsCredentials getAwsCredentials() {
        return awsCredentials;
    }

    public static class EmailStuff {

        @NotNull
        @JsonProperty
        private String adminAddress;

        @NotNull
        @JsonProperty
        private String gmailUserAccount;

        @NotNull
        @JsonProperty
        private String gmailPassword;

        public String getAdminAddress() {
            return adminAddress;
        }

        public String getGmailUserAccount() {
            return gmailUserAccount;
        }

        public String getGmailPassword() {
            return gmailPassword;
        }
    }

    public static class Images {

        @NotNull
        @JsonProperty
        private String bucket;

        public String getBucket() {
            return bucket;
        }
    }

    public static class AwsCredentials {

        @NotNull
        @JsonProperty
        private String accessKey;

        @NotNull
        @JsonProperty
        private String secretKey;

        public String getAccessKey() {
            return accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }
    }
}
