package com.elyashevich.subscription.command;

import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class LocaleCommand implements ActionCommand {
    private static final String PARAM_NAME_LANGUAGE = "locale_language";
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        String language = request.getParameter(PARAM_NAME_LANGUAGE);
        request.getSession().setAttribute("userLocale", language);
        router.setPagePath(page);
       return router;
    }
}
