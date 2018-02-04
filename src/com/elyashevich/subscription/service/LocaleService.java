package com.elyashevich.subscription.service;

import com.elyashevich.subscription.manager.MessageManager;

public class LocaleService {

    public MessageManager defineMessageManager(Object userLocale){
        MessageManager messageManager = null;

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
