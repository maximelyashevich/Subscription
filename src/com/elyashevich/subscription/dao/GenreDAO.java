package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;

public interface GenreDAO {
    Genre findByDescription(String data) throws DAOTechnicalException;
}
