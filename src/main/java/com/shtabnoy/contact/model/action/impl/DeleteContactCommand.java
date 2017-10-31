package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DeleteContactCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteContactCommand.class);
    private static final String CHECKBOX_SELECTED_CONTACTS = "selectedContacts";
    private static final String ERROR_PAGE = "/main/error";
    private static final String MAIN_PAGE = "/main/info?page=1";
    private static final String MAIN_SEARCH_PAGE = "/main/info?page=1&search=true";
    private static final int DISPLACEMENT = 0;
    private static final int CONTACT_COUNT_ON_PAGE = 10;
    private static final String CONTACTS_ATTRIBUTE = "contactsList";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Delete command starts work!");

        String listId[] = request.getParameterValues(CHECKBOX_SELECTED_CONTACTS);
        LOGGER.info("Get selected contacts: " + Arrays.toString(listId));

        HttpSession session = request.getSession();
        Contact contact = (Contact)session.getAttribute("searchSession");
        LOGGER.info("Get contact from session: " + contact);
        List<Contact> contactsList;

        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
            if (contactDAO == null) {
                response.sendRedirect(ERROR_PAGE);
            } else {
               deleteContacts(listId, contactDAO);

               if("true".equals(request.getParameter("search"))) {
                    contactsList = ((ContactDAOImpl) contactDAO).searchContacts(contact, DISPLACEMENT, CONTACT_COUNT_ON_PAGE);
                    request.setAttribute("flag", "true");
                    request.setAttribute(CONTACTS_ATTRIBUTE, contactsList);
                    response.sendRedirect(MAIN_SEARCH_PAGE);
               } else {
                    contactsList = contactDAO.findContacts(DISPLACEMENT, CONTACT_COUNT_ON_PAGE);
                    request.setAttribute(CONTACTS_ATTRIBUTE, contactsList);
                    response.sendRedirect(MAIN_PAGE);
               }
            }
        } catch (SQLException | IOException e) {
           LOGGER.error(e.getMessage());
            response.sendRedirect(ERROR_PAGE);
        }
    }


    private void deleteContacts(String[] listId, DAO<Contact, Integer> contactDAO) throws SQLException {
        for (int i = 0; i < listId.length; i++) {
            contactDAO.delete(Integer.valueOf(listId[i]));
        }
        LOGGER.info("Contacts with id: " + Arrays.toString(listId) + "were deleted successfully!");
    }
}
