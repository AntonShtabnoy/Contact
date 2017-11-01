package com.shtabnoy.contact.model.email;

import com.shtabnoy.contact.model.action.utility.ContactParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public enum EmailTemplateEnum {

    DEFAULT("default.ftlh"),
    BIRTHDAY("birthday.ftlh");

    private static final Logger LOGGER = Logger.getLogger(EmailTemplateEnum.class);

    private String templateFileName;
    private final static String SENDER_NAME = "sender-name";
    private final static String SENDER_SURNAME = "sender-surname";
    private final static String SENDER_EMAIL = "sender-email";
    private final static String SENDER_PHONE = "sender-phone";
    private final static String MESSAGE_TITLE = "title";
    private final static String MESSAGE_TEXT = "message";
    private final static String BYE_TEXT = "bye";
    private final static String MAIL_PROPERTIES = "mail.properties";
    private final static String MAIL_USERNAME = "mail.username";
    private static String isGroupSend;

    public static String getIsGroupSend() {
        return isGroupSend;
    }

    public static void setIsGroupSend(String isGroupSend) {
        EmailTemplateEnum.isGroupSend = isGroupSend;
    }

    EmailTemplateEnum(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public Map<String, String> getEmailParamsMap(HttpServletRequest request) {
        Map<String, String> emailParamsMap = new HashMap<>();
        emailParamsMap = getEmailParams(emailParamsMap, request);
        return emailParamsMap;
    }

    private Map<String, String> getEmailParams(Map<String, String> emailParamsMap,
                                               HttpServletRequest request) {
        try {
            emailParamsMap.put(ContactParameter.NAME, request.getParameter(SENDER_NAME));
            emailParamsMap.put(ContactParameter.LASTNAME, request.getParameter(SENDER_SURNAME));
            emailParamsMap.put(ContactParameter.EMAIL, request.getParameter(SENDER_EMAIL));
            emailParamsMap.put(ContactParameter.PHONE, request.getParameter(SENDER_PHONE));
            emailParamsMap.put(ContactParameter.TITLE, request.getParameter(MESSAGE_TITLE));
            emailParamsMap.put(ContactParameter.MESSAGE, request.getParameter(MESSAGE_TEXT));
            emailParamsMap.put(BYE_TEXT, request.getParameter(BYE_TEXT));
            LOGGER.info("User wants to send message with params: sender_name- " + request.getParameter(SENDER_NAME) + " sender_surname- " + request.getParameter(SENDER_SURNAME) + " sender_email- " + request.getParameter(SENDER_EMAIL) + " sender_phone- " + request.getParameter(SENDER_PHONE) + " title- " + request.getParameter(MESSAGE_TITLE) + " text- " + request.getParameter(MESSAGE_TEXT));
            isGroupSend = request.getParameter("radioEmail");
            InputStream input = getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTIES);
            Properties PROPERTIES = new Properties();
            PROPERTIES.load(input);
            emailParamsMap.put(SENDER_EMAIL, PROPERTIES.getProperty(MAIL_USERNAME));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return emailParamsMap;
    }

}
