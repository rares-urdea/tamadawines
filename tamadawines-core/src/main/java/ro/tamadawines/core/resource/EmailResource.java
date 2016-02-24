package ro.tamadawines.core.resource;

import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.tamadawines.core.main.TamadawinesConfiguration;
import ro.tamadawines.core.model.Message;
import ro.tamadawines.core.model.MessageWrapper;
import ro.tamadawines.core.service.EmailService;

import javax.mail.MessagingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response sendContactUsEmail(@QueryParam("senderEmail") String senderEmail,
                                               @QueryParam("senderName") String senderName,
                                               @QueryParam("body") String body,
                                               @QueryParam("subject") String subject) {
        String adminAddress = configuration.getEmailStuff().getAdminAddress();
        return sendEmail(senderEmail, body, adminAddress, subject);
    }

    @GET
    @UnitOfWork
    @Path("/sendMail")
    public Response sendEmail(@QueryParam("senderEmail") String senderEmail,
                                      @QueryParam("body") String body,
                                      @QueryParam("recipientAddress") String recipientAddress,
                                      @QueryParam("subject") String subject) {

        String adminAddress = configuration.getEmailStuff().getAdminAddress();
        try {
            if (emailService.sendEmail(recipientAddress, adminAddress, senderEmail, subject, body)) {
                LOGGER.info("sendEmail request by: {} with subject: {} and recipient address: {}",
                        senderEmail, subject, recipientAddress);
                return Response.status(Response.Status.OK).entity(new MessageWrapper(Message.SUCCESS)).build();
            }
        } catch (IOException | MessagingException e) {
            LOGGER.error("sendEmail failed with IOException | MessagingException: {}", e.getMessage());
            return Response.status(Response.Status.OK).entity(new MessageWrapper(Message.EMAIL_EXCEPTION)).build();
        }
        LOGGER.error("sendEmail failed utterly");
        return Response.status(Response.Status.OK).entity(new MessageWrapper(Message.UTTER_FAILURE)).build();
    }
}
