package com.elyashevich.subscription.command;


import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        router.setPagePath(page);
        request.getSession().invalidate();
        request.getSession().setAttribute(TextConstant.USER_LOCALE, request.getSession().getAttribute("userLocale"));
        return router;
    }
}
