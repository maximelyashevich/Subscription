package com.elyashevich.subscription.command;

import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.ConfigurationManager;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.service.LoginService;
import com.elyashevich.subscription.service.PaperService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private LoginService userReceiver;

    public LoginCommand(LoginService userReceiver){
        this.userReceiver = userReceiver;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String name = null;
        if (userReceiver.checkUserData(login, password)){
            try {
                name = userReceiver.findUserWithEncryption(login, password);
                if (name!=null){
                    PaperService service = new PaperService();
                    request.setAttribute("user", name);
                    request.setAttribute("papers", service.findAll());
                    page = ConfigurationManager.getProperty("path.page.main");
                } else{
                    request.setAttribute("titleMessage", getLocaleBySessionLocale(request).getMessage("message.loginerror"));
                    page = ConfigurationManager.getProperty("path.page.login");
                }

            }catch (ServiceTechnicalException e){
                request.setAttribute("errorLoginPassMessage", getLocaleBySessionLocale(request).getMessage("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.error");
            }
        }else{
            //throw new RuntimeException();
            request.setAttribute("titleMessage", getLocaleBySessionLocale(request).getMessage("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }

    ////!!!!!!!!!!!!!!!!!!!!!!!!////////
    private MessageManager getLocaleBySessionLocale(HttpServletRequest request){
        MessageManager messageManager = null;
        switch (request.getLocale().getCountry()){
            case "US":
                messageManager = MessageManager.EN;
                break;
            case "RU":
                messageManager = MessageManager.RU;
                break;
        }
        return messageManager;
    }
}
