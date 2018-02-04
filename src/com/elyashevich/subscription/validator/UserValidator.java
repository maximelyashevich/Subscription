package com.elyashevich.subscription.validator;

import com.elyashevich.subscription.exception.InputTechnicalException;
import com.elyashevich.subscription.input.ReadDataFromFile;
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
        if (login!=null && !login.isEmpty() && password!=null && !password.isEmpty()) {
            Pattern patternLogic = Pattern.compile(LOGIN_REGEX);
            Pattern patternPassword = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = patternLogic.matcher(login);
            Matcher matcherPassword = patternPassword.matcher(password);
            result = matcher.matches() && matcherPassword.matches();
        }
        return result;
    }

    private boolean isUserDataComponentCorrect(String data, String regex){
        boolean result = false;
        if (data!=null && !data.isEmpty()) {
            Pattern patternName = Pattern.compile(regex);
            Matcher matcher = patternName.matcher(data);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isUserDataCorrect(String name, String surname, String email){
        return isUserDataComponentCorrect(name, NAME_REGEX)&&
                isUserDataComponentCorrect(surname, NAME_REGEX)&&
                    isUserDataComponentCorrect(email, EMAIL_REGEX);
    }

    public boolean isCountryExists(String country){
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        try {
            return readDataFromFile.readFromFile(COUNTRY_PATH).contains(country.toLowerCase());
        } catch (InputTechnicalException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        return false;
    }
}
