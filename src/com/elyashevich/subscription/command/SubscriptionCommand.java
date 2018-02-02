package com.elyashevich.subscription.command;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.SubscriptionService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;

public class SubscriptionCommand implements ActionCommand {
    private static final String BASKET_SET = "basketSet";
    private static final String FINAL_PRICE = "finalPrice";
    private static final String USER_ID = "userId";
    private SubscriptionService subscriptionReceiver;

    public SubscriptionCommand(SubscriptionService subscriptionReceiver){
        this.subscriptionReceiver = subscriptionReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;

        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(BASKET_SET);
        BigDecimal price = (BigDecimal) request.getSession().getAttribute(FINAL_PRICE);
        long userId = Long.parseLong(request.getParameter(USER_ID));
        try {
            Subscription subscription;
            UserDAOImpl userDAO=new UserDAOImpl();
            User user = userDAO.findUserById(userId);
            if (subscriptionReceiver.checkUserPaymentAbility(user, price)){
                subscription = subscriptionReceiver.defineSubscription(user, price);
                subscriptionReceiver.createSubscription(subscription);
                subscriptionReceiver.createSubscriptionOnPapers(hashSet, subscription);
                user.setAmount(user.getAmount().subtract(price));
                userDAO.update(user);
                System.out.println("Congratulations!!!");
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("subscriptionsForUser", subscriptionReceiver.findAllByUserId(user.getId()));
                page = ConfigurationManager.getProperty("path.page.main");
                router.setRoute(Router.RouteType.REDIRECT);
                router.setPagePath(page);
            } else{
                request.setAttribute("titleMessage", "Sorry, you don't have enough money. You are able to give credit");
                page = ConfigurationManager.getProperty("path.page.main");
            }
        } catch (DAOTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        } catch (ServiceTechnicalException e) {
            e.printStackTrace();
        }
        return router;
    }
}
