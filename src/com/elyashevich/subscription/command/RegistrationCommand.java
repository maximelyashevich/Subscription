package com.elyashevich.subscription.command;

import com.elyashevich.subscription.dao.TransactionHelper;
import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.resource.ConfigurationManager;
import com.elyashevich.subscription.resource.MessageManager;

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
    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String day = request.getParameter(DAY);
        String month = request.getParameter(MONTH);
        String year = request.getParameter(YEAR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ////////
        ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ////////
        LocalDate birthday = LocalDate.parse(year+"-"+month+"-"+day, formatter);
        String userName = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        user.setBirthday(birthday);
        user.setUserName(userName);
        user.setPassword(password);

        UserDAO userDAO = new UserDAO();
        TransactionHelper transactionHelper = new TransactionHelper();
        transactionHelper.beginTransaction(userDAO);

        if(userDAO.create(user)){
            request.setAttribute("user", user.getUserName());
            page = ConfigurationManager. getProperty("path.page.main");
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
