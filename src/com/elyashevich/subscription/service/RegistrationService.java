package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

public interface RegistrationService {
    boolean createUserWithEncryption(User user, Address address) throws ServiceTechnicalException;

    boolean findUserByLogin(String login) throws ServiceTechnicalException;

    boolean findUserByEmail(String email) throws ServiceTechnicalException;
}
