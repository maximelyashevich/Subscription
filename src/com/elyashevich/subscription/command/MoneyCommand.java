package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class MoneyCommand implements ActionCommand {
    private static final String ID = "currentUserId";
    private UserService userReceiver;

    public MoneyCommand(UserService userReceiver){
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        long userId = Long.parseLong(request.getParameter(ID));
        User user;
        try {
            user = userReceiver.updateUserAmount(userId);
                if (user!=null){
                    request.getSession().setAttribute("user", user);
                    router.setRoute(Router.RouteType.FORWARD);
                    page = ConfigurationManager.getProperty("path.page.user");
                    router.setPagePath(page);
                }
            else{
                ///////////////////
                ///!!!!!!!!!!!!!!!!
                ///////////////////
                System.out.println("ERRRRROR!!!!!");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }

        return router;
    }
}
