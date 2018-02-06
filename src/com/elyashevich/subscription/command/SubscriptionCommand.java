package com.elyashevich.subscription.command;

import com.elyashevich.subscription.dao.impl.UserDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.service.SubscriptionService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;

public class SubscriptionCommand implements ActionCommand {
    private static final String BASKET_SET = "basketSet";
    private static final String FINAL_PRICE = "finalPrice";
    private static final String QUANTITY_SET = "quantity";
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

        try {
            Subscription subscription;
            UserService userService = new UserService();
            UserDAOImpl userDAO=new UserDAOImpl();
            PaperService paperService = new PaperService();
            request.getSession().setAttribute("flagOrder", "hey");
            if (price==null || price.compareTo(new BigDecimal(0))<=0){
                request.setAttribute("titleMessage", "Sorry, you don't select any papers");
                page = ConfigurationManager.getProperty("path.page.main");
                router.setPagePath(page);
            }
            else {
                long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));
                User user = userDAO.findUserById(userId);
                if (!subscriptionReceiver.checkIfSubscriptionsExist(subscriptionReceiver.findAllByUserId(user.getId()), hashSet)) {
                    if (subscriptionReceiver.checkUserPaymentAbility(user, price)) {
                        subscription = subscriptionReceiver.defineSubscription(user, price);
                        subscriptionReceiver.createSubscription(subscription);
                        subscriptionReceiver.createSubscriptionOnPapers(hashSet, subscription);
                        user.setAmount(user.getAmount().subtract(price));
                        userService.updateUserAmount(user, user.getAmount());
                        System.out.println("Congratulations!!!");
                        hashSet.clear();
                        request.getSession().setAttribute(BASKET_SET, hashSet);
                        request.getSession().setAttribute(FINAL_PRICE, paperService.defineFinalPrice(hashSet));
                        if (hashSet != null) {
                            request.getSession().setAttribute(QUANTITY_SET, hashSet.size());
                        } else {
                            request.getSession().setAttribute(QUANTITY_SET, 0);
                        }
                        request.getSession().setAttribute("subscription", subscription);
                        request.setAttribute("titleMessage", "");
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("flagOrder", "READY");
                        request.getSession().setAttribute("subscriptionsForUser", subscriptionReceiver.findAllByUserId(user.getId()));
                        page = ConfigurationManager.getProperty("path.page.main");
                        router.setRoute(Router.RouteType.REDIRECT);
                        router.setPagePath(page);
                    } else {
                        request.setAttribute("titleMessage", "Sorry, you don't have enough money. You are able to give credit");
                        page = ConfigurationManager.getProperty("path.page.main");
                        router.setPagePath(page);
                    }
                } else{
                    request.setAttribute("titleMessage", "Be careful, you've had one of chosen papers. Select another.");
                    page = ConfigurationManager.getProperty("path.page.main");
                    router.setPagePath(page);
                }
            }
        } catch (DAOTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        } catch (ServiceTechnicalException e) {
            e.printStackTrace();
        }
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
