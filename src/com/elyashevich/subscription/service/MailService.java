package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.manager.MessageManager;

import javax.servlet.ServletContext;
import java.util.ArrayList;

public interface MailService {
    void sendFromEmail(ServletContext context, String sendToEmail, String mailSubject, String mailText) throws ServiceTechnicalException;

    void sendAllUsersMessage(ServletContext context, ArrayList<User> users, MessageManager messageManager) throws ServiceTechnicalException;
}
