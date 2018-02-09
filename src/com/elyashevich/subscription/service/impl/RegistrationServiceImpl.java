package com.elyashevich.subscription.service.impl;

import com.elyashevich.subscription.dao.impl.UserDAOImpl;
import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.service.RegistrationService;
import com.elyashevich.subscription.util.Encryption;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public boolean createUserWithEncryption(User user, Address address) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        user.setPassword(Encryption.encryptPassword(user.getPassword()));
        try {
            return userDAO.create(user, address);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean findUserByLogin(String login) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.findUserByLogin(login) != null;
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean findUserByEmail(String email) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.findUserByEmail(email) != null;
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
