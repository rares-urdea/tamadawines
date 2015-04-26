package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.persistence.dao.UserDAO;
import ro.tamadawines.persistence.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User controller
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDAO userDao;

    public UserResource(UserDAO userDao) {
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces("application/json")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GET
    @UnitOfWork
    @Path("/fn")
    public User getUserByFirstName(@QueryParam("firstName") String firstName) {
        return userDao.findByFirstName(firstName);
    }

    @GET
    @UnitOfWork
    @Path("/ln")
    public User getUserByLastName(@QueryParam("lastName") String lastName) {
        return userDao.findByFirstName(lastName);
    }

    @POST
    @Path("/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public User createUser(User user) {
        return userDao.createUser(user);
    }
}
