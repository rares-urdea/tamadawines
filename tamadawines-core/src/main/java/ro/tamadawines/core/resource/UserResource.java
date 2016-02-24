package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.persistence.dao.UserDao;
import ro.tamadawines.persistence.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User controller
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces("application/json")
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(userDao.findAll()).build();
    }

    @GET
    @UnitOfWork
    @Path("/fn")
    public Response getByFirstName(@QueryParam("firstName") String firstName) {
        return Response.status(Response.Status.OK).entity(userDao.findByFirstName(firstName)).build();
    }

    @GET
    @UnitOfWork
    @Path("/ln")
    public Response getByLastName(@QueryParam("lastName") String lastName) {
        return Response.status(Response.Status.OK).entity(userDao.findByFirstName(lastName)).build();
    }

    @POST
    @Path("/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response create(User user) {
        return Response.status(Response.Status.OK).entity(userDao.createUser(user)).build();
    }
}
