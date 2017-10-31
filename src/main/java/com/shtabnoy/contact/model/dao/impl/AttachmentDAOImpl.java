package com.shtabnoy.contact.model.dao.impl;

import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.entity.Attachment;
import com.shtabnoy.contact.model.factory.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAOImpl implements DAO<Attachment, Integer> {
    private static final Logger LOGGER = Logger.getLogger(AttachmentDAOImpl.class);
    private static final String ADD_ATTACHMENT_SQL_QUERY = "INSERT INTO attachments(contact_id, name, type, date, comment) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_ATTACHMENT_SQL_QUERY = "update attachments set name=?, date=?, \n" +
            "comment=? where id=?";
    private static final String DELETE_ATTACHMENT_SQL_QUERY = "update attachments set status=? where id=?";
    private static final String ATTACHMENT_INFO_SQL_QUERY = "select id, name, type, date, comment from attachments where contact_id=?";
    private static final String ATTACHMENTS_SQL_QUERY = "select id, name, type, date, comment from attachments where status='active' and contact_id=?";
    private static final String GET_COUNT_SQL_QUERY = "select COUNT(0) from attachments where status=\"active\"";
    private static final String GET_LAST_ID_SQL_QUERY = "select id from attachments order by id desc limit 1";

    @Override
    public int insert(Attachment attachment) throws SQLException {
        PreparedStatement addAttachmentStmt = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            addAttachmentStmt = connection.prepareStatement(ADD_ATTACHMENT_SQL_QUERY, Statement.RETURN_GENERATED_KEYS);
            addAttachmentStmt.setInt(1, attachment.getContactId());
            addAttachmentStmt.setString(2, attachment.getName());
            addAttachmentStmt.setString(3, attachment.getType());
            addAttachmentStmt.setString(4, attachment.getDate());
            addAttachmentStmt.setString(5, attachment.getComment());

            addAttachmentStmt.executeUpdate();
            ResultSet generatedKeys = addAttachmentStmt.getGeneratedKeys();
            int attachmentId;
            if (generatedKeys.next()) {
                attachmentId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating of new attachment is failed, no ID (id_attachment) received");
            }
            return attachmentId;
        } finally {
            if (addAttachmentStmt != null) {
                addAttachmentStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public int update(Attachment attachment) throws SQLException {
        PreparedStatement updateAttachmentStmt = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            updateAttachmentStmt = connection.prepareStatement(UPDATE_ATTACHMENT_SQL_QUERY);
            updateAttachmentStmt.setString(1, attachment.getName());
            updateAttachmentStmt.setString(2, attachment.getDate());
            updateAttachmentStmt.setString(3, attachment.getComment());
            updateAttachmentStmt.setInt(4, attachment.getId());

            return updateAttachmentStmt.executeUpdate();
        } finally {
            if (updateAttachmentStmt != null) {
                updateAttachmentStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void delete(Integer Id) throws SQLException {
        PreparedStatement deleteAttachment = null;
        Connection connection = null;
        try {
            connection = MySqlDAOFactory.createConnection();
            deleteAttachment = connection.prepareStatement(DELETE_ATTACHMENT_SQL_QUERY);
            deleteAttachment.setString(1, "delete");
            deleteAttachment.setInt(2, Id);
            deleteAttachment.executeUpdate();
        } finally {
            if (deleteAttachment != null) {
                deleteAttachment.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Attachment getContact(Integer contactId) throws SQLException {
        Attachment attachment = new Attachment();
        PreparedStatement getAttachmentInfoStmt = null;
        Connection connection = null;
        ResultSet rs;
        try {
            connection = MySqlDAOFactory.createConnection();
            getAttachmentInfoStmt = connection.prepareStatement(ATTACHMENT_INFO_SQL_QUERY);
            getAttachmentInfoStmt.setInt(1, contactId);
            rs = getAttachmentInfoStmt.executeQuery();
            if (rs.next()) {
                attachment.setId(rs.getInt(1));
                attachment.setContactId(contactId);
                attachment.setName(rs.getString(2));
                attachment.setType(rs.getString(3));
                attachment.setDate(rs.getString(4));
                attachment.setComment(rs.getString(5));
            } else {
                throw new SQLException("No attachment with such id_contact");
            }
            return attachment;
        } finally {
            if (getAttachmentInfoStmt != null) {
                getAttachmentInfoStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<Attachment> findContacts(int contactId, int count) throws SQLException {
        List<Attachment> attachments = null;
        PreparedStatement getAttachmentsStmt = null;
        Connection connection = null;

        try {
            connection = MySqlDAOFactory.createConnection();
            getAttachmentsStmt = connection.prepareStatement(ATTACHMENTS_SQL_QUERY);
            getAttachmentsStmt.setInt(1, contactId);

            ResultSet rs = getAttachmentsStmt.executeQuery();
            attachments = new ArrayList<Attachment>(count);

            while (rs.next()) {
                Attachment attachment = new Attachment();
                attachment.setId(rs.getInt(1));
                attachment.setContactId(contactId);
                attachment.setName(rs.getString(2));
                attachment.setType(rs.getString(3));
                attachment.setDate(rs.getString(4));
                attachment.setComment(rs.getString(5));
                attachments.add(attachment);
            }
            return attachments;
        } finally {
            if (getAttachmentsStmt != null) {
                getAttachmentsStmt.close();
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

    public int getLastAttachmentId() throws SQLException {
        PreparedStatement getLastIdStmt = null;
        Connection connection = null;
        ResultSet rs = null;
        int lastId = 0;


        try {
            connection = MySqlDAOFactory.createConnection();
            getLastIdStmt = connection.prepareStatement(GET_LAST_ID_SQL_QUERY);
            rs = getLastIdStmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (getLastIdStmt != null) {
                getLastIdStmt.close();
            }
            return lastId;
        }
    }
}
