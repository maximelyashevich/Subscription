package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    void setUserFeatures(User user, String firstName, String lastName, long userId, LocalDate birthday, String country, String city, String detailAddress, String postIndex);

    boolean updateUser(User user, Address address) throws ServiceTechnicalException;

    User findUserWithEncryption(String login, String password) throws ServiceTechnicalException;

    User findUserById(long id) throws ServiceTechnicalException;

    User defineUser(LocalDate date, String firstName, String lastName, String email, String login, String password);

    User findUserByID(long id) throws ServiceTechnicalException;

    User updateUserAmount(User user, BigDecimal amount) throws ServiceTechnicalException;

    Address getAddress(String country, String city, String postIndex, String detailAddress);

    List<User> findAll() throws ServiceTechnicalException;

}
