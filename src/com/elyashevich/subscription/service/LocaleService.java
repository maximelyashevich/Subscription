package com.elyashevich.subscription.service;

import com.elyashevich.subscription.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class LocaleService {
    public static MessageManager defineMessageManager(HttpServletRequest request){
        MessageManager messageManager = null;
        System.out.println(request.getSession().getAttribute("userLocale"));
        Object userLocale = request.getSession().getAttribute("userLocale");
        if (userLocale==null){
            userLocale = "ru_RU";
        }
        switch (userLocale.toString()){
            case "en_US":
                messageManager = MessageManager.EN;
                break;
            case "ru_RU":
                messageManager = MessageManager.RU;
                break;
        }
        return messageManager;
    }
}
