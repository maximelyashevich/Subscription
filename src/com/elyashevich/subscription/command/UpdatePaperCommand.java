package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.impl.DefaultServiceImpl;
import com.elyashevich.subscription.service.impl.LocaleServiceImpl;
import com.elyashevich.subscription.service.impl.PaperServiceImpl;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.RegexComponent;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class UpdatePaperCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private PaperServiceImpl paperServiceImpl;

    UpdatePaperCommand(PaperServiceImpl paperServiceImpl) {
        this.paperServiceImpl = paperServiceImpl;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.admin");
        PaperEdition paperEdition;

        LocaleServiceImpl localeServiceImpl = new LocaleServiceImpl();
        DefaultServiceImpl defaultServiceImpl = new DefaultServiceImpl();
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeServiceImpl.defineMessageManager(userLocale);

        LOGGER.log(Level.INFO, "Start updating paper edition...");

        String paperTypeString = request.getParameter(TextConstant.PAPER_TYPE).toUpperCase();
        String paperTitle = request.getParameter(TextConstant.PAPER_TITLE);
        String description = request.getParameter(TextConstant.PAPER_DESCRIPTION);
        description = description.replaceAll(RegexComponent.TABULATION_CONTROL, TextConstant.SPACE);
        String period = request.getParameter(TextConstant.PERIOD);
        String paperIDString = request.getParameter(TextConstant.PAPER_ID);
        String price = request.getParameter(TextConstant.PRICE);
        String restriction = request.getParameter(TextConstant.RESTRICTION);

        String titleMessage = defaultServiceImpl.checkAddTitleMessage(paperTitle, description, price, restriction);
        request.getSession().setAttribute(TextConstant.TITLE_UPDATE_PAPER, localeServiceImpl.defineMessageManager(userLocale).getMessage(titleMessage));
        String paperTypeResultChecking = defaultServiceImpl.checkPaperType(paperTypeString);
        if (TextConstant.SUCCESS_OPERATION.equals(titleMessage)) {
            if (TextConstant.SUCCESS_OPERATION.equals(paperTypeResultChecking)) {
                PaperType paperType = PaperType.valueOf(paperTypeString);
                int periodInt = period != null ? Integer.parseInt(period) : 1;
                BigDecimal priceReal = new BigDecimal(price);
                int restrictionInt = Integer.parseInt(restriction);
                int paperID = Integer.parseInt(paperIDString);
                try {
                    paperEdition = paperServiceImpl.findPaperById(paperID);
                    paperServiceImpl.setPaperFeatures(paperEdition, paperType, description, paperTitle, priceReal, restrictionInt, periodInt);
                    if (paperServiceImpl.updatePaperEdition(paperEdition)) {
                        request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperServiceImpl.findAll());
                        LOGGER.log(Level.INFO, "Successful updating paper edition.");
                    } else {
                        request.getSession().setAttribute(TextConstant.ERROR_MESSAGE_PARAM, messageManager.getMessage("message.loginerror"));
                        LOGGER.log(Level.INFO, "Updating paper edition has been failed.");
                    }
                } catch (ServiceTechnicalException e) {
                    throw new CommandTechnicalException(e.getMessage(), e.getCause());
                }
            } else{
                request.getSession().setAttribute(TextConstant.TITLE_UPDATE_PAPER,messageManager.getMessage(paperTypeResultChecking));
                LOGGER.log(Level.INFO, "Updating paper edition has been failed.");
            }
        }
        router.setPagePath(page);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
