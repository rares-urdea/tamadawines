package ro.tamadawines.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.tamadawines.core.main.TamadawinesConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private TamadawinesConfiguration configuration;

    public EmailService(TamadawinesConfiguration configuration) {
        this.configuration = configuration;
    }

    public Boolean sendEmail(String to, String from, String senderAddress, String subject, String emailBody)
            throws IOException, MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", configuration.getEmailStuff().getGmailUserAccount());
        props.put("mail.smtp.password", configuration.getEmailStuff().getGmailPassword());
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        try {
            InternetAddress fromAddr = new InternetAddress(from);
            message.setSubject(subject);
            message.setFrom(fromAddr);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setReplyTo(new Address[]{
                    new InternetAddress(senderAddress)
            });

            message.setText(emailBody, "utf-8", "html");

            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"),
                    props.getProperty("mail.smtp.password"));

            transport.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
