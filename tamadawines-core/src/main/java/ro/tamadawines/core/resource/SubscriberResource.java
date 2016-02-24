package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.core.model.Message;
import ro.tamadawines.core.model.MessageWrapper;
import ro.tamadawines.persistence.dao.SubscriberDao;
import ro.tamadawines.persistence.model.Subscriber;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Subscriber Resource
 */
@Path("/sub")
@Produces(MediaType.APPLICATION_JSON)
public class SubscriberResource {

    private SubscriberDao subscriberDao;

    public SubscriberResource(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces("application/json")
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(subscriberDao.findAll()).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response create(Subscriber subscriber) {
        if (subscriberDao.findByEmail(subscriber.getEmailAddress()) != null) {
            return Response.status(Response.Status.OK).entity(new MessageWrapper(Message.ALREADY_SUBSCRIBED)).build();
        }
        subscriberDao.createUser(subscriber);
        return Response.status(Response.Status.OK).entity(subscriberDao.findByEmail(subscriber.getEmailAddress())).build();
    }
}
