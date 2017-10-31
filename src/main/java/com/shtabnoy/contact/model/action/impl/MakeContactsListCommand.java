package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.exception.RequestAttributeException;
import com.shtabnoy.contact.model.factory.DAOFactory;
import com.shtabnoy.contact.model.utility.PageManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakeContactsListCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(MakeContactsListCommand.class);
    private static final String PAGE_ATTRIBUTE = "page";
    private static final String ERROR_PAGE = "/main/error";
    private static final String MAIN_PAGE = "path.page.jsp.main";
    private static final String CONTACTS_LIST_ATTRIBUTE = "contactsList";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private int contactsCountOnPage = 10;
    private int pageCount;
    private ArrayList<Integer> pageNumbers = new ArrayList<Integer>();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Make contacts list command starts work!");
        String page;
        int selectedPage;

        HttpSession session = request.getSession();
        Contact contact = (Contact) session.getAttribute("searchSession");
        LOGGER.info("Get contact from session: " + contact);
        List<Contact> contactsList;

        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
            if (contactDAO == null) {
                response.sendRedirect(ERROR_PAGE);
            } else {
                int totalCountContacts = contactDAO.getCountContacts();
                LOGGER.info("Total count contacts: " + totalCountContacts);
                fillPageNumbers(totalCountContacts);
                selectedPage = checkPageAttribute(request);
                int displacement = (selectedPage - 1) * contactsCountOnPage;

                if ("true".equals(request.getParameter("search"))) {
                    contactsList = ((ContactDAOImpl) contactDAO).searchContacts(contact, displacement, contactsCountOnPage);
                    int count = ((ContactDAOImpl) contactDAO).getCountSearchedContacts(contact);
                    LOGGER.info("Total count of searched contacts: " + count);
                    fillPageNumbers(count);
                    request.setAttribute("flag", "true");
                } else {
                    contactsList = contactDAO.findContacts(displacement, contactsCountOnPage);
                    LOGGER.info("Get contacts from database: " + contactsList);
                }
                request.setAttribute(CONTACTS_LIST_ATTRIBUTE, contactsList);
                request.setAttribute(PAGE_NUMBERS, pageNumbers);
                LOGGER.info("Contact's list was send!");

                page = PageManager.getPage(MAIN_PAGE);
                request.getRequestDispatcher(page).forward(request, response);

            }
        } catch (RequestAttributeException | ServletException | IOException | SQLException e) {
            LOGGER.error(e.getMessage());
            response.sendRedirect(ERROR_PAGE);
        }
    }


    private void fillPageNumbers(int totalContactsCount) {
        pageCount = contactsCountOnPage > 0 ? (int) (totalContactsCount / contactsCountOnPage) : 0;
        if ((totalContactsCount % contactsCountOnPage) > 0) {
            pageCount++;
        }
        pageNumbers.clear();
        for (int i = 0; i < pageCount; i++) {
            pageNumbers.add(i);
        }
    }

    private int checkPageAttribute(HttpServletRequest request) throws RequestAttributeException {
        int selectedPage;
        if (request.getParameter("page") == null) {
            selectedPage = 1;
        } else {
            if (Integer.valueOf(request.getParameter("page")) > pageCount) {
                throw new RequestAttributeException();
            } else
                selectedPage = Integer.valueOf(request.getParameter("page"));
        }
        LOGGER.info("User on page(pagination): " + selectedPage);
        return selectedPage;
    }
}
