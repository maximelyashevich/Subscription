package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public User getUser(String date, String firstName, String lastName, String email, String login, String password){
        User user = new User();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate birthday = LocalDate.parse(date, formatter);

        user.setBirthday(birthday);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserName(login);
        user.setPassword(password);
        return user;
    }
    public boolean updateUser(User user) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        user.setPassword(Encryption.encryptPassword(user.getPassword()));
        try {
            return userDAO.update(user);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public User findUserByID(long id) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.findUserById(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public User updateUserAmount(long id) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.updateAmount(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
