package com.elyashevich.subscription.service.impl;

import com.elyashevich.subscription.dao.impl.PaperDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;
import com.elyashevich.subscription.service.PaperService;
import com.elyashevich.subscription.util.TextConstant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PaperServiceImpl implements PaperService {

    @Override
    public List<PaperEdition> findAll() throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        try {
            return paperDAOImpl.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<PaperEdition> findAllWithRestriction(User user) throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        try {
            return paperDAOImpl.findAllWithRestriction(user);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void setPaperFeatures(PaperEdition paperEdition, PaperType paperType, String description, String paperTitle, BigDecimal priceReal, int restrictionInt, int periodInt){
        paperEdition.setType(paperType);
        paperEdition.setDescription(description);
        paperEdition.setTitle(paperTitle);
        paperEdition.setPrice(priceReal);
        paperEdition.setAgeRestriction(restrictionInt);
        paperEdition.setPublishingPeriodicity(periodInt);
    }

    @Override
    public HashSet<PaperEdition> addPaperToSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition){
        if (hashSet==null){
            hashSet = new HashSet<>();
            hashSet.add(paperEdition);
        } else{
            if (hashSet.contains(paperEdition)){
                hashSet.remove(paperEdition);
            }
            hashSet.add(paperEdition);
        }
        return hashSet;
    }

    @Override
    public PaperEdition definePaper(PaperType type, String title, String description, int period, BigDecimal price, int ageRestriction){
        PaperEdition paperEdition = new PaperEdition();

        paperEdition.setType(type);
        paperEdition.setTitle(title);
        paperEdition.setDescription(description);
        paperEdition.setPublishingPeriodicity(period);
        paperEdition.setPrice(price);
        paperEdition.setAgeRestriction(ageRestriction);
        return paperEdition;
    }

    @Override
    public boolean createPaperEdition(PaperEdition paperEdition, String[] genreName) throws ServiceTechnicalException {
        PaperDAOImpl paperDAO = new PaperDAOImpl();
        try {
            return paperDAO.create(paperEdition, genreName);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean updatePaperEdition(PaperEdition paperEdition) throws ServiceTechnicalException {
        PaperDAOImpl paperDAO = new PaperDAOImpl();
        try {
            return paperDAO.update(paperEdition);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public HashSet<PaperEdition> removePaperFromSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition){
        if (hashSet==null){
            hashSet = new HashSet<>();
        } else{
            hashSet.remove(paperEdition);
        }
        return hashSet;
    }

    @Override
    public void defineProductPrice(PaperEdition paperEdition, int duration){
        BigDecimal price = paperEdition.getPrice();
        price = price.multiply(new BigDecimal((double)duration/3));
        paperEdition.setPrice(price);
    }

    @Override
    public BigDecimal defineFinalPrice(HashSet<PaperEdition> hashSet){
        BigDecimal finalPrice = new BigDecimal(0);
        if (hashSet==null || hashSet.isEmpty()){
            return new BigDecimal(0);
        }
        for (PaperEdition paperEdition: hashSet){
            finalPrice = finalPrice.add(paperEdition.getPrice());
        }
        return finalPrice;
    }

    @Override
    public PaperEdition findPaperById(long id) throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        try {
            return paperDAOImpl.findPaperById(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<PaperEdition> findAllByDescription(User user, String data, String dataCriteria) throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        if (dataCriteria==null){
            dataCriteria= TextConstant.EMPTY_STRING;
        }
        if (data==null){
            data=TextConstant.EMPTY_STRING;
        }
        try {
            return paperDAOImpl.findPapersByDescription(user, data, defineGenreList(dataCriteria));
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    private static ArrayList<String> defineGenreList(String dataCriteria){
        dataCriteria = dataCriteria.replaceAll("\"", TextConstant.EMPTY_STRING);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(dataCriteria.split(",")));
        if (list.isEmpty()){
            list.add(TextConstant.EMPTY_STRING);
        }
        return list;
    }

}
