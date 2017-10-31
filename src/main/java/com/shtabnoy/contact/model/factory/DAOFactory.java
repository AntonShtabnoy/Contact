package com.shtabnoy.contact.model.factory;

import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.entity.Attachment;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.entity.Phone;

public abstract class DAOFactory {

    public static final int MYSQL = 1;

    public abstract DAO<Contact, Integer> getContactDAO();
    public abstract DAO<Phone, Integer> getPhoneDAO();
    public abstract DAO<Attachment, Integer> getAttachmentDAO();


    public static DAOFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
            case MYSQL: return new MySqlDAOFactory();
            default: return null;
        }
    }
}
