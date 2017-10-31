package com.shtabnoy.contact.model.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class EmailTemplateCreator {

    private static final Logger LOGGER = Logger.getLogger(EmailTemplateCreator.class);

    public String createTemplate(EmailTemplateEnum emailTemplate, Map<String, String> emailParamsMap){
        Configuration configuration = prepareConfiguration();
        URL templateFolderUri = getClass().getResource("/email-templates");
        try {
            configuration.setDirectoryForTemplateLoading(new File(new URI(templateFolderUri.toString())));
        } catch (IOException | URISyntaxException e) {
           LOGGER.error(e.getMessage());
        }
        Writer out = null;
        try {
            Template template = configuration.getTemplate(emailTemplate.getTemplateFileName());
            out = new StringWriter();
            template.process(emailParamsMap, out);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        return out.toString();
    }
    private Configuration prepareConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        configuration.setLogTemplateExceptions(false);
        return configuration;
    }
}
