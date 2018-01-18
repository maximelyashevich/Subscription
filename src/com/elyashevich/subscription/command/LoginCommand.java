package com.elyashevich.subscription.command;


import com.elyashevich.subscription.logic.UserService;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

import static com.elyashevich.subscription.util.RegexComponent.LOGIN_REGEX;
import static com.elyashevich.subscription.util.RegexComponent.PASSWORD_REGEX;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private UserService userService;

    public LoginCommand(UserService userService){
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        UserValidator validator = new UserValidator();
        if (validator.isDataCorrect(login, LOGIN_REGEX) && validator.isDataCorrect(password, PASSWORD_REGEX)){
                String name = userService.checkUser(login, password);
                if (name!=null){
                    request.setAttribute("user", name);
                    page = ConfigurationManager.getProperty("path.page.main");
                } else{
                    request.setAttribute("errorLoginPassMessage",
                            MessageManager.EN.getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.error");
                }
        }else{
            //throw new RuntimeException();
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.EN.getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.error");
        }
        return page;
    }
}
