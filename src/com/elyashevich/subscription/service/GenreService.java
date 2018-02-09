package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public interface GenreService {
    List<Genre> findAll() throws ServiceTechnicalException;
}
