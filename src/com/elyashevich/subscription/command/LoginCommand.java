package com.elyashevich.subscription.command;


import com.elyashevich.subscription.dao.TransactionHelper;
import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.resource.ConfigurationManager;
import com.elyashevich.subscription.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        System.out.println(login+" "+pass);

        UserDAO userDAO = new UserDAO();
        TransactionHelper transactionHelper = new TransactionHelper();
        transactionHelper.beginTransaction(userDAO);

        if(userDAO.findUserByPassword(login, pass)){
           // request.setAttribute("user", loginBean.getRole());
            page = ConfigurationManager.getProperty("path.page.main");
        }
        else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager. getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.error");
        }
        transactionHelper.commit();
        transactionHelper.endTransaction();
        return page;
    }
}
