package com.elyashevich.subscription.command;

import com.elyashevich.subscription.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LocaleCommand implements ActionCommand {
    private static final String PARAM_NAME_LANGUAGE = "locale_language";
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.index");
        String language = request.getParameter(PARAM_NAME_LANGUAGE);
        request.getSession().setAttribute("userLocale", language);
       return page;
    }
}
