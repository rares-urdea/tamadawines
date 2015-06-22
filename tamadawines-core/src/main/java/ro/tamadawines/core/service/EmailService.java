package ro.tamadawines.core.service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    public String sendEmail(String to, String from, String senderAddress, String subject, String bodyText)
            throws IOException, MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "axesdnd");
        props.put("mail.smtp.password", "@Columb511");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);

        try {
            InternetAddress fromAddr = new InternetAddress(from);
            message.setSubject(subject);
            message.setFrom(fromAddr);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setReplyTo(new Address[] {
                    new InternetAddress(senderAddress)
            });

            message.setText(bodyText, "utf-8", "html");

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "axesdnd", "@Columb511");
            transport.sendMessage(message, message.getAllRecipients());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "{\"responseCode\": 200,\"handling\": \"success\"}";
    }
}
