package com.elyashevich.subscription.command;

import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class LocaleCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        LocaleService localeService = new LocaleService();
        Router router = new Router();
        String page = null;
        page = localeService.definePath(request.getParameter(TextConstant.PATH_PAGE));

        String language = request.getParameter(TextConstant.PARAM_NAME_LANGUAGE);
        request.getSession().setAttribute(TextConstant.USER_LOCALE, localeService.defineLanguage(language));
        router.setPagePath(request.getContextPath()+page);
       return router;
    }
}
