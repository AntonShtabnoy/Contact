package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import com.shtabnoy.contact.model.action.utility.Validator;
import com.shtabnoy.contact.model.dao.DAO;
import com.shtabnoy.contact.model.dao.impl.AttachmentDAOImpl;
import com.shtabnoy.contact.model.dao.impl.ContactDAOImpl;
import com.shtabnoy.contact.model.entity.Attachment;
import com.shtabnoy.contact.model.entity.Contact;
import com.shtabnoy.contact.model.entity.Phone;
import com.shtabnoy.contact.model.factory.DAOFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SaveEditInfoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SaveEditInfoCommand.class);
    private static final String ERROR_PAGE = "/main/error";
    private static final String EDIT_PAGE = "path.page.jsp.edit";
    private static final String CONTACT_PROFILE_ATTRIBUTE = "contactProfile";
    private static final String ATTRIBUTE_FOR_ID = "contact";
    private static final String DOWNLOAD_FOLDER_PROPERTIES = "download_folder.properties";
    private static final String DOWNLOAD_FOLDER_PATH_PROPERTY = "folder.path";
    private static final int MIN_SIZE_NAME_FILE = 4;
    private static String UPLOAD_DIRECTORY_FOR_DOCUMENTS;
    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatePhones = new ArrayList<>();
    private ArrayList<Integer> deletePhones = new ArrayList<>();
    private ArrayList<Attachment> newAttachments = new ArrayList<>();
    private ArrayList<Attachment> updateAttachments = new ArrayList<>();
    private ArrayList<Integer> deleteAttachments = new ArrayList<>();
    private static ArrayList<String> extensions = new ArrayList<>();
    private Attachment attachment;
    private Contact contact;
    private Phone phone;
    private static int lastId;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("Save contact command starts work!");

        String page = null;
        String id = request.getParameter("contact");
        InputStream fis;
        Properties property = new Properties();

        try {
            DAOFactory mySqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            DAO<Contact, Integer> contactDAO = mySqlFactory.getContactDAO();
            DAO<Attachment, Integer> attachmentDAO = mySqlFactory.getAttachmentDAO();
            DAO<Phone, Integer> phoneDAO = mySqlFactory.getPhoneDAO();
            fis = getClass().getClassLoader().getResourceAsStream(DOWNLOAD_FOLDER_PROPERTIES);
            property.load(fis);
            UPLOAD_DIRECTORY_FOR_DOCUMENTS = property.getProperty(DOWNLOAD_FOLDER_PATH_PROPERTY);

            int idInt = Integer.valueOf(request.getParameter(ATTRIBUTE_FOR_ID));
            String defaultImage = ((ContactDAOImpl) contactDAO).getImageName(idInt);
            LOGGER.info("Get image contact: " + defaultImage);

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (attachmentDAO == null || phoneDAO == null) {
                response.sendRedirect(ERROR_PAGE);
            } else {
                contact = new Contact();
                contact.setImage(defaultImage);

                if (!isMultipart) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    LOGGER.error("No multipart");
                    return;
                }

                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> multiparts = upload.parseRequest(request);
                extensions.clear();

                Map<String, String> mistakeMap = Validator.validateSaveCommand(multiparts);

                if (mistakeMap.size() != 0) {
                    Map<String, String> paramMap = Validator.getInfoMap();
                    LOGGER.info("Edit page has some mistakes: " + paramMap);
                    request.setAttribute("paramMap", paramMap);
                    request.setAttribute("mistakeMap", mistakeMap);
                    List<Phone> phones = phoneDAO.findContacts(idInt, 0);
                    List<Attachment> attachments = attachmentDAO.findContacts(idInt, 0);
                    Contact contact = contactDAO.getContact(idInt);
                    request.setAttribute("contactProfile", contact);
                    request.setAttribute("phones", phones);
                    request.setAttribute("attachments", attachments);
                    request.getRequestDispatcher("/edit_contact_page.jsp").forward(request, response);
                } else {
                    lastId = ((AttachmentDAOImpl) attachmentDAO).getLastAttachmentId();
                    for (FileItem item : multiparts) {
                        if (!item.isFormField() && item.getName().length() > MIN_SIZE_NAME_FILE) {
                            String image = request.getParameter("contact") + getTypeFile(item);
                            processUploadedFile(item, id, image);
                        } else {
                            processFormField(item, idInt);
                        }
                    }
                    contact.setId(idInt);
                    contactDAO.update(contact);
                    LOGGER.info("Contact: " + contact + "was updated!");

                    insertAttachments(attachmentDAO);
                    refreshAttachments(attachmentDAO);
                    removeAttachments(attachmentDAO);

                    insertPhones(phoneDAO);
                    refreshPhones(phoneDAO);
                    removePhones(phoneDAO);

                    LOGGER.info("All were saved or updated or deleted!");
                    response.sendRedirect("/main/edit?contact=" + id);

                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void savePhoto(FileItem item, String id) throws Exception {
        String fileExtension = getTypeFile(item);
        item.write(new File(UPLOAD_DIRECTORY_FOR_DOCUMENTS + File.separator + id + File.separator + id + fileExtension));
        LOGGER.info("Photo "+ item.getName()+ "is in file system!");
    }

    private static void saveAttachment(FileItem item, String id) throws Exception {
        StringBuilder path = new StringBuilder(UPLOAD_DIRECTORY_FOR_DOCUMENTS);
        String fileExtension = getTypeFile(item);
        extensions.add(fileExtension);
        path.append(File.separator).append(id).append(File.separator).append(id).append("-").append(++lastId).append("attachment").append(fileExtension);
        item.write(new File(String.valueOf(path)));
        LOGGER.info("Attachment "+ item.getName()+ "is in file system!");
    }

    private static String parseFileName(String path) {
        int lastIndex = path.lastIndexOf('\\');
        if (lastIndex != -1) {
            return path.substring(lastIndex + 1, path.length());
        }
        return path;
    }

    private void processUploadedFile(FileItem item, String id, String image) throws Exception {
        File folder = new File(UPLOAD_DIRECTORY_FOR_DOCUMENTS + File.separator + id);
        createFolder(folder);
        if (item.getFieldName().equals("photoInputName")) {
            savePhoto(item, id);
            contact.setImage(image);
        }
        if (item.getFieldName().equals("attachmentInputName")) {
            saveAttachment(item, id);
            LOGGER.info("Attachment is in file system!");
        }
    }

    private static String getTypeFile(FileItem item) {
        String fileName = item.getName();
        Pattern pattern = Pattern.compile(".\\w+$");
        Matcher matcher = pattern.matcher(fileName);
        matcher.find();
        return matcher.group();
    }

    private void insertAttachments(DAO<Attachment, Integer> attachmentDAO) throws SQLException {
        int i = 0;
        for (Attachment attach : newAttachments) {
            attach.setType(extensions.get(i));
            i++;
            attachmentDAO.insert(attach);
            LOGGER.info("Attachment " + attach + "was inserted!");
        }
    }

    private void refreshAttachments(DAO<Attachment, Integer> attachmentDAO) throws SQLException {
        for (Attachment attach : updateAttachments) {
            if (attach.getId() != 0) {
                attachmentDAO.update(attach);
                LOGGER.info("Attachment " + attach + "was updated!");
            }
        }
    }

    private void removeAttachments(DAO<Attachment, Integer> attachmentDAO) throws SQLException {
        for (Integer attach : deleteAttachments) {
            attachmentDAO.delete(attach);
            LOGGER.info("Attachment " + attach + "was deleted!");
        }
    }

    private void insertPhones(DAO<Phone, Integer> phoneDAO) throws SQLException {
        for (Phone ph : newPhones) {
            phoneDAO.insert(ph);
            LOGGER.info("Phone " + ph + "was inserted!");
        }
    }

    private void refreshPhones(DAO<Phone, Integer> phoneDAO) throws SQLException {
        for (Phone ph : updatePhones) {
            if (ph.getId() != 0) {
                phoneDAO.update(ph);
                LOGGER.info("Phone " + ph + "was updated!");
            }
        }
    }

    private void removePhones(DAO<Phone, Integer> phoneDAO) throws SQLException {
        for (Integer ph : deletePhones) {
            phoneDAO.delete(ph);
            LOGGER.info("Phone " + ph + "was deleted!");
        }
    }

    private void processFormField(FileItem item, int idInt) {
        String fieldName = item.getFieldName();
        String data = item.getString();
        switch (fieldName) {
            case "name":
                contact.setName(data);
                break;
            case "surname":
                contact.setSurname(data);
                break;
            case "date":
                contact.setDate(data);
                break;
            case "sex":
                contact.setSex(data);
                break;
            case "nationality":
                contact.setNationality(data);
                break;
            case "family_status":
                contact.setFamily_status(data);
                break;
            case "webSite":
                contact.setWebSite(data);
                break;
            case "email":
                contact.setEmail(data);
                break;
            case "work":
                contact.setWorkPlace(data);
                break;
            case "country":
                contact.setCountry(data);
                break;
            case "city":
                contact.setCity(data);
                break;
            case "street":
                contact.setStreet(data);
                break;
            case "home":
                contact.setHome(data);
                break;
            case "index":
                contact.setIndex(data);
                break;
            case "attachmentName":
                attachment = new Attachment();
                attachment.setName(data);
                break;
            case "attachmentDate":
                attachment.setDate(data);
                break;
            case "attachmentComment":
                attachment.setComment(data);
                attachment.setContactId(idInt);
                updateAttachments.add(attachment);
                break;
            case "newAttachmentName":
                attachment = new Attachment();
                attachment.setName(data);
                break;
            case "editIdAttachment":
                attachment.setId(Integer.valueOf(data));
                break;
            case "newAttachmentDate":
                attachment.setDate(data);
                break;
            case "newAttachmentComment":
                attachment.setComment(data);
                attachment.setContactId(idInt);
                newAttachments.add(attachment);
                break;
            case "phoneNumber":
                phone = new Phone();
                phone.setNumber(data);
                break;
            case "editIdPhone":
                phone.setId(Integer.valueOf(data));
                break;
            case "phoneType":
                phone.setType(data);
                break;
            case "phoneComment":
                phone.setComment(data);
                phone.setContactId(idInt);
                updatePhones.add(phone);
                break;
            case "newPhoneNumber":
                phone = new Phone();
                phone.setNumber(data);
                break;
            case "newPhoneType":
                phone.setType(data);
                break;
            case "newPhoneComment":
                phone.setComment(data);
                phone.setContactId(idInt);
                newPhones.add(phone);
                break;
            case "deletedPhoneInputName":
                deletePhones.add(Integer.valueOf(data));
                break;
            case "deletedAttachmentInputName":
                deleteAttachments.add(Integer.valueOf(data));
                break;

        }

    }
    private static void createFolder(File folder) {
        if (!folder.exists()) {
            boolean f = folder.mkdir();
            if (f) {
                LOGGER.info("Folder is created!");
            } else LOGGER.info("Folder was't created!");
        }
    }


}
