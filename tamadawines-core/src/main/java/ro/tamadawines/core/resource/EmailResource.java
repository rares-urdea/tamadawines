package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.service.EmailService;
import ro.tamadawines.core.status.model.Status;
import ro.tamadawines.core.status.model.StatusDescriptor;

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
    public StatusDescriptor sendContactUsEmail(@QueryParam("senderEmail") String senderEmail,
                                   @QueryParam("senderName") String senderName,
                                   @QueryParam("body") String body,
                                   @QueryParam("subject") String subject) {

        String adminAddress = configuration.getEmailStuff().getAdminAddress();

        try {
            if (emailService.sendEmail(adminAddress, adminAddress, senderEmail, subject, body)) {
                return Status.OPERATION_SUCCESSFUL.getStatusDescriptor();
            }
        } catch (IOException | MessagingException e) {
            return Status.EMAIL_EXCEPTION.getStatusDescriptor();
        }
        return Status.UTTER_FAILURE.getStatusDescriptor();
    }

    @GET
    @UnitOfWork
    @Path("/sendMail")
    public StatusDescriptor sendEmail(@QueryParam("senderEmail") String senderEmail,
                            @QueryParam("body") String body,
                            @QueryParam("recipientAddress") String recipientAddress,
                            @QueryParam("subject") String subject) {

        String adminAddress = configuration.getEmailStuff().getAdminAddress();

        try {
            if (emailService.sendEmail(recipientAddress, adminAddress, senderEmail, subject, body)) {
                return Status.OPERATION_SUCCESSFUL.getStatusDescriptor();
            }
        } catch (IOException | MessagingException e) {
            return Status.EMAIL_EXCEPTION.getStatusDescriptor();
        }
        return Status.UTTER_FAILURE.getStatusDescriptor();
    }
}
