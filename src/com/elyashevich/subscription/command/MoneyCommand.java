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
import java.math.BigDecimal;

public class MoneyCommand implements ActionCommand {
    private UserService userReceiver;
    private static final int MIN_AMOUNT = 10;

    private static final Logger LOGGER = LogManager.getLogger();

    MoneyCommand(UserService userReceiver) {
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        User user;

        LOGGER.log(Level.INFO, "Money command is activated...");

        String page = ConfigurationManager.getProperty("path.page.user");
        long userId = Long.parseLong(request.getParameter(TextConstant.USER_ID));

        try {
            user = userReceiver.findUserById(userId);
            user = userReceiver.updateUserAmount(user, user.getAmount().add(new BigDecimal(MIN_AMOUNT)));
            if (user != null) {
                request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                LOGGER.log(Level.INFO, "Successful performing money operation.");
            } else {
                LOGGER.log(Level.INFO, "Money operation has been failed.");
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandTechnicalException(e.getMessage(), e.getCause());
        }
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(page);
        return router;
    }
}
