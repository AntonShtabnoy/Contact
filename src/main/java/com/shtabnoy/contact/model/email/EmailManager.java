package com.shtabnoy.contact.model.email;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class EmailManager {

    private static final Logger LOGGER = Logger.getLogger(EmailManager.class);
    private static final String SENDER_EMAIL = "sender-email";
    private static final String MESSAGE_TITLE = "title";
    private GmailEmailService gmailEmailService;


    public EmailManager(){
        this.gmailEmailService = new GmailEmailService();
    }

    public void sendEmail(List<String> receiverEmails, EmailTemplateEnum emailTemplate, Map<String, String> emailParamsMap ){
        EmailTemplateCreator templateCreator = new EmailTemplateCreator();
        String message = templateCreator.createTemplate(emailTemplate, emailParamsMap);
        EmailSender emailSender = new EmailSender(gmailEmailService, emailParamsMap.get(SENDER_EMAIL), receiverEmails,
                emailParamsMap.get(MESSAGE_TITLE), message );
        Thread sender = new Thread(emailSender);
        sender.start();
        LOGGER.info("Email sender was successfully start.");
    }
    public void sendEmail(String receiverEmail, EmailTemplateEnum emailTemplate, Map<String, String> emailParamsMap ){
        EmailTemplateCreator templateCreator = new EmailTemplateCreator();
        String message = templateCreator.createTemplate(emailTemplate, emailParamsMap);
        EmailSender emailSender = new EmailSender(gmailEmailService, emailParamsMap.get(SENDER_EMAIL), receiverEmail,
                emailParamsMap.get(MESSAGE_TITLE), message );
        Thread sender = new Thread(emailSender);
        sender.start();
        LOGGER.info("Email sender was successfully start.");
    }
}
