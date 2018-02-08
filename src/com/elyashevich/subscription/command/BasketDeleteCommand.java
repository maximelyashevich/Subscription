package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

import static com.elyashevich.subscription.util.TextConstant.BASKET_SET;
import static com.elyashevich.subscription.util.TextConstant.ID;

public class BasketDeleteCommand implements ActionCommand {
    private PaperService paperReceiver;
    private static final Logger LOGGER = LogManager.getLogger();

    BasketDeleteCommand(PaperService paperReceiver) {
        this.paperReceiver = paperReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        PaperEdition paperEdition;

        LOGGER.log(Level.INFO, "Basket delete command is activated...");

        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(BASKET_SET);
        long paperId = Long.parseLong(request.getParameter(ID));
        System.out.println(paperId);
        try {
            paperEdition = paperReceiver.findPaperById(paperId);
            hashSet = paperReceiver.removePaperFromSet(hashSet, paperEdition);
            request.getSession().setAttribute(TextConstant.BASKET_SET, hashSet);
            request.getSession().setAttribute(TextConstant.FINAL_PRICE, paperReceiver.defineFinalPrice(hashSet));
            request.getSession().setAttribute(TextConstant.QUANTITY_SET, hashSet != null ? hashSet.size() : 0);
            LOGGER.log(Level.INFO, "Successful deleting paper edition from the basket.");
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setPagePath(page);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
