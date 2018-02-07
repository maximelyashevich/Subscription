package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.DAOTechnicalException;

import java.util.List;

public interface SubscriptionDAO {
    List<Subscription> findAllWithFinishedAction() throws DAOTechnicalException;

    List<Subscription> findAllByID(long id) throws DAOTechnicalException;

    List<Subscription> findAllByUserID(long id) throws DAOTechnicalException;

    boolean deleteBySubscriptionAndPaperId(long subscriptionId, long paperId) throws DAOTechnicalException;

    void createWithPaper(Subscription subscription, long paperId) throws DAOTechnicalException;
}
