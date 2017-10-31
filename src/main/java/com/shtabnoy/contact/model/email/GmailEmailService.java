package com.shtabnoy.contact.model.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class GmailEmailService {
    private static Session SESSION;
    private static Properties PROPERTIES;

    private static Logger logger = Logger.getLogger(GmailEmailService.class);

    public GmailEmailService() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("mail.properties");
        try {
            PROPERTIES = new Properties();
            PROPERTIES.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SESSION = Session.getInstance(PROPERTIES,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                PROPERTIES.getProperty("mail.username"),
                                PROPERTIES.getProperty("mail.password")
                        );
                    }
                });
    }

    public void sendMessage(String fromEmail, String toEmail, String subject, String messageText) {
        try {
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(messageText, "text/html; charset=utf-8");
            Transport.send(message);
            logger.info("Email to address " + toEmail + " was successfully send.");
        } catch (MessagingException me) {
            logger.error(me.getMessage() + ". Error during sending email to address " + toEmail);
        }
    }


    public void sendGroupMessage(String fromEmail, List<String> toEmail, String subject, String messageText) {
        try {
            int j = 0;
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(fromEmail));
            Address []addresses = new Address[toEmail.size()];
            for(String i: toEmail) {
                addresses[j] = new InternetAddress(i);
                j++;
            }
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setContent(messageText, "text/html; charset=utf-8");
            Transport.send(message);

            logger.info("Email to address " + toEmail + " was successfully send.");
        } catch (MessagingException me) {
            logger.error(me.getMessage() + ". Error during sending email to address " + toEmail);
        }
    }
}
