package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class BasketDeleteCommand implements ActionCommand {
    private static final String BASKET_SET = "basketSet";
    private static final String QUANTITY_SET = "quantity";
    private static final String FINAL_PRICE = "finalPrice";
    private static final String ID = "paperEditionId";
    private PaperService paperReceiver;

    public BasketDeleteCommand(PaperService paperReceiver){
        this.paperReceiver = paperReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(BASKET_SET);
        long paperId = Long.parseLong(request.getParameter(ID));
        PaperEdition paperEdition;
        try {
            paperEdition = paperReceiver.findPaperById(paperId);
            hashSet = paperReceiver.removePaperFromSet(hashSet, paperEdition);
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
        return router;
    }
}
