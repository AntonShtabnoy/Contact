package com.shtabnoy.contact.controller.servlet;

import com.shtabnoy.contact.controller.utility.IdentifySelectedPage;
import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.factory.Commands;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ContactFrontController")
public class ContactFrontController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ContactFrontController.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Function processRequest starts!");
        LOGGER.info("Get path: " + request.getPathInfo());

        IdentifySelectedPage identifySelectedPage =  new IdentifySelectedPage(request.getPathInfo());
        LOGGER.info("Get page: " + identifySelectedPage.getPage());

        Command command = Commands.getRequestProcessor(identifySelectedPage.getPage());

        if(command != null) {
            command.execute(request, response);
        }

    }








}
