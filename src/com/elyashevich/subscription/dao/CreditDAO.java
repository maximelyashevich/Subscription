package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Credit;
import com.elyashevich.subscription.exception.DAOTechnicalException;

import java.util.List;

public interface CreditDAO {
    List<Credit> findCreditByUserId(long id) throws DAOTechnicalException;
}
