package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

public interface SubscriptionService {
    void createSubscriptionOnPapers(HashSet<PaperEdition> hashSet, Subscription subscription) throws ServiceTechnicalException;

    void deleteBySubscriptionAndPaperId(long subscriptionId, long paperId) throws ServiceTechnicalException;

    boolean checkUserPaymentAbility(User user, BigDecimal price);

    void createSubscription(Subscription subscription) throws ServiceTechnicalException;

    boolean checkIfSubscriptionsExist(List<Subscription> subscriptions, HashSet<PaperEdition> hashSet) throws ServiceTechnicalException;

    Subscription defineSubscription(User user, BigDecimal price);

    List<Subscription> findAll() throws ServiceTechnicalException;

    List<Subscription> findAllWithFinishedAction() throws ServiceTechnicalException;

    List<Subscription> findAllByUserId(long id) throws ServiceTechnicalException;

}
