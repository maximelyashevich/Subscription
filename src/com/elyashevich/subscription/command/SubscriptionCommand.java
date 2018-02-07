package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.service.SubscriptionService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;

public class SubscriptionCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private SubscriptionService subscriptionReceiver;

    SubscriptionCommand(SubscriptionService subscriptionReceiver) {
        this.subscriptionReceiver = subscriptionReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.main");
        Subscription subscription;

        UserService userService = new UserService();
        PaperService paperService = new PaperService();
        LocaleService localeService = new LocaleService();
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeService.defineMessageManager(userLocale);

        LOGGER.log(Level.INFO, "Start subscription process...");
        HashSet<PaperEdition> hashSet = (HashSet<PaperEdition>) request.getSession().getAttribute(TextConstant.BASKET_SET);
        BigDecimal price = (BigDecimal) request.getSession().getAttribute(TextConstant.FINAL_PRICE);
        long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));

        request.getSession().setAttribute(TextConstant.FLAG_ORDER, TextConstant.NOT_READY_VALUE);
        if (price != null && price.compareTo(new BigDecimal(0)) > 0) {
            try {
                User user = userService.findUserById(userId);
                if (!subscriptionReceiver.checkIfSubscriptionsExist(subscriptionReceiver.findAllByUserId(user.getId()), hashSet)) {
                    if (subscriptionReceiver.checkUserPaymentAbility(user, price)) {
                        subscription = subscriptionReceiver.defineSubscription(user, price);
                        subscriptionReceiver.createSubscription(subscription);
                        subscriptionReceiver.createSubscriptionOnPapers(hashSet, subscription);
                        user.setAmount(user.getAmount().subtract(price));
                        userService.updateUserAmount(user, user.getAmount());
                        hashSet.clear();
                        request.getSession().setAttribute(TextConstant.BASKET_SET, hashSet);
                        request.getSession().setAttribute(TextConstant.FINAL_PRICE, paperService.defineFinalPrice(hashSet));
                        request.getSession().setAttribute(TextConstant.QUANTITY_SET, hashSet.size());

                        request.getSession().setAttribute(TextConstant.SUBSCRIPTION, subscription);
                        request.getSession().setAttribute(TextConstant.TITLE_SUBSCRIPTION, TextConstant.EMPTY_STRING);
                        request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                        request.getSession().setAttribute(TextConstant.FLAG_ORDER, TextConstant.READY_VALUE);
                        request.getSession().setAttribute(TextConstant.SUBSCRIPTIONS_FOR_USER_PARAM, subscriptionReceiver.findAllByUserId(user.getId()));
                        LOGGER.log(Level.INFO, "Successfully process performing.");
                    } else {
                        request.getSession().setAttribute(TextConstant.TITLE_SUBSCRIPTION, messageManager.getMessage("message.topBalance"));
                        LOGGER.log(Level.INFO, "Subscription has been failed. Don't enough money");
                    }
                } else {
                    request.getSession().setAttribute(TextConstant.TITLE_SUBSCRIPTION, messageManager.getMessage("message.existsSubscription"));
                    LOGGER.log(Level.INFO, "Subscription has been failed. Exists paper");
                }
            } catch (ServiceTechnicalException e) {
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        } else {
            request.getSession().setAttribute(TextConstant.TITLE_SUBSCRIPTION, messageManager.getMessage("message.forgot"));
            LOGGER.log(Level.INFO, "Subscription has been failed. Forgot select paper");
        }
        router.setPagePath(page);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
