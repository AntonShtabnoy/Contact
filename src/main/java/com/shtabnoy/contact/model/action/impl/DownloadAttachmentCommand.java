package com.shtabnoy.contact.model.action.impl;

import com.shtabnoy.contact.model.action.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DownloadAttachmentCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(DownloadAttachmentCommand.class);
    private static String UPLOAD_DIRECTORY_FOR_DOCUMENTS;
    private static final String DOWNLOAD_FOLDER_PROPERTIES = "download_folder.properties";
    private static final String DOWNLOAD_FOLDER_PATH_PROPERTY  = "folder.path";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Download attachment command starts work!");
        InputStream fis;
        Properties property = new Properties();
        fis = getClass().getClassLoader().getResourceAsStream(DOWNLOAD_FOLDER_PROPERTIES);
        property.load(fis);
        UPLOAD_DIRECTORY_FOR_DOCUMENTS = property.getProperty(DOWNLOAD_FOLDER_PATH_PROPERTY);

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        LOGGER.info("Get attachment name: " + name);

        File attachment = new File(UPLOAD_DIRECTORY_FOR_DOCUMENTS + File.separator + id + File.separator + name);
        ServletOutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(attachment);

        int length;
        byte[] buffer = new byte[4096];
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
        out.close();

        response.setContentType("application/download;");
        //response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Length", String.valueOf(attachment.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getPath() + "\"");

    }
}

