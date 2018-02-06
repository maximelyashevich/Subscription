package com.elyashevich.subscription.service;

import com.elyashevich.subscription.validator.UserValidator;

public class DefaultService {
    public String defineTitleMessage(String firstName, String lastName, String detailAddress, String userName, String password, String postIndex, String country, String city, String dob) {
        String result = "message.registrationsuccess";
        UserValidator validator = new UserValidator();
        if (!validator.isDataCorrect(firstName) || !validator.isDataCorrect(lastName) || !validator.isDataCorrect(country) || !validator.isDataCorrect(city) ||
                !validator.isDataCorrect(detailAddress) || !validator.isDataCorrect(dob)) {
            result = "message.errorData";
        }
        if (!validator.isLoginCorrect(userName)) {
                result = "message.loginIn";
            }
        if (!validator.isPasswordCorrect(password)) {
                result = "message.passwordIn";
            }
        if (!validator.isPostIndexCorrect(postIndex)) {
                result = "message.postIn";
            }
        return result;
    }
}
