package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.SubscriptionDAO;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class SubscriptionService {
    public List<Subscription> findAll() throws ServiceTechnicalException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        try {
            return subscriptionDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public List<Subscription> findAllByUserId(long id) throws ServiceTechnicalException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        try {
            return subscriptionDAO.findAllByUserID(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
