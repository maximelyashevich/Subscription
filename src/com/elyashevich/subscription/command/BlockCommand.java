package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.http.HttpServletRequest;

public class BlockCommand implements ActionCommand {
    private UserService userService;

    BlockCommand(UserService userService){
        this.userService = userService;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = null;
        long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));
        User user;
        try {
            user = userService.findUserById(userId);
            user.setAvailability(!user.isAvailability());
            if (userService.updateUser(user, user.getAddress())){
                router.setRoute(Router.RouteType.REDIRECT);
                request.getSession().setAttribute(TextConstant.USERS_PARAM, userService.findAll());
                page = ConfigurationManager.getProperty("path.page.admin");
                router.setPagePath(page);
            }
            else{

                System.out.println("ERRRRROR!!!!!");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        return router;
    }
}
