package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.mail.MailThread;
import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.util.TextConstant;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MailService {
    public void sendFromEmail(ServletContext context, String sendToEmail, String mailSubject, String mailText) throws ServiceTechnicalException {
        try {
            Properties properties = new Properties();
            String filename = context.getInitParameter(TextConstant.MAIL);

            properties.load(context.getResourceAsStream(filename));

            MailThread mailOperator=new MailThread(sendToEmail, mailSubject, mailText, properties);
            mailOperator.start();
        } catch (IOException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public void sendAllUsersMessage(ServletContext context, ArrayList<User> users, MessageManager messageManager) throws ServiceTechnicalException {
        for (User user: users){
            sendFromEmail(context, user.getEmail(), messageManager.getMessage("message.hello"), messageManager.getMessage("message.mailNew"));
        }
    }
}
