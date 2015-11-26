package ro.tamadawines.persistence.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ro.tamadawines.persistence.model.Counter;

import java.util.List;

public class CounterDao extends AbstractDAO<Counter> {

    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public CounterDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Counter createOrUpdate(Counter counter) {
        return persist(counter);
    }

    public List<Counter> getAll() {
        return list(namedQuery("Counter.findAll"));
    }

    public Counter getByName(String name) {
        return uniqueResult(namedQuery("Counter.findByName").setParameter("nm", name));
    }

    public void increment(String name) {
        namedQuery("Counter.increment").setParameter("nm", name).executeUpdate();
    }

}
