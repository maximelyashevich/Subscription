package com.elyashevich.subscription.util;

public final class RegexComponent {
    public static final String LOGIN_REGEX = "^[a-zA-Z][\\w-_.]{1,20}$";
    public static final String PASSWORD_REGEX = "^.{5,}$";
    public static final String NAME_REGEX = ".*";
    public static final String EMAIL_REGEX = ".*";
    public static final String CITY_REGEX= ".*";

}
