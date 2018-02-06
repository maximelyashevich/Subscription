package com.elyashevich.subscription.util;

public final class RegexComponent {
    public static final String LOGIN_REGEX = "^[\\w_]{5,16}$";
    public static final String PASSWORD_REGEX = "^[\\wа-яА-Я][_\\wа-яА-Я]{5,16}$";
    public static final String POST_INDEX = "^[\\w_]{6,12}$";
    public static final String EMAIL_REGEX = ".*";
    public static final String CITY_REGEX= ".*";


}
