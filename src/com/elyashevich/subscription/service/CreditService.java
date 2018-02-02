package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.CreditDAO;
import com.elyashevich.subscription.entity.Credit;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.List;

public class CreditService {
    public List<Credit> findAll() throws ServiceTechnicalException {
        CreditDAO creditDAO = new CreditDAO();
        try {
            return creditDAO.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public List<Credit> findAllByUserId(long id) throws ServiceTechnicalException {
        CreditDAO creditDAO = new CreditDAO();
        try {
            return creditDAO.findCreditByUserId(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }
}
