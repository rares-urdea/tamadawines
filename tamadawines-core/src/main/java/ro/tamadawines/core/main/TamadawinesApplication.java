package ro.tamadawines.core.main;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ro.tamadawines.core.resource.ProductResource;
import ro.tamadawines.core.resource.UserResource;
import ro.tamadawines.persistence.dao.ProductDao;
import ro.tamadawines.persistence.dao.UserDAO;
import ro.tamadawines.persistence.model.Address;
import ro.tamadawines.persistence.model.Product;
import ro.tamadawines.persistence.model.User;

/**
 * Application Main Class.
 */
public class TamadawinesApplication extends Application<TamadawinesConfiguration> {
    public static void main(String[] args) throws Exception{
        new TamadawinesApplication().run(args);
    }

    private final HibernateBundle<TamadawinesConfiguration> hibernateBundle =
            new HibernateBundle<TamadawinesConfiguration>(User.class, Address.class, Product.class) {
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
    }

    @Override
    public String getName() {
        return "tamadawines-core";
    }

    @Override
    public void run(TamadawinesConfiguration tamadawinesConfiguration, Environment environment) throws Exception {
        UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        ProductDao productDao = new ProductDao(hibernateBundle.getSessionFactory());
//        SimpleAuthenticator simpleAuthenticator = new SimpleAuthenticator();
//        environment.jersey().register(new BasicAuthProvider<>(simpleAuthenticator, "Basic"));
        environment.jersey().register(productDao);
        environment.jersey().register(new ProductResource(productDao));

        environment.jersey().register(userDAO);
        environment.jersey().register(new UserResource(userDAO));
    }
}
