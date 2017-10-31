package com.shtabnoy.contact.model.observer;

import com.shtabnoy.contact.model.action.utility.ContactParameter;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.email.EmailManager;
import com.shtabnoy.contact.model.email.EmailTemplateEnum;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.DAOFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;
import java.util.*;

public class BirthdayObserver implements Job {

    private static final Logger LOGGER = Logger.getLogger(BirthdayObserver.class);
    private static final ResourceBundle BIRTHDAY_PARAMS = ResourceBundle.getBundle("birthday-job-params");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Birthday observer start work!");

        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
            List<Contact> birthdayBoys = ((ContactDAOImpl) contactDAO).getContactsByBirthday(new Date());

            EmailTemplateEnum emailTemplate = EmailTemplateEnum.BIRTHDAY;
            for (Contact person : birthdayBoys) {
                Map<String, String> emailParamsMap = createMapParams(person) ;
                EmailManager emailManager = new EmailManager();
                emailManager.sendEmail(person.getEmail(), emailTemplate, emailParamsMap);
                LOGGER.info("Birthday message was successfully send to email: " + person.getEmail());
            }


        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

    }

    private Map<String, String> createMapParams(Contact contact){
        Map<String,String> emailParamsMap = new HashMap<>();

        emailParamsMap.put(ContactParameter.NAME, BIRTHDAY_PARAMS.getString("sender.name"));
        emailParamsMap.put(ContactParameter.LASTNAME, BIRTHDAY_PARAMS.getString("sender.surname"));
        emailParamsMap.put(ContactParameter.EMAIL, BIRTHDAY_PARAMS.getString("sender.email"));
        emailParamsMap.put(ContactParameter.PHONE, BIRTHDAY_PARAMS.getString("sender.phone"));
        emailParamsMap.put(ContactParameter.TITLE, BIRTHDAY_PARAMS.getString("message.title"));
        emailParamsMap.put("bye", BIRTHDAY_PARAMS.getString("message.bye.words"));
        emailParamsMap.put("sender-email", BIRTHDAY_PARAMS.getString("sender.email"));
        emailParamsMap.put(ContactParameter.RECEIVER_NAME, contact.getName());
        emailParamsMap.put(ContactParameter.RECEIVER_SURNAME, contact.getSurname());

        return emailParamsMap;
    }
}
