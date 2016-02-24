package ro.tamadawines.persistence.dao;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ro.tamadawines.persistence.model.Subscriber;

import java.util.List;

public class SubscriberDao extends AbstractDAO<Subscriber> {

    public SubscriberDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Subscriber> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    public Subscriber createUser(Subscriber subscriber) {
        return persist(subscriber);
    }

    public List<Subscriber> findAll() {
        return list(namedQuery("Subscriber.findAll"));
    }

    public Subscriber findByEmail(String email) {
        return uniqueResult(namedQuery("Subscriber.findByEmail").setParameter("email", email));
    }
}
