package com.elyashevich.subscription.scheduler;

import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.service.impl.SubscriptionServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

public class SubscriptionScheduler implements Job {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SUBSCRIPTION = "Subscription ";
    private static final String DELETED_MESSAGE = " has been deleted!";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("Subscription observer start work!");

        try {
            SubscriptionServiceImpl subscriptionServiceImpl = new SubscriptionServiceImpl();
            List<Subscription> subscriptions = subscriptionServiceImpl.findAllWithFinishedAction();
            for (Subscription subscription : subscriptions) {
                subscriptionServiceImpl.deleteBySubscriptionAndPaperId(subscription.getId(), subscription.getPaperEdition().getId());
                LOGGER.info(SUBSCRIPTION + subscription.getId() + DELETED_MESSAGE);
            }
        } catch (ServiceTechnicalException e) {
            LOGGER.catching(Level.ERROR, e);
        }
    }
}
