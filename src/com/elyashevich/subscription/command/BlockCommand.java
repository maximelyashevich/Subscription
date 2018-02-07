package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BlockCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userService;

    BlockCommand(UserService userService){
        this.userService = userService;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.admin");
        User user;

        LOGGER.log(Level.INFO, "Starting block user...");

        long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));
        try {
            user = userService.findUserById(userId);
            user.setAvailability(!user.isAvailability());
            if (userService.updateUser(user, user.getAddress())){
                request.getSession().setAttribute(TextConstant.USERS_PARAM, userService.findAll());
                LOGGER.log(Level.INFO, "Successful block user.");
            } else{
                LOGGER.log(Level.INFO, "User blocking has been failed.");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
