package uz.pdp.back.support;

import lombok.Data;
import uz.pdp.back.utils.Input;
import uz.pdp.back.utils.Validators;
import uz.pdp.back.utils.ValidatorsForRegistration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Data
public class SendNotificationToEmail {
    private final String senderUsername = "capitanamericaflyjet@gmail.com";
    private final String password = "plao czfj okmv fjbc";

    public SendNotificationToEmail() {
    }
    private final Validators validators = ValidatorsForRegistration.getInstance();

    Executor executor = Executors.newCachedThreadPool();

    public void sendNotification(String recipient, String subject, String sms) {
        Runnable runnable = () -> {
            Properties properties = getProperties();
            Session session = getSession(properties);
            Message message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(senderUsername));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                message.setSubject(subject);
                message.setContent(sms, "text/html");
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        };
        executor.execute(runnable);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    private Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderUsername, password);
            }
        });
    }
}
