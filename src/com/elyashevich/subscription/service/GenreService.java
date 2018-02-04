package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.impl.GenreDAOImpl;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class GenreService {
    public List<Genre> findAll() throws ServiceTechnicalException {
        GenreDAOImpl genreDAOImpl = new GenreDAOImpl();
        try {
            return genreDAOImpl.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
