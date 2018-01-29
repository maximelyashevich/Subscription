package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;

public class ProfileService {
    public boolean updateUser(User user) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        user.setPassword(Encryption.encryptPassword(user.getPassword()));
        try {
            return userDAO.update(user);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
