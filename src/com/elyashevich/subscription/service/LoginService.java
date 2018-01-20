package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;
import com.elyashevich.subscription.validator.UserValidator;

public class LoginService {
    public String findUserWithEncryption(String login, String password) throws ServiceTechnicalException {
        UserDAO userDAO = new UserDAO();
        password = Encryption.encryptPassword(password);
        try {
            return userDAO.findUser(login, password);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
    public boolean checkUserData(String login, String password){
        UserValidator validator = new UserValidator();
        return validator.isLoginAndPasswordCorrect(login, password);
    }
}
