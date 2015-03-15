package ro.tamadawines.core.main;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ro.tamadawines.core.resource.UserResource;
import ro.tamadawines.persistence.dao.UserDAO;
import ro.tamadawines.persistence.model.Address;
import ro.tamadawines.persistence.model.User;

/**
 * Application Main Class.
 */
public class TamadawinesApplication extends Application<TamadawinesConfiguration> {
    public static void main(String[] args) throws Exception{
        new TamadawinesApplication().run(args);
    }

    private final HibernateBundle<TamadawinesConfiguration> hibernateBundle =
            new HibernateBundle<TamadawinesConfiguration>(User.class, Address.class) {
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
        environment.jersey().register(userDAO);
        environment.jersey().register(new UserResource(userDAO));
    }
}
