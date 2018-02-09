package com.elyashevich.subscription.service.impl;

import com.elyashevich.subscription.dao.impl.GenreDAOImpl;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    @Override
    public List<Genre> findAll() throws ServiceTechnicalException {
        GenreDAOImpl genreDAOImpl = new GenreDAOImpl();
        try {
            return genreDAOImpl.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
