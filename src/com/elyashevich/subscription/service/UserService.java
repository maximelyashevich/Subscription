package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class UserService {
    public List<User> findAll() throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
