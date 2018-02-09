package com.elyashevich.subscription.service;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

public interface PaperService {
    void setPaperFeatures(PaperEdition paperEdition, PaperType paperType, String description, String paperTitle, BigDecimal priceReal, int restrictionInt, int periodInt);

    void defineProductPrice(PaperEdition paperEdition, int duration);

    boolean createPaperEdition(PaperEdition paperEdition, String[] genreName) throws ServiceTechnicalException;

    boolean updatePaperEdition(PaperEdition paperEdition) throws ServiceTechnicalException;

    PaperEdition definePaper(PaperType type, String title, String description, int period, BigDecimal price, int ageRestriction);

    PaperEdition findPaperById(long id) throws ServiceTechnicalException;

    List<PaperEdition> findAll() throws ServiceTechnicalException;

    List<PaperEdition> findAllWithRestriction(User user) throws ServiceTechnicalException;

    List<PaperEdition> findAllByDescription(User user, String data, String dataCriteria) throws ServiceTechnicalException;

    HashSet<PaperEdition> removePaperFromSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition);

    HashSet<PaperEdition> addPaperToSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition);

    BigDecimal defineFinalPrice(HashSet<PaperEdition> hashSet);
}
