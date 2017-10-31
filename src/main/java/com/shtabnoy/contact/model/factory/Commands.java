package com.shtabnoy.contact.model.factory;


import com.shtabnoy.contact.model.action.*;
import com.shtabnoy.contact.model.action.impl.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private final static Logger LOGGER = Logger.getLogger(Commands.class);
    private static Map<String, Class> processors = new HashMap<>();

    static {
        processors.put("info", MakeContactsListCommand.class);
        processors.put("delete", DeleteContactCommand.class);
        processors.put("search", SearchRedirectCommand.class);
        processors.put("result", SearchContactCommand.class);
        processors.put("create", CreateContactCommand.class);
        processors.put("edit", EditContactCommand.class);
        processors.put("save", SaveEditInfoCommand.class);
        processors.put("send_email", SendEmailCommand.class);
        processors.put("attachment", DownloadAttachmentCommand.class);
        processors.put("new", NewContactCommand.class);
        processors.put("error", ErrorRedirectCommand.class);
    }

    public static Command getRequestProcessor(String command) {
        Class processorClass = processors.get(command);
        if (processorClass != null) {
            try {
                return (Command) processorClass.newInstance();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }
}
