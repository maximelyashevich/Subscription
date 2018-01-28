package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;

public interface UserDAO {
    User findUser(String login, String password) throws DAOTechnicalException;
}
