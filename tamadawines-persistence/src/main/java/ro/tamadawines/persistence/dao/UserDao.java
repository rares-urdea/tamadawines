package ro.tamadawines.persistence.dao;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ro.tamadawines.persistence.model.User;

import java.util.List;

/**
 *
 */
public class UserDao extends AbstractDAO<User> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    public User createUser(User user) {
        return persist(user);
    }

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public User findByFirstName(String firstName) {
        return uniqueResult(namedQuery("User.findByFirstName").setParameter("fn", firstName));
    }

    public User findByLastName(String lastName) {
        return uniqueResult(namedQuery("User.findByFirstName").setParameter("ln", lastName));
    }
}
