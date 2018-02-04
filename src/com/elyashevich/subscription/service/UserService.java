package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.dao.impl.UserDAOImpl;
import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.util.Encryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REG_EX_JSP = "/jsp.+";

    public User findUserWithEncryption(String login, String password) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        password = Encryption.encryptPassword(password);
        try {
            return userDAO.findUser(login, password);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public User findUserById(long id) throws ServiceTechnicalException {
        UserDAO userDAO = new UserDAOImpl();
        try {
            return userDAO.findUserById(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

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

    public Address getAddress(String country, String city, String postIndex, String detailAddress){
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setPostIndex(postIndex);
        address.setDetailAddress(detailAddress);
        return address;
    }
    public boolean updateUser(User user, Address address) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        user.setImagePath(user.getImagePath());
        user.setPassword(Encryption.encryptPassword(user.getPassword()));
        try {
            return userDAO.update(user, address);
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

    public User updateUserAmount(User user, BigDecimal amount) throws ServiceTechnicalException {
        UserDAOImpl userDAO = new UserDAOImpl();
        try {
            return userDAO.updateAmount(user.getId(), amount);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
