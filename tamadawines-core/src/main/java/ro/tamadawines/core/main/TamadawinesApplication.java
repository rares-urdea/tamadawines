package ro.tamadawines.core.main;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import ro.tamadawines.core.resource.EmailResource;
import ro.tamadawines.core.resource.ProductResource;
import ro.tamadawines.core.resource.SubscriberResource;
import ro.tamadawines.core.resource.UserResource;
import ro.tamadawines.core.service.EmailService;
import ro.tamadawines.core.service.S3UploadService;
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.dao.SubscriberDao;
import ro.tamadawines.persistence.dao.UserDao;
import ro.tamadawines.persistence.model.*;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Application Main Class.
 */
public class TamadawinesApplication extends Application<TamadawinesConfiguration> {
    public static void main(String[] args) throws Exception {
        new TamadawinesApplication().run(args);
    }

    private final HibernateBundle<TamadawinesConfiguration> hibernateBundle =
            new HibernateBundle<TamadawinesConfiguration>(User.class, Address.class, Product.class, Counter.class, Subscriber.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(TamadawinesConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<TamadawinesConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<TamadawinesConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TamadawinesConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public String getName() {
        return "tamadawines-core";
    }

    @Override
    public void run(TamadawinesConfiguration tamadawinesConfiguration, Environment environment) throws Exception {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(tamadawinesConfiguration.getAwsCredentials().getAccessKey(),
                tamadawinesConfiguration.getAwsCredentials().getSecretKey());
        AmazonS3Client s3Client = new AmazonS3Client(awsCreds);
        S3UploadService s3UploadService = new S3UploadService(s3Client);

        UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        SubscriberDao subscriberDao = new SubscriberDao(hibernateBundle.getSessionFactory());
        ProductDao productDao = new ProductDao(hibernateBundle.getSessionFactory());
        CounterDao counterDao = new CounterDao(hibernateBundle.getSessionFactory());

        environment.jersey().register(productDao);
        environment.jersey().register(new ProductResource(productDao, counterDao, tamadawinesConfiguration, s3UploadService));

        environment.jersey().register(userDao);
        environment.jersey().register(new UserResource(userDao));

        environment.jersey().register(subscriberDao);
        environment.jersey().register(new SubscriberResource(subscriberDao));

        environment.jersey().register(new EmailResource(new EmailService(tamadawinesConfiguration),
                tamadawinesConfiguration));
        configureCors(environment);
    }

    /**
     * Configure CORS filter for cross origin requests
     *
     * @param environment The {@code Environment}
     */
    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
