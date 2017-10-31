package com.shtabnoy.contact.controller.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Properties;

@WebServlet(name = "Image")
public class ImageServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(ImageServlet.class);

    private static String UPLOAD_DIRECTORY_FOR_DOCUMENTS;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("ImageServlet starts work!");
        InputStream fis;
        Properties property = new Properties();

        String id = request.getParameter("imageId");
        String image = request.getParameter("im");
        LOGGER.info("Get image: " + image);

        response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Cache-Control", "max-age=0");
        response.setHeader("Pragma", "no-cache");
        File file = null;
        try {
            fis = getClass().getClassLoader().getResourceAsStream("download_folder.properties");
            property.load(fis);
            UPLOAD_DIRECTORY_FOR_DOCUMENTS = property.getProperty("folder.path");

            if (image.equals("empty_profile_image.jpg")) {
                file = new File(getClass().getClassLoader().getResource("images/empty_profile_image.jpg").toURI());
            } else {
                file = new File(UPLOAD_DIRECTORY_FOR_DOCUMENTS + File.separator + id + File.separator + image);
            }
        }
        catch (URISyntaxException e) {
           LOGGER.error(e.getMessage());
        }
        response.setHeader("Content-Type","image/jpeg");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getPath() + "\"");
        response.getOutputStream().write(Files.readAllBytes(file.toPath()));
        LOGGER.info("Image was send!");
        response.getOutputStream().flush();
    }
}
