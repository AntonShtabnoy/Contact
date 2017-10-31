package com.shtabnoy.contact.model.factory;

import com.shtabnoy.contact.model.dao.impl.AttachmentDAOImpl;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.PhoneDAOImpl;
import com.shtabnoy.contact.model.entity.Attachment;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.entity.Phone;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory {

    private static final String URL_DB = "java:comp/env/jdbc/contact_directory";
    private static final Logger LOGGER = Logger.getLogger(MySqlDAOFactory.class);


    public static Connection createConnection() throws SQLException {
        DataSource ds = null;
        InitialContext initContext;
        try {
            initContext = new InitialContext();
            ds = (DataSource) initContext.lookup(URL_DB);

        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
        if (ds != null) {
            return  ds.getConnection();
        } else {
            throw new SQLException();
        }
    }




    @Override
    public DAO<Contact, Integer> getContactDAO() {
        return new ContactDAOImpl();
    }

    @Override
    public DAO<Phone, Integer> getPhoneDAO() {
        return new PhoneDAOImpl();
    }

    @Override
    public DAO<Attachment, Integer> getAttachmentDAO() {
        return new AttachmentDAOImpl();
    }


}
