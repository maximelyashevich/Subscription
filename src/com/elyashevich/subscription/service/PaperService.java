package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.PaperDAO;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class PaperService {
    ////////
    ////!!!CHANGE NAME OF FIND_ALL()!!!
    ////////
    public List<PaperEdition> findAll() throws ServiceTechnicalException {
        PaperDAO paperDAO = new PaperDAO();
        try {
            return paperDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
