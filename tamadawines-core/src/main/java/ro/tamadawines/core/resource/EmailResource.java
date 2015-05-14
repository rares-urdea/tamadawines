package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.service.EmailService;

import javax.mail.MessagingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Email controller
 */
@Path("/mail")
@Produces(MediaType.APPLICATION_JSON)
public class EmailResource {

    private EmailService emailService;
    private TamadawinesConfiguration configuration;

    public EmailResource(EmailService emailService, TamadawinesConfiguration configuration) {
        this.emailService = emailService;
        this.configuration = configuration;
    }

    @GET
    @UnitOfWork
    @Path("/contactForm")
    public String sendContactUsEmail(@QueryParam("senderEmail") String senderEmail,
                                   @QueryParam("senderName") String senderName,
                                   @QueryParam("body") String body) {

        String adminAddress = configuration.getEmailStuff().getAdminAddress();
        String subject = "Contact form received from: " + senderName;

        try {
            return emailService.sendEmail(adminAddress, adminAddress, senderEmail, subject, body);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
