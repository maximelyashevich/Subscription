package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;

public class LoginService {
    public User findUserWithEncryption(String login, String password) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        password = Encryption.encryptPassword(password);
        try {
            return userDAO.findUser(login, password);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
