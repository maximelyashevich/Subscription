package com.elyashevich.subscription.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elyashevich.subscription.util.RegexComponent.*;

public class UserValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    public boolean isLoginAndPasswordCorrect(String login, String password){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking login and password for correctness");
        if (isUserDataCorrect(login) && isUserDataCorrect(password)) {
            result = isLoginCorrect(login)&& isPasswordCorrect(password);
        }
        return result;
    }

    public boolean isLoginCorrect(String login){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking login...");
        if (isUserDataCorrect(login)) {
            Pattern patternLogic = Pattern.compile(LOGIN_REGEX);
            Matcher matcher = patternLogic.matcher(login);
            result = matcher.matches();
        }
        return result;
    }


    public boolean isPasswordCorrect(String password){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking password...");
        if (isUserDataCorrect(password)) {
            Pattern patternLogic = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = patternLogic.matcher(password);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isPostIndexCorrect(String postIndex){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking login and password for correctness...");
        if (isUserDataCorrect(postIndex)) {
            Pattern patternLogic = Pattern.compile(POST_INDEX);
            Matcher matcher = patternLogic.matcher(postIndex);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isDataCorrect(LocalDate localDate){
        return localDate!=null && localDate.isBefore(LocalDate.now());
    }

    public boolean isUserDataCorrect(String data){
        LOGGER.log(Level.INFO, "Checking user data for correctness...");
        return data!=null && !data.isEmpty();
    }
}
