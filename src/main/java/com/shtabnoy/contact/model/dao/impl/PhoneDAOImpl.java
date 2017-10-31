package com.shtabnoy.contact.model.dao.impl;

import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.factory.MySqlDAOFactory;
import com.shtabnoy.contact.model.entity.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAOImpl implements DAO<Phone, Integer> {
    private static final String ADD_PHONE_SQL_QUERY = "INSERT INTO phones(contact_id, phone_number, \n"
            + "comment, phone_type) \n" + "VALUES(?, ?, ?, ?)";
    private static final String UPDATE_PHONE_SQL_QUERY = "update phones set phone_number=?, comment=?, phone_type=? where id=?";
    private static final String DELETE_PHONE_SQL_QUERY = "update phones set phone_status=? where id=?";
    private static final String GET_PHONE_INFO_SQL_QUERY = "select id, phone_number, comment, phone_type from phones where contact_id=?";
    private static final String GET_PHONES_SQL_QUERY = "select id, phone_number, " +
            "comment, phone_type from phones where phone_status='active' and contact_id=?";
    private static final String GET_COUNT_SQL_QUERY = "select COUNT(0) from phones where phone_status=\"active\"";

    @Override
    public int insert(Phone contact) throws SQLException {
        PreparedStatement addPhoneStmt = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            addPhoneStmt = connection.prepareStatement(ADD_PHONE_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            addPhoneStmt.setInt(1, contact.getContactId());
            addPhoneStmt.setString(2, contact.getNumber());
            addPhoneStmt.setString(3, contact.getComment());
            addPhoneStmt.setString(4, contact.getType());

            addPhoneStmt.executeUpdate();
            ResultSet generatedKeys = addPhoneStmt.getGeneratedKeys();
            int phoneId;
            if (generatedKeys.next()) {
                phoneId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating of new contact is failed, no ID (id_contact) received ");
            }
            return phoneId;
        } finally {
            if (addPhoneStmt != null) {
                addPhoneStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int update(Phone phone) throws SQLException {
        PreparedStatement updatePhoneStmt = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            updatePhoneStmt = connection.prepareStatement(UPDATE_PHONE_SQL_QUERY);
            updatePhoneStmt.setString(1, phone.getNumber());
            updatePhoneStmt.setString(2, phone.getComment());
            updatePhoneStmt.setString(3, phone.getType());
            updatePhoneStmt.setInt(4, phone.getId());
            return updatePhoneStmt.executeUpdate();
        } finally {
            if (updatePhoneStmt != null) {
                updatePhoneStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void delete(Integer contactId) throws SQLException {
        PreparedStatement deletePhone = null;
        Connection connection = null;

        try {
            connection = MySqlDAOFactory.createConnection();
            deletePhone = connection.prepareStatement(DELETE_PHONE_SQL_QUERY);
            deletePhone.setString(1, "delete");
            deletePhone.setInt(2, contactId);
            deletePhone.executeUpdate();
        } finally {
            if (deletePhone != null) {
                deletePhone.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public Phone getContact(Integer contactId) throws SQLException {
        Phone phone = new Phone();
        PreparedStatement getPhoneInfoStmt = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            getPhoneInfoStmt = connection.prepareStatement(GET_PHONE_INFO_SQL_QUERY);
            getPhoneInfoStmt.setInt(1, contactId);
            ResultSet rs = getPhoneInfoStmt.executeQuery();
            if (rs.next()) {
                phone.setId(rs.getInt(1));
                phone.setContactId(contactId);
                phone.setNumber(rs.getString(2));
                phone.setComment(rs.getString(2));
                phone.setType(rs.getString(4));
            } else {
                throw new SQLException("No contact with such id_contact");
            }
            return phone;
        } finally {
            if (getPhoneInfoStmt != null) {
                getPhoneInfoStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<Phone> findContacts(int contactId, int count) throws SQLException {
        List<Phone> phones = null;
        PreparedStatement getPhonesStmt = null;
        Connection connection = null;


        try {
            connection = MySqlDAOFactory.createConnection();
            getPhonesStmt = connection.prepareStatement(GET_PHONES_SQL_QUERY);
            getPhonesStmt.setInt(1, contactId);
            ResultSet rs = getPhonesStmt.executeQuery();
            phones = new ArrayList<>();
            while (rs.next()) {
                Phone phone = new Phone();
                phone.setId(rs.getInt(1));
                phone.setContactId(contactId);
                phone.setNumber(rs.getString(2));
                phone.setComment(rs.getString(3));
                phone.setType(rs.getString(4));

                phones.add(phone);
            }
            return phones;
        } finally {
            if (getPhonesStmt != null) {
                getPhonesStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int getCountContacts() throws SQLException {
        PreparedStatement getCountStmt = null;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = MySqlDAOFactory.createConnection();
            getCountStmt = connection.prepareStatement(GET_COUNT_SQL_QUERY);
            rs = getCountStmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getCountStmt != null) {
                getCountStmt.close();
            }
        }
    }
}
