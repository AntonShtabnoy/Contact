package com.shtabnoy.contact.model.dao.impl;

import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContactDAOImpl implements DAO<Contact, Integer> {
    private static final String ADD_CONTACT_SQL_QUERY = "INSERT INTO contacts(contact_name, contact_surname, contact_dob, contact_sex, \n"
            + "contact_nationality, family_status, contact_website, contact_email, contact_work, contact_country, contact_city, contact_street, contact_home_flat, contact_index) \n"
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTACT_SQL_QUERY = "update contacts set contact_name=?, contact_surname=?,contact_dob=?, \n" + "contact_sex=?, contact_nationality=?, family_status=?, contact_website=?, contact_email=?, contact_work=?, \n" + "contact_country=?, contact_city=?, contact_street=?, contact_home_flat=?, contact_index=?, contact_image=? where contact_id=?";
    private static final String DELETE_CONTACT_SQL_QUERY = "update contacts set contact_status=? where contact_id=?";
    private static final String GET_CONTACT_INFO_SQL_QUERY = "select contact_name, contact_surname, contact_dob, contact_sex, \n" + "contact_nationality, family_status, contact_website, contact_email, contact_work, \n" +
            "contact_country, contact_city, contact_street, contact_home_flat, contact_index, contact_status, contact_image from contacts where contact_id=?";
    private static final String GET_CONTACTS_SQL_QUERY = "select contacts.contact_id, contact_name, contact_surname ,contact_dob, " + "contact_country, contact_city, contact_street, contact_home_flat, " +
            "contact_work from contacts where contact_status='active' order by contact_name limit ?,?";
    private static final String GET_COUNT_SQL_QUERY = "select COUNT(0) from contacts where contact_status=\"active\"";
    private static String GET_IMAGE_SQL_QUERY = "select contact_image from contacts where contact_id=?";
    private static final String GET_EMAIL_SQL_QUERY = "select contact_email from contacts where contact_id=? and contact_status='active'";
    private static final String GET_NAME_FROM_EMAIL_SQL_QUERY = "select contact_name, contact_surname from contacts where contact_email=? and contact_status='active'";
    private static final String GET_CONTACTS_FROM_DATE_SQL_QUERY = "select contact_name, contact_surname, contact_email from contacts where DATE_FORMAT( `contact_dob` ,'%m-%d')= DATE_FORMAT(?, '%m-%d')";
    private static final String GET_LAST_ID_SQL_QUERY = "select max(contacts.contact_id) from contacts";

    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ContactDAOImpl.class);


    public ContactDAOImpl() {
    }

    @Override
    public int insert(Contact contact) throws SQLException {
        PreparedStatement addContactStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            addContactStmt = connection.prepareStatement(ADD_CONTACT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            addContactStmt.setString(1, contact.getName());
            addContactStmt.setString(2, contact.getSurname());
            addContactStmt.setString(3, contact.getDate());
            addContactStmt.setString(4, contact.getSex());
            addContactStmt.setString(5, contact.getNationality());
            addContactStmt.setString(6, contact.getFamily_status());
            addContactStmt.setString(7, contact.getWebSite());
            addContactStmt.setString(8, contact.getEmail());
            addContactStmt.setString(9, contact.getWorkPlace());
            addContactStmt.setString(10, contact.getCountry());
            addContactStmt.setString(11, contact.getCity());
            addContactStmt.setString(12, contact.getStreet());
            addContactStmt.setString(13, contact.getHome());
            addContactStmt.setString(14, contact.getIndex());

            addContactStmt.executeUpdate();
            ResultSet generatedKeys = addContactStmt.getGeneratedKeys();
            int contactId;
            if (generatedKeys.next()) {
                contactId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating of new contact is failed, no ID (id_contact) received ");
            }
            return contactId;
        } finally {
            if (addContactStmt != null) {
                addContactStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int update(Contact contact) throws SQLException {
        PreparedStatement updateContactStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            updateContactStmt = connection.prepareStatement(UPDATE_CONTACT_SQL_QUERY);
            updateContactStmt.setString(1, contact.getName());
            updateContactStmt.setString(2, contact.getSurname());
            updateContactStmt.setString(3, contact.getDate());
            updateContactStmt.setString(4, contact.getSex());
            updateContactStmt.setString(5, contact.getNationality());
            updateContactStmt.setString(6, contact.getFamily_status());
            updateContactStmt.setString(7, contact.getWebSite());
            updateContactStmt.setString(8, contact.getEmail());
            updateContactStmt.setString(9, contact.getWorkPlace());
            updateContactStmt.setString(10, contact.getCountry());
            updateContactStmt.setString(11, contact.getCity());
            updateContactStmt.setString(12, contact.getStreet());
            updateContactStmt.setString(13, contact.getHome());
            updateContactStmt.setString(14, contact.getIndex());
            updateContactStmt.setString(15, contact.getImage());
            updateContactStmt.setInt(16, contact.getId());


            return updateContactStmt.executeUpdate();
        } finally {
            if (updateContactStmt != null) {
                updateContactStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public void delete(Integer contactId) throws SQLException {
        PreparedStatement deleteContact = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            deleteContact = connection.prepareStatement(DELETE_CONTACT_SQL_QUERY);
            deleteContact.setString(1, "delete");
            deleteContact.setInt(2, contactId);
            deleteContact.executeUpdate();
        } finally {
            if (deleteContact != null) {
                deleteContact.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public Contact getContact(Integer contactId) throws SQLException {
        Contact contact = new Contact();
        PreparedStatement getContactInfoStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            getContactInfoStmt = connection.prepareStatement(GET_CONTACT_INFO_SQL_QUERY);
            getContactInfoStmt.setInt(1, contactId);
            ResultSet rs;
            rs = getContactInfoStmt.executeQuery();
            if (rs.next()) {
                contact.setId(contactId);
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
                contact.setDate(rs.getString(3));
                contact.setSex(rs.getString(4));
                contact.setNationality(rs.getString(5));
                contact.setFamily_status(rs.getString(6));
                contact.setWebSite(rs.getString(7));
                contact.setEmail(rs.getString(8));
                contact.setWorkPlace(rs.getString(9));
                contact.setCountry(rs.getString(10));
                contact.setCity(rs.getString(11));
                contact.setStreet(rs.getString(12));
                contact.setHome(rs.getString(13));
                contact.setIndex(rs.getString(14));
                contact.setStatus(rs.getString(15));
                contact.setImage(rs.getString(16));
            } else {
                throw new SQLException("No contact with such id_contact");
            }
            return contact;
        } finally {
            if (getContactInfoStmt != null) {
                getContactInfoStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<Contact> findContacts(int displacement, int countContactsOnPage) throws SQLException {
        List<Contact> contacts = null;
        PreparedStatement getContactsStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            getContactsStmt = connection.prepareStatement(GET_CONTACTS_SQL_QUERY);
            getContactsStmt.setInt(1, displacement);
            getContactsStmt.setInt(2, countContactsOnPage);

            ResultSet rs = getContactsStmt.executeQuery();
            contacts = new ArrayList<Contact>(countContactsOnPage);

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(1));
                contact.setName(rs.getString(2));
                contact.setSurname(rs.getString(3));
                contact.setDate(rs.getString(4));
                contact.setCountry(rs.getString(5));
                contact.setCity(rs.getString(6));
                contact.setStreet(rs.getString(7));
                contact.setHome(rs.getString(8));
                contact.setWorkPlace(rs.getString(9));

                contacts.add(contact);
            }
            return contacts;
        } finally {
            if (getContactsStmt != null) {
                getContactsStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int getCountContacts() throws SQLException {
        PreparedStatement getCountStmt = null;
        ResultSet rs;
        int count = 0;
        try {
            connection = MySqlDAOFactory.createConnection();
            getCountStmt = connection.prepareStatement(GET_COUNT_SQL_QUERY);
            rs = getCountStmt.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
            return count;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getCountStmt != null) {
                getCountStmt.close();
            }
        }
    }

    public int getLastId() throws SQLException {
        PreparedStatement getLastIdStmt = null;
        ResultSet rs;
        int lastId = 0;
        try {
            connection = MySqlDAOFactory.createConnection();
            getLastIdStmt = connection.prepareStatement(GET_LAST_ID_SQL_QUERY);
            rs = getLastIdStmt.executeQuery();
            if (rs.next())
                lastId = rs.getInt(1);
            return lastId;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getLastIdStmt != null) {
                getLastIdStmt.close();
            }
        }
    }

    public List<Contact> searchContacts(Contact contact, int displacement, int count) throws SQLException {
        Class c = contact.getClass();
        Field[] fields = c.getDeclaredFields();
        StringBuilder resultRequest = new StringBuilder("select contacts.contact_id, contact_name, contact_surname, \n"
                + "contact_dob, contact_country, contact_city, contact_street, contact_home_flat, \n"
                + "contact_work from contacts where contact_status=\"active\" ");
        List<Contact> contacts = null;
        ArrayList<String> params = new ArrayList<>();
        PreparedStatement searchStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            for (Field field : fields) {
                field.setAccessible(true);
                String value = String.valueOf(field.get(contact));
                if (!"".equals(value) && !"id".equals(field.getName()) && !"null".equals(value)) {
                    resultRequest.append(createPartOfRequest(field));
                    if ("dateFrom".equals(field.getName()) || "toDate".equals(field.getName())) {
                        params.add(value);
                    } else
                        params.add("%" + value + "%");
                }
            }
            resultRequest.append(" limit ?,?");
            searchStmt = connection.prepareStatement(String.valueOf(resultRequest));
            Iterator<String> iterator = params.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                searchStmt.setString(i, iterator.next());
                i++;
            }
            searchStmt.setInt(i, displacement);
            searchStmt.setInt(i + 1, count);
            ResultSet rs = searchStmt.executeQuery();
            contacts = new ArrayList<>();
            while (rs.next()) {
                Contact cont = new Contact();
                cont.setId(rs.getInt("contact_id"));
                cont.setName(rs.getString("contact_name"));
                cont.setSurname(rs.getString("contact_surname"));
                cont.setDate(rs.getString("contact_dob"));
                cont.setCountry(rs.getString("contact_country"));
                cont.setCity(rs.getString("contact_city"));
                cont.setStreet(rs.getString("contact_street"));
                cont.setHome(rs.getString("contact_home_flat"));
                cont.setWorkPlace(rs.getString("contact_work"));

                contacts.add(cont);
            }

        } catch (IllegalAccessException e) {
          LOGGER.error(e.getMessage());
        } finally {
            if (searchStmt != null) {
                searchStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return contacts;
    }

    public int getCountSearchedContacts(Contact contact) throws SQLException {
        Class c = contact.getClass();
        Field[] fields = c.getDeclaredFields();
        StringBuilder resultRequest = new StringBuilder("select count(0) from contacts where contact_status=\"active\" ");
        int count = 0;
        ArrayList<String> params = new ArrayList<>();
        PreparedStatement searchStmt = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            for (Field field : fields) {
                field.setAccessible(true);
                String value = String.valueOf(field.get(contact));
                if (!"".equals(value) && !"id".equals(field.getName()) && !"null".equals(value)) {
                    resultRequest.append(createPartOfRequest(field));
                    if ("dateFrom".equals(field.getName()) || "toDate".equals(field.getName())) {
                        params.add(value);
                    } else
                        params.add("%" + value + "%");
                }
            }
            searchStmt = connection.prepareStatement(String.valueOf(resultRequest));
            Iterator<String> iterator = params.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                searchStmt.setString(i, iterator.next());
                i++;
            }
            ResultSet rs = searchStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (searchStmt != null) {
                searchStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return count;
    }

    public String getImageName(int contactId) throws SQLException {
        PreparedStatement getImageStmt = null;
        ResultSet rs;
        String image = "";
        try {
            connection = MySqlDAOFactory.createConnection();
            getImageStmt = connection.prepareStatement(GET_IMAGE_SQL_QUERY);
            getImageStmt.setInt(1, contactId);
            rs = getImageStmt.executeQuery();
            if (rs.next())
                image = rs.getString(1);
            return image;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getImageStmt != null) {
                getImageStmt.close();
            }
        }
    }

    public Contact getEmailInfo(int contactId) throws SQLException {
        PreparedStatement getEmailStmt = null;
        ResultSet rs;
        String email = "";
        try {
            Contact contact = new Contact();
            connection = MySqlDAOFactory.createConnection();
            getEmailStmt = connection.prepareStatement(GET_EMAIL_SQL_QUERY);
            getEmailStmt.setInt(1, contactId);
            rs = getEmailStmt.executeQuery();
            if (rs.next()) {
                contact.setEmail(rs.getString(1));
            }
            return contact;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getEmailStmt != null) {
                getEmailStmt.close();
            }
        }
    }

    public Contact getNameContactFromEmail(String email) throws SQLException {
        PreparedStatement getNameStmt = null;
        ResultSet rs;
        try {
            Contact contact = new Contact();
            connection = MySqlDAOFactory.createConnection();
            getNameStmt = connection.prepareStatement(GET_NAME_FROM_EMAIL_SQL_QUERY);
            getNameStmt.setString(1, email);
            rs = getNameStmt.executeQuery();
            if (rs.next()) {
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
            }
            return contact;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getNameStmt != null) {
                getNameStmt.close();
            }
        }
    }

    public List<Contact> getContactsByBirthday(Date date) throws SQLException {
        PreparedStatement getContactsStmt = null;
        ResultSet rs = null;
        List<Contact> list = new ArrayList<>();

        try {
            connection = MySqlDAOFactory.createConnection();
            getContactsStmt = connection.prepareStatement(GET_CONTACTS_FROM_DATE_SQL_QUERY);
            getContactsStmt.setDate(1, new java.sql.Date(date.getTime()));
            rs = getContactsStmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setName(rs.getString(1));
                contact.setSurname(rs.getString(2));
                contact.setEmail(rs.getString(3));
                list.add(contact);
            }
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getContactsStmt != null) {
                getContactsStmt.close();
            }
        }
    }

    private String createPartOfRequest(Field field) {
        String resultPart;
        if ("dateFrom".equals(field.getName())) {
            resultPart = "and contact_dob >= ?";
        } else if ("toDate".equals(field.getName())) {
            resultPart = "and contact_dob <= ?";
        } else if ("family_status".equals(field.getName())) {
            resultPart = "and " + field.getName() + " like ? ";
        } else if ("workPlace".equals(field.getName())) {
            resultPart = "and contact_work like ? ";
        } else if ("home".equals(field.getName())) {
            resultPart = "and contact_home_flat like ?";
        } else {
            resultPart = "and contact_" + field.getName() + " like ? ";
        }
        return resultPart;
    }

}
