package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddPaperCommand implements ActionCommand {
    private PaperService paperService;

    public AddPaperCommand(PaperService paperService){
        this.paperService = paperService;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);

        String myRadio= request.getParameter(TextConstant.RADIO_BUTTON);
        PaperType paperType;
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
        int periodInt = period!=null?Integer.parseInt(period):1;
        String price = request.getParameter(TextConstant.PRICE);
        BigDecimal priceReal = price!=null?new BigDecimal(price):new BigDecimal(2);
        String restriction = request.getParameter(TextConstant.RESTRICTION);
        int restrictionInt = restriction!=null?Integer.parseInt(restriction):0;
        String[]checkValues = request.getParameterValues(TextConstant.CHECH_BUTTON);
        if (checkValues==null){
            checkValues[0]="Научно-популярные издания";
        }
        LocaleService localeService = new LocaleService();
        //  && validator.isCountryExists(country)
            try {
                PaperEdition paperEdition = paperService.getPaper(paperType, paperTitle, description, periodInt, priceReal, restrictionInt);
                if (paperService.createPaperEdition(paperEdition, checkValues)){
//                    MailCommand.sendFromEmail(request, email, MessageManager.EN.getMessage("message.welcome"),
//                            "Здравствуйте, "+firstName+"! Мы очень рады, что Вы решили попробовать Subscription!");
//                    request.setAttribute("titleMessage", localeService.defineMessageManager(userLocale).getMessage("message.registrationsuccess"));
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, paperService.findAll());
                    page = ConfigurationManager.getProperty("path.page.admin");
                } else{
                    request.getSession().setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
                }
            } catch (ServiceTechnicalException e){
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
            router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
