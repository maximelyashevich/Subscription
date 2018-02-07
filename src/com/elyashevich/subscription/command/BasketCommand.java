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

public class BasketCommand implements ActionCommand {
    private PaperService paperReceiver;
    private static final int MONTH_QUANTITY = 3;
    private static final Logger LOGGER = LogManager.getLogger();

    BasketCommand(PaperService paperReceiver) {
        this.paperReceiver = paperReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        PaperEdition paperEdition;

        LOGGER.log(Level.INFO, "Basket command is activated...");

        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(BASKET_SET);
        String durationString = request.getParameter(TextConstant.DURATION_PERIOD);
        long paperId = Long.parseLong(request.getParameter(ID));
        int durationPeriod = durationString != null ? Integer.parseInt(durationString) : MONTH_QUANTITY;

        try {
            paperEdition = paperReceiver.findPaperById(paperId);
            paperEdition.setDurationMonth(durationPeriod);
            paperReceiver.defineProductPrice(paperEdition, durationPeriod);
            hashSet = paperReceiver.addPaperToSet(hashSet, paperEdition);
            request.getSession().setAttribute(BASKET_SET, hashSet);
            request.getSession().setAttribute(TextConstant.FINAL_PRICE, paperReceiver.defineFinalPrice(hashSet));
            request.getSession().setAttribute(TextConstant.QUANTITY_SET, hashSet != null ? hashSet.size() : 0);
            LOGGER.log(Level.INFO, "Successful adding paper edition to the basket.");
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
