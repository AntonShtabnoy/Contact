package com.shtabnoy.contact.model.action.utility;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ContactParameter {
    private static final Logger LOGGER = Logger.getLogger(ContactParameter.class);
    private Map<String, String> countries = new TreeMap<>();
    private Map<String, String> nationalities = new TreeMap<>();

    public Map<String, String> getCountries() {
        return countries;
    }
    public Map<String, String> getNationalities() {
        return nationalities;
    }

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String DATE = "date";
    public static final String DATE_FROM = "dateFrom";
    public static final String TO_DATE = "toDate";
    public static final String SEX = "sex";
    public static final String NATIONALITY = "nationality";
    public static final String FAMILY_STATUS = "family_status";
    public static final String EMAIL = "email";
    public static final String WEBSITE = "webSite";
    public static final String WORK = "work";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String STREET = "street";
    public static final String HOME = "home";
    public static final String INDEX = "index";
    public static final String ATTACHMENT_NAME = "attachmentName";
    public static final String NEW_ATTACHMENT_NAME = "newAttachmentName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String NEW_PHONE_NUMBER = "newPhoneNumber";
    public static final String RECEIVER_NAME = "receiverName";
    public static final String RECEIVER_SURNAME = "receiverLastName";
    public static final String LASTNAME = "lastName";
    public static final String PHONE = "phone";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    public ContactParameter() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(getClass().getClassLoader().getResource("countries.txt").toURI()));
            while (scanner.hasNext()) {
                String key = scanner.next();
                String value = null;
                if (scanner.hasNext()) {
                    value = scanner.next();
                }
                countries.put(key, value);
            }
            scanner = new Scanner(new File(getClass().getClassLoader().getResource("nationalities.txt").toURI()));
            while (scanner.hasNext()) {
                String key = scanner.next();
                String value = null;
                if (scanner.hasNext()) {
                    value = scanner.next();
                }
                nationalities.put(key, value);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
        finally {
            if (scanner!=null){
                scanner.close();
            }
        }
    }

}
