package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.SubscriptionDAO;
import com.elyashevich.subscription.dao.UserDAOImpl;
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
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        try {
            return subscriptionDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public Subscription defineSubscription(User user, BigDecimal price) throws DAOTechnicalException {
        Subscription subscription = new Subscription();
        UserDAOImpl userDAO = new UserDAOImpl();
        subscription.setUser(user);
        subscription.setSubscriptionRegistration(LocalDate.now());
        subscription.setPrice(price);
        return subscription;
    }
    public boolean checkUserPaymentAbility(User user, BigDecimal price) throws DAOTechnicalException {
        return user.getAmount().compareTo(price)>0;
    }

    public void createSubscription(Subscription subscription) throws ServiceTechnicalException {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        try {
            subscriptionDAO.create(subscription);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
    public void createSubscriptionOnPapers(HashSet<PaperEdition> hashSet, Subscription subscription) throws ServiceTechnicalException {
        SubscriptionDAO subscriptionDAO= new SubscriptionDAO();
        try {
            for (PaperEdition paperEdition : hashSet) {
                subscriptionDAO.createWithPaper(subscription, paperEdition.getId(), defineFinishDateForPaper(paperEdition.getDurationMonth()));
            }
        } catch (DAOTechnicalException e){
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public LocalDate defineFinishDateForPaper(int durationMonth){
        LocalDate localDate = LocalDate.now();
        return localDate.plusMonths(durationMonth);
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
