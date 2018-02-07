package com.elyashevich.subscription.command;


import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();
        request.getSession().setAttribute(TextConstant.USER_LOCALE, request.getSession().getAttribute(TextConstant.USER_LOCALE));
        router.setPagePath(page);
        LOGGER.log(Level.INFO, "Successful log out.");
        return router;
    }
}
