package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.GenreDAO;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class GenreService {
    public List<Genre> findAll() throws ServiceTechnicalException {
        GenreDAO genreDAO = new GenreDAO();
        try {
            return genreDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
