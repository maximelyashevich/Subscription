package com.elyashevich.subscription.util;

public final class RegexComponent {
    public static final String LOGIN_REGEX = "^[\\w_]{5,16}$";
    public static final String PASSWORD_REGEX = "^[\\wа-яА-Я][_\\wа-яА-Я]{5,16}$";
    public static final String POST_INDEX = "^[\\d]{5,12}$";
    public static final String MONEY_REGEX = "^-?[0-9]+(?:\\.[0-9]{1,5})?";
    public static final String RESTRICTION_REGEX = "[0-9]{1,2}";
    public static final String JSP_PATH_REGEX = "/jsp.+";
    public static final String PAPER_TYPE_REGEX = "^([Mm][Aa][Gg][Aa][Zz][Ii][Nn][Ee]|[Nn][Ee][Ww][Ss][Pp][Aa][Pp][Ee][Rr]|[Bb][Oo][Oo][Kk])$";
}
