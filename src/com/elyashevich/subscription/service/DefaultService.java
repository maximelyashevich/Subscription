package com.elyashevich.subscription.service;

import java.time.LocalDate;

public interface DefaultService {
    String checkTitleMessage(LocalDate date, String firstName, String lastName, String detailAddress, String userName, String password, String postIndex, String country, String city);

    String checkNotUniqueData(LocalDate date, String postIndex, String firstName, String lastName, String country, String city, String detailAddress);

    String checkPaperType(String paperType);

    String checkAddTitleMessage(String paperTitle, String description, String price, String restriction);
}
