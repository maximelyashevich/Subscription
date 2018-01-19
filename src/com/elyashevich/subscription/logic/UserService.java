package com.elyashevich.subscription.logic;

import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;

import java.time.LocalDate;

public class UserService {
    public String checkUser(String login, String password) throws ServiceTechnicalException {
        UserDAO userDAO = new UserDAO();
       // TransactionHelper transactionHelper = new TransactionHelper();
       // transactionHelper.beginTransaction(userDAO);
        ///!!!!!!!!!!!!!!!!!!!!!!!!
       // transactionHelper.commit();
       // transactionHelper.endTransaction();
        password = Encryption.encryptPassword(password);
        try {
            return userDAO.findUserByPassword(login, password);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
    public boolean addUser(LocalDate date, String firstName, String lastName, String email, String login, String password) throws ServiceTechnicalException {
        UserDAO userDAO = new UserDAO();
        User user = new User();

        user.setBirthday(date);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserName(login);
        user.setPassword(Encryption.encryptPassword(password));
        try {
            return userDAO.create(user);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
