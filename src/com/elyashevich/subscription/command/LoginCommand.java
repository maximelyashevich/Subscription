package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.*;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.util.TextConstant;
import com.elyashevich.subscription.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private UserService userReceiver;
    private static final Logger LOGGER = LogManager.getLogger();

    public LoginCommand(UserService userReceiver) {
        this.userReceiver = userReceiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandTechnicalException {
        Router router = new Router();
        String page = ConfigurationManager.getProperty("path.page.login");

        GenreService genreService = new GenreService();
        LocaleService localeService = new LocaleService();
        PaperService service = new PaperService();
        UserValidator validator = new UserValidator();

        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        MessageManager messageManager = localeService.defineMessageManager(userLocale);

        LOGGER.log(Level.INFO, "Login command is activated...");

        String login = request.getParameter(TextConstant.PARAM_NAME_LOGIN);
        String password = request.getParameter(TextConstant.PARAM_NAME_PASSWORD);

        if (validator.isLoginAndPasswordCorrect(login, password)) {
            try {
                User user = userReceiver.findUserWithEncryption(login, password);
                if (user != null) {
                    request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                    request.setAttribute(TextConstant.IMAGE_PATH, user.getImagePath());

                    SubscriptionService subscriptionService = new SubscriptionService();
                    request.getSession().setAttribute(TextConstant.USERS_PARAM, userReceiver.findAll());
                    request.getSession().setAttribute(TextConstant.SUBSCRIPTIONS_PARAM, subscriptionService.findAll());
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, service.findAll());
                    request.getSession().setAttribute(TextConstant.PAPERS_RESTRICTION_PARAM, service.findAllWithRestriction(user));
                    request.getSession().setAttribute(TextConstant.GENRES_PARAM, genreService.findAll());
                    request.getSession().setAttribute(TextConstant.SUBSCRIPTIONS_FOR_USER_PARAM, subscriptionService.findAllByUserId(user.getId()));
                    switch (user.getType()) {
                        case ADMIN:
                            page = ConfigurationManager.getProperty("path.page.admin");
                            break;
                        case USER:
                            if (user.isAvailability()) {
                                page = ConfigurationManager.getProperty("path.page.main");
                            } else {
                                request.setAttribute(TextConstant.TITLE_PARAM, messageManager.getMessage("message.blockText"));
                            }
                            break;
                    }
                    LOGGER.log(Level.INFO, "Successful performing find operation.");
                } else {
                    request.setAttribute(TextConstant.TITLE_PARAM, messageManager.getMessage("message.loginerror"));
                    LOGGER.log(Level.INFO, "Login operation has been failed.");
                }
            } catch (ServiceTechnicalException e) {
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        } else {
            request.setAttribute(TextConstant.TITLE_PARAM, localeService.defineMessageManager(userLocale).getMessage("message.loginerror"));
        }
        router.setPagePath(page);
        return router;
    }

}
