package com.shtabnoy.contact.model.action.utility;

import com.shtabnoy.contact.model.entity.Contact;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
    private static final Logger LOGGER = Logger.getLogger(Validator.class);

    private static final String MISTAKE_MESSAGE = "Data is incorrect!";
    private static final String EMPTY_MESSAGE = "Field can't be empty!";
    private static final String EMPTY_FIELDS = "One of the fields should be fill in!";
    private static final String LITERAL_REGULAR_EXPRESSION = "(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)";
    private static final String EMAIL_REGULAR_EXPRESSION = "^([a-z0-9_.-]+)@([a-z0-9_.-]+)\\.([a-z.]{2,6})$";
    private static final String NUMERAL_REGULAR_EXPRESSION = "^[0-9]{1,15}$";
    private static final String NUMERAL_AND_LITERAL_EXPRESSION = "(^[0-9][-_а-яА-Яa-zA-Z0-9 ]{1,15}$)";

    private static Map<String, String> infoMap;
    private static Map<String, String> mistakeMap;
    private static int countEmptyFields;

    public static Map<String, String> validateCreateCommand(HttpServletRequest request) {
        mistakeMap = new HashMap<>();
        infoMap = new HashMap<>();
        Contact contact = Initializer.initializeContactFromRequest(request);
        infoMap.put(ContactParameter.WEBSITE, contact.getWebSite());

        validate(contact.getName(), LITERAL_REGULAR_EXPRESSION, ContactParameter.NAME);
        validate(contact.getSurname(), LITERAL_REGULAR_EXPRESSION, ContactParameter.SURNAME);
        validateDate(contact.getDate(), ContactParameter.DATE);
        validateEmpty(contact.getNationality(), ContactParameter.NATIONALITY);
        validateEmpty(contact.getFamily_status(), ContactParameter.FAMILY_STATUS);
        validateEmpty(contact.getSex(), ContactParameter.SEX);
        validate(contact.getEmail(), EMAIL_REGULAR_EXPRESSION, ContactParameter.EMAIL);
        validate(contact.getWorkPlace(), LITERAL_REGULAR_EXPRESSION, ContactParameter.WORK);
        validateEmpty(contact.getCountry(), ContactParameter.COUNTRY);
        validate(contact.getCity(), LITERAL_REGULAR_EXPRESSION, ContactParameter.CITY);
        validate(contact.getStreet(), LITERAL_REGULAR_EXPRESSION, ContactParameter.STREET);
        validate(contact.getHome(), NUMERAL_AND_LITERAL_EXPRESSION, ContactParameter.HOME);
        validateIndex(contact.getIndex(), NUMERAL_REGULAR_EXPRESSION, ContactParameter.INDEX);

        LOGGER.info("Mistake values: " + infoMap);
        return mistakeMap;
    }



    public static Map<String, String> getInfoMap() {
        return infoMap;
    }


    public static Map<String, String> validateSearchCommand(HttpServletRequest request) {
        mistakeMap = new HashMap<>();
        infoMap = new HashMap<>();
        countEmptyFields = 0;
        Contact contact = Initializer.initializeContactFromRequest(request);

        validateForSearch(contact.getName(), LITERAL_REGULAR_EXPRESSION, ContactParameter.NAME);
        validateForSearch(contact.getSurname(), LITERAL_REGULAR_EXPRESSION, ContactParameter.SURNAME);
        validateDateForSearch(contact.getDate(), ContactParameter.DATE);
        validateDateForSearch(contact.getDateFrom(), ContactParameter.DATE_FROM);
        validateDateForSearch(contact.getToDate(), ContactParameter.TO_DATE);
        validateEmptyForSearch(contact.getNationality(), ContactParameter.NATIONALITY);
        validateEmptyForSearch(contact.getFamily_status(), ContactParameter.FAMILY_STATUS);
        validateEmptyForSearch(contact.getSex(), ContactParameter.SEX);
        validateEmptyForSearch(contact.getWebSite(), ContactParameter.WEBSITE);
        validateForSearch(contact.getEmail(), EMAIL_REGULAR_EXPRESSION, ContactParameter.EMAIL);
        validateForSearch(contact.getWorkPlace(), LITERAL_REGULAR_EXPRESSION, ContactParameter.WORK);
        validateEmptyForSearch(contact.getCountry(), ContactParameter.COUNTRY);
        validateForSearch(contact.getCity(), LITERAL_REGULAR_EXPRESSION, ContactParameter.CITY);
        validateForSearch(contact.getStreet(), LITERAL_REGULAR_EXPRESSION, ContactParameter.STREET);
        validateForSearch(contact.getHome(), NUMERAL_AND_LITERAL_EXPRESSION, ContactParameter.HOME);
        validateForSearch(contact.getIndex(), NUMERAL_REGULAR_EXPRESSION, ContactParameter.INDEX);
        LOGGER.info("Count of empty fields: " + countEmptyFields);
        LOGGER.info("Mistake values: " + infoMap);
        if (countEmptyFields == 0) {
            mistakeMap.put("emptyFields", EMPTY_FIELDS);
        }
        return mistakeMap;
    }


    public static Map<String, String> validateSaveCommand(List<FileItem> fileItems) {
        mistakeMap = new HashMap<>();
        infoMap = new HashMap<>();

        for (FileItem item : fileItems) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String data = item.getString();
                switch (fieldName) {
                    case "name":
                        validate(data, LITERAL_REGULAR_EXPRESSION, ContactParameter.NAME);
                        break;
                    case "surname":
                        validate(data, LITERAL_REGULAR_EXPRESSION, ContactParameter.SURNAME);
                        break;
                    case "date":
                        validateDate(data, ContactParameter.DATE);
                        break;
                    case "sex":
                        validateEmpty(data, ContactParameter.SEX);
                        break;
                    case "nationality":
                        validateEmpty(data, ContactParameter.NATIONALITY);
                        break;
                    case "family_status":
                        validateEmpty(data, ContactParameter.FAMILY_STATUS);
                        break;
                    case "email":
                        validate(data, EMAIL_REGULAR_EXPRESSION, ContactParameter.EMAIL);
                        break;
                    case "work":
                        validate(data, LITERAL_REGULAR_EXPRESSION, ContactParameter.WORK);
                        break;
                    case "country":
                        validateEmpty(data, ContactParameter.COUNTRY);
                        break;
                    case "city":
                        validate(data, LITERAL_REGULAR_EXPRESSION, ContactParameter.CITY);
                        break;
                    case "street":
                        validate(data, LITERAL_REGULAR_EXPRESSION, ContactParameter.STREET);
                        break;
                    case "home":
                        validate(data, NUMERAL_AND_LITERAL_EXPRESSION, ContactParameter.HOME);
                        break;
                    case "index":
                        validateIndex(data, NUMERAL_REGULAR_EXPRESSION, ContactParameter.INDEX);
                        break;
                    case "attachmentName":
                        validateEmpty(data, ContactParameter.ATTACHMENT_NAME);
                        break;
                    case "newAttachmentName":
                        validateEmpty(data, ContactParameter.NEW_ATTACHMENT_NAME);
                        break;
                    case "phoneNumber":
                        validate(data, NUMERAL_REGULAR_EXPRESSION, ContactParameter.PHONE_NUMBER);
                        break;
                    case "newPhoneNumber":
                        validate(data, NUMERAL_REGULAR_EXPRESSION, ContactParameter.NEW_PHONE_NUMBER);
                        break;
                }
            }
        }

        LOGGER.info("Mistake values: " + infoMap);
        return mistakeMap;
    }




    private static void validate(String data, String regExp, String attribute) {
        infoMap.put(attribute, data);
        if ("".equals(data)) {
            mistakeMap.put(attribute, EMPTY_MESSAGE);
        } else if (!data.matches(regExp)) {
            mistakeMap.put(attribute, MISTAKE_MESSAGE);
        }
    }

    private static void validateForSearch(String data, String regExp, String attribute) {
        infoMap.put(attribute, data);
        if (!"".equals(data)) {
            countEmptyFields++;
            if (!data.matches(regExp)) {
                mistakeMap.put(attribute, MISTAKE_MESSAGE);
            }
        }
    }

    private static void validateEmpty(String data, String attribute) {
        infoMap.put(attribute, data);
        if ("".equals(data)) {
            mistakeMap.put(attribute, EMPTY_MESSAGE);
        }
    }

    private static void validateEmptyForSearch(String data, String attribute) {
        infoMap.put(attribute, data);
        if (!"".equals(data)) {
            countEmptyFields++;
        }
    }

    private static void validateDate(String data, String attribute) {
        infoMap.put(attribute, data);
        Date date = parseDate(data);
        Date currentDate = new Date();
        if (date.after(currentDate)) {
            mistakeMap.put(attribute, MISTAKE_MESSAGE);
        }
    }

    private static void validateDateForSearch(String data, String attribute) {
        infoMap.put(attribute, data);
        Date date = parseDate(data);
        Date currentDate = new Date();
        if (!"".equals(data)) {
            countEmptyFields++;
            if (date.after(currentDate)) {
                mistakeMap.put(attribute, MISTAKE_MESSAGE);
            }
        }
    }

    private static void validateIndex(String data, String regExp, String attribute) {
        infoMap.put(attribute, data);
        if (!"".equals(data)) {
            if (!data.matches(regExp)) {
                mistakeMap.put(attribute, MISTAKE_MESSAGE);
            }
        }
    }


    private static Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            if (!"".equals(dateStr))
                date = formatter.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        } finally {
            return date;
        }
    }

}