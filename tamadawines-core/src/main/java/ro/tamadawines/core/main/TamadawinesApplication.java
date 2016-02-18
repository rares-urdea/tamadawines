package ro.tamadawines.core.main;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import ro.tamadawines.core.resource.EmailResource;
import ro.tamadawines.core.resource.ProductResource;
import ro.tamadawines.core.resource.UserResource;
import ro.tamadawines.core.service.EmailService;
import ro.tamadawines.persistence.dao.CounterDao;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.dao.UserDao;
import ro.tamadawines.persistence.model.Address;
import ro.tamadawines.persistence.model.Counter;
import ro.tamadawines.persistence.model.Image;
import ro.tamadawines.persistence.model.Product;
import ro.tamadawines.persistence.model.User;

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
            new HibernateBundle<TamadawinesConfiguration>(User.class, Address.class, Product.class, Counter.class, Image.class) {
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
        UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        ProductDao productDao = new ProductDao(hibernateBundle.getSessionFactory());
        CounterDao counterDao = new CounterDao(hibernateBundle.getSessionFactory());

        environment.jersey().register(productDao);
        environment.jersey().register(new ProductResource(productDao, counterDao, tamadawinesConfiguration));

        environment.jersey().register(userDao);
        environment.jersey().register(new UserResource(userDao));

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
