package com.elyashevich.subscription.util;

public final class RegexComponent {
    public static final String LOGIN_REGEX = "^[\\w_]{5,16}$";
    public static final String PASSWORD_REGEX = "^[\\wа-яА-Я][_\\wа-яА-Я]{5,16}$";
    public static final String POST_INDEX = "^[\\w_]{6,12}$";
    public static final String MONEY_REGEX = "^-?[0-9]+(?:\\.[0-9]{1,5})?";
    public static final String RESTRICTION_REGEX = "[0-9]{1,2}";
    public static final String JSP_PATH_REGEX = "/jsp.+";
    public static final String PAPER_TYPE_REGEX = "^(magazine|newspaper|book)$";
}
