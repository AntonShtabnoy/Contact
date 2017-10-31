package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.ContactParameter;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.entity.Attachment;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.entity.Phone;
import com.shtabnoy.contact.model.factory.DAOFactory;
import com.shtabnoy.contact.model.utility.PageManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditContactCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EditContactCommand.class);
    private static final String ERROR_PAGE = "/main/error";
    private static final String EDIT_PAGE = "path.page.jsp.edit";
    private static final String ATTRIBUTE_FOR_ID = "contact";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Edit command starts work!");

        String page;
        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
            DAO<Attachment, Integer> attachmentDAO = mySqlFactory.getAttachmentDAO();
            DAO<Phone, Integer> phoneDAO = mySqlFactory.getPhoneDAO();
            ContactParameter param = new ContactParameter();
            if (contactDAO == null || attachmentDAO == null || phoneDAO == null) {
               response.sendRedirect(ERROR_PAGE);
            } else {
                checkPageAttribute(request, response, contactDAO);
                int id = Integer.valueOf(request.getParameter(ATTRIBUTE_FOR_ID));
                Contact contact = contactDAO.getContact(id);
                LOGGER.info("Get contact with id " + id + "from database: " + contact);

                List<Phone> phones = phoneDAO.findContacts(id, 0);
                LOGGER.info("Get phones: " + phones + "of contact with id " + id);
                List<Attachment> attachments = attachmentDAO.findContacts(id, 0);
                LOGGER.info("Get attachments: " + attachments + "of contact with id " + id);

                request.setAttribute("contactProfile", contact);
                request.setAttribute("phones", phones);
                request.setAttribute("attachments", attachments);
                request.setAttribute("countries", param.getCountries());
                request.setAttribute("nationalities", param.getNationalities());
                LOGGER.info("Information about contact was sent to edit page!");

                page = PageManager.getPage(EDIT_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
        catch (ServletException | IOException | SQLException e) {
            LOGGER.error(e.getMessage());
            response.sendRedirect(ERROR_PAGE);
        }
    }


    private void checkPageAttribute(HttpServletRequest request, HttpServletResponse response, DAO<Contact, Integer> contactDAO ) throws SQLException, IOException {
        int lastId = ((ContactDAOImpl)contactDAO).getLastId();
        int id = Integer.valueOf(request.getParameter(ATTRIBUTE_FOR_ID));
        if(id > lastId){
            response.sendRedirect(ERROR_PAGE);
        } else {
            Contact contact = contactDAO.getContact(id);
            if("delete".equals(contact.getStatus())){
                response.sendRedirect(ERROR_PAGE);
            }
        }
    }
}
