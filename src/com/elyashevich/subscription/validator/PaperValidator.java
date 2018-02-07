package com.elyashevich.subscription.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elyashevich.subscription.util.RegexComponent.MONEY_REGEX;
import static com.elyashevich.subscription.util.RegexComponent.PAPER_TYPE_REGEX;
import static com.elyashevich.subscription.util.RegexComponent.RESTRICTION_REGEX;

public class PaperValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    public boolean isPaperDataCorrect(String data){
        LOGGER.log(Level.INFO, "Checking paper data for correctness...");
        return data!=null && !data.isEmpty();
    }

    public boolean isMoneyCorrect(String money){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking money...");
        if (isPaperDataCorrect(money)) {
            Pattern patternLogic = Pattern.compile(MONEY_REGEX);
            Matcher matcher = patternLogic.matcher(money);
            result = matcher.matches();
        }
        return result && !money.contains("-");
    }

    public boolean isPaperTypeCorrect(String paperType){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking paperType...");
        if (isPaperDataCorrect(paperType)) {
            paperType = paperType.toLowerCase();
            Pattern patternLogic = Pattern.compile(PAPER_TYPE_REGEX);
            Matcher matcher = patternLogic.matcher(paperType);
            result = matcher.matches();
        }
        return result;
    }

    public boolean isRestrictionCorrect(String restriction){
        boolean result = false;
        LOGGER.log(Level.INFO, "Checking restriction...");
        if (isPaperDataCorrect(restriction)) {
            Pattern patternLogic = Pattern.compile(RESTRICTION_REGEX);
            Matcher matcher = patternLogic.matcher(restriction);
            result = matcher.matches();
        }
        return result;
    }
}
