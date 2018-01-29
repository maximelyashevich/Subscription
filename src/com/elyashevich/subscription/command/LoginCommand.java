package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.service.*;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

import static com.elyashevich.subscription.service.LocaleService.defineMessageManager;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private LoginService userReceiver;

    public LoginCommand(LoginService userReceiver){
        this.userReceiver = userReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        UserValidator validator = new UserValidator();
        System.out.println(login);
        if (validator.isLoginAndPasswordCorrect(login, password)){
            try {
                User user = userReceiver.findUserWithEncryption(login, password);
                if (user!=null){
                    request.getSession().setAttribute("user", user);
                    PaperService service = new PaperService();
                    GenreService genreService = new GenreService();
                    UserService userService = new UserService();
                    SubscriptionService subscriptionService = new SubscriptionService();
                    request.getSession().setAttribute("users", userService.findAll());
                    request.getSession().setAttribute("subscriptions", subscriptionService.findAll());
                    request.getSession().setAttribute("papers", service.findAll());
                    request.getSession().setAttribute("genres", genreService.findAll());
                    request.getSession().setAttribute("subscriptionsForUser", subscriptionService.findAllByUserId(user.getId()));
                    switch (user.getType()){
                        case ADMIN:

                            page = ConfigurationManager.getProperty("path.page.admin");
                            break;
                        case USER:
                            page = ConfigurationManager.getProperty("path.page.main");
                            break;
                        default:
                            page = ConfigurationManager.getProperty("path.page.login");
                    }

                } else{
                    request.setAttribute("titleMessage", defineMessageManager(request).getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }

            }catch (ServiceTechnicalException e){
                request.setAttribute("errorLoginPassMessage", defineMessageManager(request).getMessage("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.error");
            }
        }else{
            //throw new RuntimeException();
            request.setAttribute("titleMessage", defineMessageManager(request).getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        router.setPagePath(page);
        return router;
    }

}
