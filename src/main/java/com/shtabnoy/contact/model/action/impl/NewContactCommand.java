package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.ContactParameter;
import com.shtabnoy.contact.model.utility.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewContactCommand implements Command{
    private static final String NEW_CONTACT_PAGE = "path.page.jsp.create_contact";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactParameter param = new ContactParameter();
        request.setAttribute("countries", param.getCountries());
        request.setAttribute("nationalities", param.getNationalities());
        request.getRequestDispatcher(PageManager.getPage(NEW_CONTACT_PAGE)).forward(request,response);
    }
}
