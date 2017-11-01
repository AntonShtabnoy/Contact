package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.Initializer;
import com.shtabnoy.contact.model.action.utility.Validator;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchContactCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SearchContactCommand.class);
    private static final String ERROR_PAGE = "/main/error";
    private static final String SEARCH_PAGE = "path.page.jsp.search";
    private static final String RESULT_SEARCH_ATTRIBUTE = "contactsList";
    private final static String PAGE_NUMBERS = "pageNumbers";
    private int contactsCountOnPage = 10;
    private int displacement;
    private ArrayList<Integer> pageNumbers = new ArrayList<>();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Search contact command starts work!");
        String page;

        Map<String, String> mistakeMap = Validator.validateSearchCommand(request);

        if (mistakeMap.size() != 0) {
            Map<String, String> paramMap = Validator.getInfoMap();
            LOGGER.info("Search page has some mistakes: " + paramMap);
            request.setAttribute("paramMap", paramMap);
            request.setAttribute("mistakeMap", mistakeMap);
            request.getRequestDispatcher("/search_contact.jsp").forward(request, response);
        } else {
            try {
                Contact contact = Initializer.initializeContactFromRequest(request);
                LOGGER.info("User wants to search contact: "+ contact);
                DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
                DAO<Contact, Integer> contactDAO = null;
                if (mySqlFactory != null) {
                    contactDAO = mySqlFactory.getContactDAO();
                }
                if (contactDAO == null) {
                    response.sendRedirect(ERROR_PAGE);
                } else {
                    List<Contact> resultListOfSearch = ((ContactDAOImpl) contactDAO).searchContacts(contact, displacement, contactsCountOnPage);
                    LOGGER.info("Contacts were found: " + resultListOfSearch);
                    HttpSession session = request.getSession();
                    session.setAttribute("searchSession", contact);
                    LOGGER.info("Contact: " + contact + "was set in session!");

                    request.setAttribute(RESULT_SEARCH_ATTRIBUTE, resultListOfSearch);
                    request.setAttribute("flag", "true");
                    fillPageNumbers(((ContactDAOImpl) contactDAO).getCountSearchedContacts(contact));
                    request.setAttribute(PAGE_NUMBERS, pageNumbers);
                    response.sendRedirect("/main/info?page=1&search=true");
                }

            } catch (IOException | SQLException e) {
                LOGGER.error(e.getMessage());
                response.sendRedirect(ERROR_PAGE);
            }
        }
    }


    private void fillPageNumbers(int totalContactsCount) {
        int pageCount = contactsCountOnPage > 0 ? totalContactsCount / contactsCountOnPage : 0;
        if ((totalContactsCount % contactsCountOnPage) > 0) {
            pageCount++;
        }
        pageNumbers.clear();
        for (int i = 0; i < pageCount; i++) {
            pageNumbers.add(i);
        }
    }
}
