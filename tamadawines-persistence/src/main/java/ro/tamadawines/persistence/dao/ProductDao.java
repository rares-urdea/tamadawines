package ro.tamadawines.persistence.dao;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.tamadawines.persistence.model.Product;

import java.util.List;

/**
 *
 */
public class ProductDao extends AbstractDAO<Product> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

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
        LOGGER.debug("Entered createProduct with target object: {}", product);
        return persist(product);
    }

    public List<Product> findAll() {
        return list(namedQuery("Product.findAll"));
    }

    public Product findByName(String productName) {
        return uniqueResult(namedQuery("Product.findByName").setParameter("nm", productName));
    }

    public Product updateProduct(Product product) {
        LOGGER.debug("Entered updateProduct with target object: {}", product);
        return persist(product);
    }

    public boolean deleteProduct(Product product) {
        LOGGER.debug("Entered deleteProduct with target object: {}", product);
        Optional<Product> productOptional = findById(product.getId());
        if (productOptional.equals(Optional.<Product>absent())) {
            return false;
        } else {
            Query query = namedQuery("Product.delete").setParameter("id", product.getId());
            query.executeUpdate();
            LOGGER.debug("Executed Product.delete query");
            return true;
        }
    }
}
