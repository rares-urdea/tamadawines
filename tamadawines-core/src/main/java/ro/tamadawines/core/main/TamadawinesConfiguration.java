package ro.tamadawines.core.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by axes on 2/22/2015.
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

        @NotNull
        @JsonProperty
        private String baseUrl;

        public String getBucket() {
            return bucket;
        }

        public String getBaseUrl() {
            return baseUrl;
        }
    }
}
