package com.elyashevich.subscription.command;

import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.impl.LocaleServiceImpl;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(TextConstant.COMMAND_PARAM);
        LocaleServiceImpl localeServiceImpl = new LocaleServiceImpl();
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeServiceImpl.defineMessageManager(userLocale);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.catching(Level.ERROR, e);
            request.setAttribute(TextConstant.WRONG_ACTION, action
                    +  messageManager.getMessage("message.wrongaction"));
        }
        LOGGER.log(Level.INFO, "Successful recognize command");
        return current;
    }
}