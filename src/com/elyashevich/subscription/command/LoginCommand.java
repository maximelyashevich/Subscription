package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.CommandTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
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
        LocaleService localeService = new LocaleService();
        PaperService service = new PaperService();
        GenreService genreService = new GenreService();
        CreditService creditService = new CreditService();
        Object userLocale = request.getSession().getAttribute(TextConstant.USER_LOCALE);
        String page;
        String login = request.getParameter(TextConstant.PARAM_NAME_LOGIN);
        String password = request.getParameter(TextConstant.PARAM_NAME_PASSWORD);
        UserValidator validator = new UserValidator();
        if (validator.isLoginAndPasswordCorrect(login, password)) {
            try {
                User user = userReceiver.findUserWithEncryption(login, password);
                if (user != null) {
                    LOGGER.log(Level.INFO, "successful");
                    request.getSession().setAttribute(TextConstant.USER_PARAM, user);
                    request.setAttribute(TextConstant.IMAGE_PATH, user.getImagePath());

                    SubscriptionService subscriptionService = new SubscriptionService();
                    request.getSession().setAttribute(TextConstant.USERS_PARAM, userReceiver.findAll());
                    request.getSession().setAttribute(TextConstant.SUBSCRIPTIONS_PARAM, subscriptionService.findAll());
                    request.getSession().setAttribute(TextConstant.PAPERS_PARAM, service.findAll());
                    request.getSession().setAttribute(TextConstant.GENRES_PARAM, genreService.findAll());
                    request.getSession().setAttribute(TextConstant.CREDITS_PARAM, creditService.findAll());
                    request.getSession().setAttribute(TextConstant.CREDITS_FOR_USER_PARAM, creditService.findAllByUserId(user.getId()));
                    request.getSession().setAttribute(TextConstant.SUBSCRIPTIONS_FOR_USER_PARAM, subscriptionService.findAllByUserId(user.getId()));
                    switch (user.getType()) {
                        case ADMIN:
                            page = ConfigurationManager.getProperty("path.page.admin");
                            break;
                        case USER:
                            if (user.isAvailability()) {
                                page = ConfigurationManager.getProperty("path.page.main");
                            } else{
                                page = ConfigurationManager.getProperty("path.page.login");
                                request.setAttribute(TextConstant.TITLE_PARAM, "Вы заблокированы! Свяжитесь с администратором для получения более подробной информации");
                            }
                            break;
                        default:
                            page = ConfigurationManager.getProperty("path.page.login");
                    }
                } else {
                    request.setAttribute(TextConstant.TITLE_PARAM, localeService.defineMessageManager(userLocale).getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }

            } catch (ServiceTechnicalException e) {
                throw new CommandTechnicalException(e.getMessage(), e.getCause());
            }
        } else {
            request.setAttribute(TextConstant.TITLE_PARAM, localeService.defineMessageManager(userLocale).getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        router.setPagePath(page);
        return router;
    }

}
