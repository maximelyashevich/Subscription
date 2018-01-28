package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAOImpl;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationService {
    public boolean createUserWithEncryption(User user) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        user.setPassword(Encryption.encryptPassword(user.getPassword()));
        try {
            return userDAO.create(user);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public User getUser(String date, String firstName, String lastName, String email, String login, String password){
        UserDAOImpl userDAO = new UserDAOImpl();
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
}
