package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.ContactParameter;
import com.shtabnoy.contact.model.action.utility.Initializer;
import com.shtabnoy.contact.model.action.utility.Validator;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.factory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CreateContactCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateContactCommand.class);
    private static final String ERROR_PAGE = "/main/error";
    private static final String MAIN_PAGE = "/main/info?page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Create contact command starts work!");

        ContactParameter param = new ContactParameter();
        request.setAttribute("countries", param.getCountries());
        request.setAttribute("nationalities", param.getNationalities());
        LOGGER.info("Country and nationalities attributes were installed!");
        Map<String, String> mistakeMap = Validator.validateCreateCommand(request);

        if (mistakeMap.size() != 0) {
            Map<String, String> paramMap = Validator.getInfoMap();
            LOGGER.info("Create page has some mistakes: " + paramMap);
            request.setAttribute("paramMap", paramMap);
            request.setAttribute("mistakeMap", mistakeMap);
            request.getRequestDispatcher("/new_contact.jsp").forward(request, response);
        } else {
            try {
                DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
                DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
                if (contactDAO == null) {
                    response.sendRedirect(ERROR_PAGE);
                } else {
                    Contact contact = Initializer.initializeContactFromRequest(request);
                    contactDAO.insert(contact);

                    LOGGER.info("Contact " + contact + " has been inserted!");
                    response.sendRedirect(MAIN_PAGE);

                }
            } catch (IOException | SQLException e) {
                LOGGER.error(e.getMessage());
                response.sendRedirect(ERROR_PAGE);
            }
        }
    }
}
