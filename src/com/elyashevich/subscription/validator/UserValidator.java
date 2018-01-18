package com.elyashevich.subscription.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    public boolean isDataCorrect(String data, String regex){
        boolean result = false;
        if (data!=null && !data.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            result = matcher.matches();
        }
        return result;
    }
}
