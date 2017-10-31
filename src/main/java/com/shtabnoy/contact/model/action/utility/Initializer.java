package com.shtabnoy.contact.model.action.utility;

import com.shtabnoy.contact.model.entity.Contact;

import javax.servlet.http.HttpServletRequest;

public class Initializer {

    public static Contact initializeContactFromRequest(HttpServletRequest request){
        Contact contact = new Contact();

        contact.setName(request.getParameter("name"));
        contact.setSurname(request.getParameter("surname"));
        contact.setDate(request.getParameter("date"));
        contact.setDateFrom(request.getParameter("dateFrom"));
        contact.setToDate(request.getParameter("toDate"));
        contact.setSex(request.getParameter("sex"));
        contact.setNationality(request.getParameter("nationality"));
        contact.setFamily_status(request.getParameter("family_status"));
        contact.setWebSite(request.getParameter("webSite"));
        contact.setEmail(request.getParameter("email"));
        contact.setWorkPlace(request.getParameter("work"));
        contact.setCountry(request.getParameter("country"));
        contact.setCity(request.getParameter("city"));
        contact.setStreet(request.getParameter("street"));
        contact.setHome(request.getParameter("home"));
        contact.setIndex(request.getParameter("index"));

        return contact;

    }


}
