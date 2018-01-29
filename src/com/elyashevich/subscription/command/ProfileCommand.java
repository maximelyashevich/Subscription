package com.elyashevich.subscription.command;

import com.elyashevich.subscription.command.client.ClientType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LocaleService;
import com.elyashevich.subscription.service.ProfileService;
import com.elyashevich.subscription.service.UserService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class ProfileCommand implements ActionCommand {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String TYPE = "type";

    private ProfileService userReceiver;

    public ProfileCommand(ProfileService userReceiver){
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
        ClientType clientType = ClientType.valueOf(request.getParameter(TYPE).toUpperCase());
        BigDecimal amount = new BigDecimal(request.getParameter(AMOUNT));
        long id = Long.parseLong(request.getParameter(ID));

        UserValidator validator = new UserValidator();
        UserService userService = new UserService();
        if (validator.isLoginAndPasswordCorrect(userName, password) &&
                validator.isUserDataCorrect(firstName, lastName, email)){
            try {
                User user = userService.getUser(dob, firstName, lastName, email, userName, password);
                user.setId(id);
                user.setType(clientType);
                user.setAmount(amount);
                if (userReceiver.updateUser(user)){
                    MailCommand.sendFromEmail(request, email, MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Вы только что обновили свои данные в Subscription!");
                    request.setAttribute("titleMessage", LocaleService.defineMessageManager(request).getMessage("message.registrationsuccess"));
                    request.setAttribute("user", user);
                    page = ConfigurationManager.getProperty("path.page.user");
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
