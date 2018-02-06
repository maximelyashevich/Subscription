package com.elyashevich.subscription.service;

import com.elyashevich.subscription.manager.MessageManager;
import com.elyashevich.subscription.util.TextConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocaleService {

    public String defineLanguage(String language){
        if (language==null) {
            return TextConstant.RU_PARAM;
        }
        switch (language) {
            case TextConstant.EN_PARAM:
                language = TextConstant.EN_PARAM;
                break;
            case TextConstant.RU_PARAM:
                language = TextConstant.RU_PARAM;
                break;
        }
        return language;
    }

    public String definePath(String path){
        String page = null;
        Pattern p = Pattern.compile("/jsp.+");
        Matcher m = p.matcher(path);
        if (m.find()){
            page = m.group();
        }
        return page;
    }

    public MessageManager defineMessageManager(Object userLocale){
        MessageManager messageManager = null;

        if (userLocale==null){
            userLocale = TextConstant.RU_PARAM;
        }
        switch (userLocale.toString()){
            case TextConstant.EN_PARAM:
                messageManager = MessageManager.EN;
                break;
            case TextConstant.RU_PARAM:
                messageManager = MessageManager.RU;
                break;
        }
    return messageManager;
    }
}
