package com.shtabnoy.contact.controller.utility;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifySelectedPage {
    private String page;
    private List<Pattern> patterns;
    private static final Logger LOGGER = Logger.getLogger(IdentifySelectedPage.class);

    {
        patterns = new ArrayList<>();
        patterns.add(Pattern.compile("/create"));
        patterns.add(Pattern.compile("/delete"));
        patterns.add(Pattern.compile("/edit"));
        patterns.add(Pattern.compile("/info"));
        patterns.add(Pattern.compile("/save"));
        patterns.add(Pattern.compile("/search"));
        patterns.add(Pattern.compile("/result"));
        patterns.add(Pattern.compile("/send_email"));
        patterns.add(Pattern.compile("/update"));
        patterns.add(Pattern.compile("/add_attachment"));
        patterns.add(Pattern.compile("/error"));
        patterns.add(Pattern.compile("/attachment"));
        patterns.add(Pattern.compile("/new"));
    }


    public IdentifySelectedPage(final String pathInfo) throws ServletException {
        LOGGER.info("Start work with " + pathInfo);
        Matcher matcher;
        for (Pattern pattern: patterns) {
            matcher = pattern.matcher(pathInfo);
            if(matcher.find()){
                page = matcher.group(0).substring(1);
                if(page.contains("/")){
                    int indexOfSlash = page.indexOf("/");
                    page = page.substring(0, indexOfSlash);
                }
                LOGGER.info("Get page: " + page);
                return;
            }
        }
        throw new ServletException("Invalid URI");
    }

    public String getPage() {
        return page;
    }

}
