package com.elyashevich.subscription.factory;

import com.elyashevich.subscription.command.ActionCommand;
import com.elyashevich.subscription.command.EmptyCommand;
import com.elyashevich.subscription.command.client.CommandEnum;
import com.elyashevich.subscription.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum. valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager. getProperty("message.wrongaction"));
        }
        return current;
    }
}