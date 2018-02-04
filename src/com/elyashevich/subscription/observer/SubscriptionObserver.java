package com.elyashevich.subscription.observer;

import com.elyashevich.subscription.dao.impl.SubscriptionDAOImpl;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class SubscriptionObserver implements Job {

    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Subscription observer start work!");

        try {
            SubscriptionDAOImpl subscriptionDAOImpl = new SubscriptionDAOImpl();
            List<Subscription> subscriptions = subscriptionDAOImpl.findAllWithFinishedAction();
            for (Subscription subscription : subscriptions) {
                subscriptionDAOImpl.deleteBySubscriptionAndPaperId(subscription.getId(), subscription.getPaperEdition().getId());
            }
            System.out.println("*");
        } catch (DAOTechnicalException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
}
