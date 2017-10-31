package com.shtabnoy.contact.model.action.impl;


import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.ContactParameter;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.email.EmailManager;
import com.shtabnoy.contact.model.email.EmailTemplateEnum;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.DAOFactory;
import com.shtabnoy.contact.model.utility.PageManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SendEmailCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SendEmailCommand.class);
    private static final String CHECKBOX_SELECTED_CONTACTS= "selectedContacts";
    private static final String ERROR_PAGE = "/main/error";
    private static final String SEND_EMAIL_PAGE = "path.page.jsp.send_email";
    private static final String MAIN_PAGE = "/main/info?page=1";
    private static final String EMAIL_ATTRIBUTE = "emailsInfo";
    private ArrayList<Contact> emailsInfo = new ArrayList<>();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Send email command starts work!");

        String page;
        String stringOfId[] = request.getParameterValues(CHECKBOX_SELECTED_CONTACTS);
        LOGGER.info("Get selected contacts with id: " + Arrays.toString(stringOfId));
        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = null;
            if (mySqlFactory != null) {
                contactDAO = mySqlFactory.getContactDAO();
            }
            if(request.getParameter("isSend").equals("false")) {
                if (contactDAO == null) {
                    response.sendRedirect(ERROR_PAGE);
                } else {
                    for (String id : stringOfId) {
                        emailsInfo.add(((ContactDAOImpl) contactDAO).getEmailInfo(Integer.valueOf(id)));
                    }
                    LOGGER.info("Get email info from database: " + emailsInfo);
                    request.setAttribute(EMAIL_ATTRIBUTE, emailsInfo);
                    page = PageManager.getPage(SEND_EMAIL_PAGE);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            } else {
                String []emails = request.getParameterValues("emails");
                EmailTemplateEnum emailTemplate = EmailTemplateEnum.valueOf(request.getParameter("selectTemplate").toUpperCase());
                List<String> emailsList = Arrays.asList(emails);
                if("Group".equals(request.getParameter("radioEmail"))){
                    LOGGER.info("Group message was selected!");
                    Map<String, String> emailParamsMap = emailTemplate.getEmailParamsMap(request);
                    emailParamsMap.put(ContactParameter.RECEIVER_NAME, "receivers");
                    emailParamsMap.put(ContactParameter.RECEIVER_SURNAME, "");
                    EmailManager emailManager = new EmailManager();
                    emailManager.sendEmail(emailsList, emailTemplate, emailParamsMap);
                }
                else {
                    for (String i: emailsList) {
                        Map<String, String> emailParamsMap = emailTemplate.getEmailParamsMap(request);
                        Contact contact = null;
                        if (contactDAO != null) {
                            contact = ((ContactDAOImpl) contactDAO).getNameContactFromEmail(i);
                            LOGGER.info("Get contact from email: " + contact);
                        }
                        if (contact != null) {
                            emailParamsMap.put(ContactParameter.RECEIVER_NAME, contact.getName());
                        }
                        emailParamsMap.put(ContactParameter.RECEIVER_SURNAME, contact.getSurname());
                        EmailManager emailManager = new EmailManager();
                        emailManager.sendEmail(i, emailTemplate, emailParamsMap);
                    }
                }
                response.sendRedirect(MAIN_PAGE);
            }

        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage());
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
