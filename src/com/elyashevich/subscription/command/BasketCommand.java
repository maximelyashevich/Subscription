package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class BasketCommand implements ActionCommand{
    private static final String BASKET_SET = "basketSet";
    private static final String QUANTITY_SET = "quantity";
    private static final String FINAL_PRICE = "finalPrice";
    private static final String ID = "paperEditionId";
    private static final String DURATION_PERIOD = "durationQuantity";
    private PaperService paperReceiver;

    public BasketCommand(PaperService paperReceiver){
        this.paperReceiver = paperReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;

        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(BASKET_SET);
        long paperId = Long.parseLong(request.getParameter(ID));
        String durationString = request.getParameter(DURATION_PERIOD);
        int durationPeriod;
        if (durationString==null){
            durationPeriod = 3;
        } else {
            durationPeriod = Integer.parseInt(durationString);
        }
        PaperEdition paperEdition;
        try {
            paperEdition = paperReceiver.findPaperById(paperId);
            paperEdition.setDurationMonth(durationPeriod);
            paperReceiver.defineProductPrice(paperEdition, durationPeriod);
            hashSet = paperReceiver.addPaperToSet(hashSet, paperEdition);
            request.getSession().setAttribute(BASKET_SET,  hashSet);
            request.getSession().setAttribute(FINAL_PRICE, paperReceiver.defineFinalPrice(hashSet));
           if (hashSet!=null) {
               request.getSession().setAttribute(QUANTITY_SET, hashSet.size());
           } else{
               request.getSession().setAttribute(QUANTITY_SET, 0);
           }
            page = ConfigurationManager.getProperty("path.page.main");
            router.setPagePath(page);
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
