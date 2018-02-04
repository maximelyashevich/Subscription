package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.exception.DAOTechnicalException;

public interface AddressDAO {
    Address createAddress(Address address) throws DAOTechnicalException;
    Address findAddressById(long id) throws DAOTechnicalException;
}
