package com.shtabnoy.contact.model.utility;

import java.util.HashMap;


public class PageManager {
    private static HashMap<String, String> pagesContainer = new HashMap<>(); ;

    static {
        pagesContainer.put("path.page.jsp.main", "/main_page.jsp");
        pagesContainer.put("path.page.jsp.error", "/error_page.jsp");
        pagesContainer.put("path.page.jsp.search", "/search_contact.jsp");
        pagesContainer.put("path.page.jsp.create_contact", "/new_contact.jsp");
        pagesContainer.put("path.page.jsp.edit", "/edit_contact_page.jsp");
        pagesContainer.put("path.page.jsp.send_email", "/send_email_page.jsp");
        pagesContainer.put("path.page.jsp.error", "/error_page.jsp");
    }

    public static String getPage(String key) {
        return pagesContainer.get(key);
    }


}
