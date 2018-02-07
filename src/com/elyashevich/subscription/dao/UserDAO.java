package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;

import java.math.BigDecimal;

public interface UserDAO {
    User findUser(String login, String password) throws DAOTechnicalException;

    User findUserById(long id) throws DAOTechnicalException;

    User updateAmount(long id, BigDecimal price) throws DAOTechnicalException;

    boolean create(User user, Address address) throws DAOTechnicalException;

    boolean update(User user, Address address) throws DAOTechnicalException;

    User findUserByEmail(String email) throws DAOTechnicalException;

    User findUserByLogin(String login) throws DAOTechnicalException;
}
