package com.elyashevich.subscription.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elyashevich.subscription.util.RegexComponent.*;

public class UserValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COUNTRY_PATH = "country\\country.txt";
    public boolean isLoginAndPasswordCorrect(String login, String password){
        boolean result = false;
        LOGGER.log(Level.INFO, "Start checking login and password for correctness");
        if (isDataCorrect(login) && isDataCorrect(password)) {
            result = isLoginCorrect(login)&& isPasswordCorrect(password);
        }
        return result;
    }

    public boolean isLoginCorrect(String login){
        boolean result = false;
        LOGGER.log(Level.INFO, "Start checking login...");
        if (isDataCorrect(login)) {
            Pattern patternLogic = Pattern.compile(LOGIN_REGEX);
            Matcher matcher = patternLogic.matcher(login);
            result = matcher.matches();
        }
        return result;
    }


    public boolean isPasswordCorrect(String password){
        boolean result = false;
        LOGGER.log(Level.INFO, "Start checking password...");
        if (isDataCorrect(password)) {
            Pattern patternLogic = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = patternLogic.matcher(password);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isPostIndexCorrect(String postIndex){
        boolean result = false;
        LOGGER.log(Level.INFO, "Start checking login and password for correctness...");
        if (isDataCorrect(postIndex)) {
            Pattern patternLogic = Pattern.compile(POST_INDEX);
            Matcher matcher = patternLogic.matcher(postIndex);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isDataCorrect(String data){
        LOGGER.log(Level.INFO, "Start checking data for correctness...");
        return data!=null && !data.isEmpty();
    }
}
