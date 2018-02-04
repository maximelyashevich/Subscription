package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.impl.SubscriptionDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class SubscriptionService {
    public List<Subscription> findAll() throws ServiceTechnicalException {
        SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
        try {
            return subscriptionDAOImpl.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public List<Subscription> findAllById(long id) throws ServiceTechnicalException {
        SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
        try {
            return subscriptionDAOImpl.findAllByID(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
    public Subscription defineSubscription(User user, BigDecimal price) throws DAOTechnicalException {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionRegistration(LocalDate.now());
        subscription.setPrice(price);
        return subscription;
    }
    public boolean checkUserPaymentAbility(User user, BigDecimal price) throws DAOTechnicalException {
        return user.getAmount().compareTo(price)>0;
    }

    public void createSubscription(Subscription subscription) throws ServiceTechnicalException {
        SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
        try {
            subscriptionDAOImpl.create(subscription);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public boolean checkIfSubscriptionsExist(List<Subscription> subscriptions, HashSet<PaperEdition> hashSet) throws ServiceTechnicalException {
        boolean result = false;
        if (subscriptions==null || subscriptions.isEmpty()) return false;
        for (Subscription subscription: subscriptions){
            for (Subscription subscription1: findAllById(subscription.getId())){
                for (PaperEdition paperEdition: hashSet){
                    if (subscription1.getPaperEdition().equals(paperEdition)){
                        result = true;
                    }
                }
            }
        }
        return result;
    }
    public void createSubscriptionOnPapers(HashSet<PaperEdition> hashSet, Subscription subscription) throws ServiceTechnicalException {
        SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
        try {
            for (PaperEdition paperEdition : hashSet) {
                subscription.setSubscriptionFinish(defineFinishDateForPaper(paperEdition.getDurationMonth()));
                subscriptionDAOImpl.createWithPaper(subscription, paperEdition.getId());
            }
        } catch (DAOTechnicalException e){
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public LocalDate defineFinishDateForPaper(int durationMonth){
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusMonths(durationMonth);
        return localDate;
    }
    public List<Subscription> findAllByUserId(long id) throws ServiceTechnicalException {
        SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
        try {
            return subscriptionDAOImpl.findAllByUserID(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
