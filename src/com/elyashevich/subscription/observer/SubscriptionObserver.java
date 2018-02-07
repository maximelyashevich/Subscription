package com.elyashevich.subscription.observer;

import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

public class SubscriptionObserver implements Job {

    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("Subscription observer start work!");

        try {
            SubscriptionService subscriptionService = new SubscriptionService();
            List<Subscription> subscriptions = subscriptionService.findAllWithFinishedAction();
            for (Subscription subscription : subscriptions) {
                subscriptionService.deleteBySubscriptionAndPaperId(subscription.getId(), subscription.getPaperEdition().getId());
                LOGGER.info("Subscription "+subscription.getId()+" has been deleted!");
            }
        }catch (ServiceTechnicalException e) {
            LOGGER.catching(e);
        }
    }
}
