package com.elyashevich.subscription.command;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.RegistrationService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private RegistrationService userReceiver;

    public RegistrationCommand(RegistrationService userReceiver){
        this.userReceiver = userReceiver;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        String page = null;

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String dob = request.getParameter(DOB);
        String userName = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        UserValidator validator = new UserValidator();
        if (validator.isLoginAndPasswordCorrect(userName, password) &&
                validator.isUserDataCorrect(firstName, lastName, email)){
            try {
                User user = userReceiver.getUser(dob, firstName, lastName, email, userName, password);
                if (userReceiver.createUserWithEncryption(user)){
                    MailCommand.sendFromEmail(request, email, MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Мы очень рады, что Вы решили попробовать Subscription!");
                    request.setAttribute("titleMessage", LocaleService.defineMessageManager(request).getMessage("message.registrationsuccess"));
                    page = ConfigurationManager.getProperty("path.page.login");
                } else{
                    request.setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
                }
            } catch (ServiceTechnicalException e) {
                request.setAttribute("errorLoginPassMessage", MessageManager.EN.getMessage("message.loginerror"));
               // page = ConfigurationManager.getProperty("path.page.error");
            }
        } else{
            request.setAttribute("titleMessage", MessageManager.EN.getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        router.setPagePath(page);
        return router;
    }

}
