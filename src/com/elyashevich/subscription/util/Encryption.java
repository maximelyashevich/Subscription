package com.elyashevich.subscription.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SECRET = "salt";

    public static String encryptPassword(String password) {
        StringBuilder result = new StringBuilder();
        password = password + SECRET;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            for (byte aDigested : digested) {
                result.append(Integer.toHexString(0xff & aDigested));
            }
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.ERROR, ex);
        }
        return result.toString();
    }
}
