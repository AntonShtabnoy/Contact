package com.shtabnoy.contact.model.email;

import org.apache.log4j.Logger;

import java.util.List;


public class EmailSender implements Runnable {

    private static Logger LOGGER = Logger.getLogger(EmailSender.class);

    private GmailEmailService service;
    private String senderEmail;
    private List<String> receiverEmails;
    private String receiverEmail;
    private String title;
    private String message;

    public EmailSender(GmailEmailService service){
        this.service = service;
    }

    public EmailSender(GmailEmailService service, String senderEmail, List<String> receiverEmails,
            String title, String message) {
        this.service = service;
        this.senderEmail = senderEmail;
        this.receiverEmails = receiverEmails;
        this.title = title;
        this.message = message;
    }
    public EmailSender(GmailEmailService service, String senderEmail, String receiverEmail,
                       String title, String message) {
        this.service = service;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.message = message;
    }

    @Override
    public void run() {
        if("Group".equals(EmailTemplateEnum.getIsGroupSend()))
        {
            service.sendGroupMessage(senderEmail, receiverEmails, title, message);
        } else {
            LOGGER.info("Sending email to address " + receiverEmail);
             service.sendMessage(senderEmail, receiverEmail, title, message);
        }
    }
}
