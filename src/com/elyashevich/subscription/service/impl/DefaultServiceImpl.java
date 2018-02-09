package com.elyashevich.subscription.service.impl;

import com.elyashevich.subscription.service.DefaultService;
import com.elyashevich.subscription.util.TextConstant;
import com.elyashevich.subscription.validator.PaperValidator;
import com.elyashevich.subscription.validator.UserValidator;

import java.time.LocalDate;

public class DefaultServiceImpl implements DefaultService {
    @Override
    public String checkTitleMessage(LocalDate date, String firstName, String lastName, String detailAddress, String userName, String password, String postIndex, String country, String city) {
        String result = TextConstant.SUCCESS;
        UserValidator validator = new UserValidator();
        if (!validator.isUserDataCorrect(firstName) || !validator.isUserDataCorrect(lastName) || !validator.isUserDataCorrect(country) || !validator.isUserDataCorrect(city) ||
                !validator.isUserDataCorrect(detailAddress)) {
            result = "message.errorData";
        }
        if (!validator.isLoginCorrect(userName)) {
                result = "message.loginIn";
            }
        if (!validator.isPasswordCorrect(password)) {
                result = "message.passwordIn";
            }
       if (!validator.isDateCorrect(date))   {
            result = "message.dateIn";
        }
        if (!validator.isPostIndexCorrect(postIndex)) {
                result = "message.postIn";
            }
        return result;
    }

    @Override
    public String checkNotUniqueData(LocalDate date, String postIndex, String firstName, String lastName, String country, String city, String detailAddress){
        String result = TextConstant.SUCCESS_OPERATION;
        UserValidator validator = new UserValidator();
        if (!validator.isUserDataCorrect(firstName) || !validator.isUserDataCorrect(lastName) || !validator.isUserDataCorrect(country) || !validator.isUserDataCorrect(city) ||
                !validator.isUserDataCorrect(detailAddress)) {
            result = "message.errorData";
        }
        if (!validator.isDateCorrect(date))   {
            result = "message.dateIn";
        }
        if (!validator.isPostIndexCorrect(postIndex)) {
            result = "message.postIn";
        }
        return result;
    }

    @Override
    public String checkPaperType(String paperType){
        String result = TextConstant.SUCCESS_OPERATION;
        PaperValidator validator = new PaperValidator();
        if (!validator.isPaperDataCorrect(paperType)) {
            result = "message.errorData";
        }
        if (!validator.isPaperTypeCorrect(paperType))   {
            result = "message.paperTypeIn";
        }
        return result;
    }

    @Override
    public String checkAddTitleMessage(String paperTitle, String description, String price, String restriction) {
        String result = TextConstant.SUCCESS_OPERATION;
        PaperValidator validator = new PaperValidator();

        if (!validator.isPaperDataCorrect(paperTitle) || !validator.isPaperDataCorrect(description) ||
                !validator.isPaperDataCorrect(price) || !validator.isPaperDataCorrect(restriction)) {
            result = "message.errorData";
        }
        if (!validator.isPaperDataCorrect(paperTitle)) {
            result = "message.paperTitle";
        }
        if (!validator.isPaperDataCorrect(description)) {
            result = "message.descriptionIn";
        }
        if (!validator.isMoneyCorrect(price)) {
            result = "message.moneyIn";
        }
        if (!validator.isRestrictionCorrect(restriction)) {
            result = "message.restrictionIn";
        }
        return result;
    }
}
