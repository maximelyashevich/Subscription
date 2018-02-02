package com.elyashevich.subscription.command;

import com.elyashevich.subscription.command.client.CommandType;
import com.elyashevich.subscription.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType. valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            request.setAttribute("wrongAction", action
                    +  MessageManager.EN.getMessage("message.wrongaction"));
        }
        return current;
    }
}