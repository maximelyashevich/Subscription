package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.impl.DefaultServiceImpl;
import com.elyashevich.subscription.service.impl.LocaleServiceImpl;
import com.elyashevich.subscription.service.impl.MailServiceImpl;
import com.elyashevich.subscription.service.impl.PaperServiceImpl;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.RegexComponent;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AddPaperCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private PaperServiceImpl paperServiceImpl;

    AddPaperCommand(PaperServiceImpl paperServiceImpl) {
        this.paperServiceImpl = paperServiceImpl;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page;
        PaperType paperType;
        ArrayList<User> userList;

        DefaultServiceImpl defaultServiceImpl = new DefaultServiceImpl();
        LocaleServiceImpl localeServiceImpl = new LocaleServiceImpl();
        MailServiceImpl mailServiceImpl = new MailServiceImpl();

        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeServiceImpl.defineMessageManager(userLocale);
        ServletContext context = request.getServletContext();
        String myRadio = request.getParameter(TextConstant.RADIO_BUTTON);
        userList = (ArrayList<User>) request.getSession().getAttribute(TextConstant.USERS_PARAM);
        switch (myRadio) {
            case TextConstant.NUMBER_ONE:
                paperType = PaperType.MAGAZINE;
                break;
            case TextConstant.NUMBER_TWO:
                paperType = PaperType.NEWSPAPER;
                break;
            case TextConstant.NUMBER_THREE:
                paperType = PaperType.BOOK;
                break;
            default:
                paperType = PaperType.NEWSPAPER;
                break;
        }
        String paperTitle = request.getParameter(TextConstant.PAPER_TITLE);
        String description = request.getParameter(TextConstant.PAPER_DESCRIPTION);
        description = description.replaceAll(RegexComponent.TABULATION_CONTROL, TextConstant.SPACE);
        String period = request.getParameter(TextConstant.PERIOD);
        String price = request.getParameter(TextConstant.PRICE);
        String restriction = request.getParameter(TextConstant.RESTRICTION);
        String titleMessage = defaultServiceImpl.checkAddTitleMessage(paperTitle, description, price, restriction);

        LOGGER.log(Level.INFO, "Try to add new paper edition...");

        request.getSession().setAttribute(TextConstant.TITLE_ADD_TO_BASKET, localeServiceImpl.defineMessageManager(userLocale).getMessage(titleMessage));
        page = ConfigurationManager.getProperty("path.page.addadmin");
        if (TextConstant.SUCCESS_OPERATION.equals(titleMessage)) {
            BigDecimal priceReal = new BigDecimal(price);
            int restrictionInt = Integer.parseInt(restriction);
            int periodInt = period != null ? Integer.parseInt(period) : 1;
            String[] checkValues = request.getParameterValues(TextConstant.CHECK_BUTTON);
            if (checkValues==null){
                checkValues = new String[]{TextConstant.DEFAULT_GENRE};
            }
            try {
                PaperEdition paperEdition = paperServiceImpl.definePaper(paperType, paperTitle, description, periodInt, priceReal, restrictionInt);
                if (paperServiceImpl.createPaperEdition(paperEdition, checkValues)) {
                    LOGGER.log(Level.INFO, "Try to send message to all users...");
                    mailServiceImpl.sendAllUsersMessage(context, userList, messageManager);
                    request.setAttribute(TextConstant.TITLE_PARAM, messageManager.getMessage("message.registrationsuccess"));
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperServiceImpl.findAll());
                    request.getSession().setAttribute(TextConstant.TITLE_ADD_TO_BASKET, TextConstant.EMPTY_STRING);
                    page = ConfigurationManager.getProperty("path.page.admin");
                    LOGGER.log(Level.INFO, "Successful creating new paper edition.");
                    LOGGER.log(Level.INFO, "Successful message sending.");
                } else {
                    request.getSession().setAttribute(TextConstant.ERROR_MESSAGE_PARAM, messageManager.getMessage("message.loginerror"));
                    LOGGER.log(Level.INFO, "Adding new paper edition has been failed.");
                }
            } catch (ServiceTechnicalException e) {
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
