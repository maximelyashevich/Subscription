package com.elyashevich.subscription.command;

import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.logic.UserService;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationCommand implements ActionCommand {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private UserService userService;

    public RegistrationCommand(UserService userService){
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String day = request.getParameter(DAY);
        String month = request.getParameter(MONTH);
        String year = request.getParameter(YEAR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        /**
         *
         * !!!!!!!
         *
         */
        LocalDate birthday = LocalDate.parse(year+"-"+month+"-"+day, formatter);
        String userName = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserValidator validator = new UserValidator();
        if (validator.isLoginAndPasswordCorrect(userName, password) &&
                validator.isUserDataCorrect(firstName, lastName, email)){
            try {
                if (userService.addUser(birthday, firstName, lastName, email, userName, password)){
                    MailCommand.sendFromEmail(request, email, MessageManager.EN.getMessage("message.welcome"),
                            "Здравствуйте, "+firstName+"! Мы очень рады, что Вы решили попробовать Subscription!");
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
        return page;
    }

}
