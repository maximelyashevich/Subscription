package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;

import java.util.ArrayList;
import java.util.List;

public interface PaperDAO {
    List<PaperEdition> findPapersByDescription(User user, String data, ArrayList<String> criteria) throws DAOTechnicalException;

    PaperEdition findPaperById(long id) throws DAOTechnicalException;

    boolean deleteById(long paperId) throws DAOTechnicalException;

    List<PaperEdition> findAllWithRestriction(User user) throws DAOTechnicalException;

    void insertGenreToPaper(Genre genre, PaperEdition paperEdition) throws DAOTechnicalException;

    boolean create(PaperEdition paperEdition, String[] genreNames) throws DAOTechnicalException;
}
