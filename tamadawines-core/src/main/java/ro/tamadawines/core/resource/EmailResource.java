package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailResource.class);

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
                LOGGER.info("ContactUs request sent by: {}", senderEmail);
                return Status.OPERATION_SUCCESSFUL.getStatusDescriptor();
            }
        } catch (IOException | MessagingException e) {
            LOGGER.error("sendContactUsEmail failed with IOException | MessagingException: {}", e.getMessage());
            return Status.EMAIL_EXCEPTION.getStatusDescriptor();
        }
        LOGGER.error("sendContactUsEmail failed utterly");
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
                LOGGER.info("sendEmail request by: {} with subject: {} and recipient address: {}",
                        senderEmail, subject, recipientAddress);
                return Status.OPERATION_SUCCESSFUL.getStatusDescriptor();
            }
        } catch (IOException | MessagingException e) {
            LOGGER.error("sendEmail failed with IOException | MessagingException: {}", e.getMessage());
            return Status.EMAIL_EXCEPTION.getStatusDescriptor();
        }
        LOGGER.error("sendEmail failed utterly");
        return Status.UTTER_FAILURE.getStatusDescriptor();
    }
}
