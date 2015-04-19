package ro.tamadawines.persistence.dao;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ro.tamadawines.persistence.model.Product;

import java.util.List;

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

    public List<Product> findAll() {
        return list(namedQuery("Product.findAll"));
    }

    public Product findByName(String productName) {
        return uniqueResult(namedQuery("Product.findByName").setParameter("nm", productName));
    }
}
