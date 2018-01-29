package com.elyashevich.subscription.command;

import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.SubscriptionService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class UserCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
            Router router = new Router();
            String page = ConfigurationManager.getProperty("path.page.user");
            SubscriptionService subscriptionService = new SubscriptionService();
            router.setPagePath(page);
            return router;
       }
}
