package com.elyashevich.subscription.logic;

public class LoginLogic {
    public static boolean checkLogin(String realLogin, String realPass, String enterLogin, String enterPass) {
        return realLogin.equals(enterLogin) && realPass.equals(enterPass);
    }
}