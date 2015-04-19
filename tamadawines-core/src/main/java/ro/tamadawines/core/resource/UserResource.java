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

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @UnitOfWork
    @Path("/getAll")
    @Produces("application/json")
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @GET
    @UnitOfWork
    @Path("/fn")
    public User getUserByFirstName(@QueryParam("firstName") String firstName) {
        return userDAO.findByFirstName(firstName);
    }

    @GET
    @UnitOfWork
    @Path("/ln")
    public User getUserByLastName(@QueryParam("lastName") String lastName) {
        return userDAO.findByFirstName(lastName);
    }

}
