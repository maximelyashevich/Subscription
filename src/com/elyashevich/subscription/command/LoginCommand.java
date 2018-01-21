package com.elyashevich.subscription.command;

import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LoginService;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.servlet.Router;
import com.elyashevich.subscription.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

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
        String name = null;
        UserValidator validator = new UserValidator();
        if (validator.isLoginAndPasswordCorrect(login, password)){
            try {
                name = userReceiver.findUserWithEncryption(login, password);
                if (name!=null){
                    PaperService service = new PaperService();
                    request.setAttribute("user", name);
                    request.setAttribute("papers", service.findAll());
                    page = ConfigurationManager.getProperty("path.page.main");
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

    ////!!!!!!!!!!!!!!!!!!!!!!!!////////
    public static MessageManager defineMessageManager(HttpServletRequest request){
        MessageManager messageManager = null;
        System.out.println(request.getSession().getAttribute("userLocale"));
        Object userLocale = request.getSession().getAttribute("userLocale");
        if (userLocale==null){
            userLocale = "ru_RU";
        }
        switch (userLocale.toString()){
            case "en_US":
                messageManager = MessageManager.EN;
                break;
            case "ru_RU":
                messageManager = MessageManager.RU;
                break;
        }
        return messageManager;
    }
}
