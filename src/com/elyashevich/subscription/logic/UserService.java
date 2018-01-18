package com.elyashevich.subscription.logic;

import com.elyashevich.subscription.dao.UserDAO;

public class UserService {
    public String checkUser(String login, String password){
        UserDAO userDAO = new UserDAO();
       // TransactionHelper transactionHelper = new TransactionHelper();
       // transactionHelper.beginTransaction(userDAO);
        ///!!!!!!!!!!!!!!!!!!!!!!!!
       // transactionHelper.commit();
       // transactionHelper.endTransaction();
        return userDAO.findUserByPassword(login, password);
    }
}
