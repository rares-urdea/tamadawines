package ro.tamadawines.persistence.dao;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ro.tamadawines.persistence.model.Product;

/**
 *
 */
public class ProductDao extends AbstractDAO<Product>{
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Product> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    public Product createProduct(Product product) {
        return persist(product);
    }


}
