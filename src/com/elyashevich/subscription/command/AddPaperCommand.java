package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.DefaultService;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.MailService;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
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
    private PaperService paperService;

    AddPaperCommand(PaperService paperService) {
        this.paperService = paperService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page;
        PaperType paperType;
        ArrayList<User> userList;

        DefaultService defaultService = new DefaultService();
        LocaleService localeService = new LocaleService();
        MailService mailService = new MailService();

        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeService.defineMessageManager(userLocale);
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
        String period = request.getParameter(TextConstant.PERIOD);
        String price = request.getParameter(TextConstant.PRICE);
        String restriction = request.getParameter(TextConstant.RESTRICTION);
        String titleMessage = defaultService.defineAddTitleMessage(paperTitle, description, price, restriction);

        LOGGER.log(Level.INFO, "Try to add new paper edition...");

        request.getSession().setAttribute(TextConstant.TITLE_ADD_TO_BASKET, localeService.defineMessageManager(userLocale).getMessage(titleMessage));
        page = ConfigurationManager.getProperty("path.page.addadmin");
        if (TextConstant.SUCCESS_OPERATION.equals(titleMessage)) {
            BigDecimal priceReal = new BigDecimal(price);
            int restrictionInt = Integer.parseInt(restriction);
            int periodInt = period != null ? Integer.parseInt(period) : 1;
            String[] checkValues = request.getParameterValues(TextConstant.CHECK_BUTTON);
            if (checkValues==null){
                checkValues = new String[]{"Семья. Дом. Быт. Досуг"};
            }
            try {
                PaperEdition paperEdition = paperService.getPaper(paperType, paperTitle, description, periodInt, priceReal, restrictionInt);
                if (paperService.createPaperEdition(paperEdition, checkValues)) {
                    LOGGER.log(Level.INFO, "Try to send message to all users...");
                    mailService.sendAllUsersMessage(context, userList, messageManager);
                    request.setAttribute("titleMessage", messageManager.getMessage("message.registrationsuccess"));
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperService.findAll());
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
