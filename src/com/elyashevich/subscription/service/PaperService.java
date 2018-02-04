package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.impl.PaperDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PaperService {
    ////////
    ////!!!CHANGE NAME OF FIND_ALL()!!!
    ////////
    public List<PaperEdition> findAll() throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        try {
            return paperDAOImpl.findAll();
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

//    public static void main(String[] args) {
//        LocalDate localDateTime = LocalDate.now();
//        localDateTime = localDateTime.plusMonths(3);
//        System.out.println(localDateTime);
//        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH-mm-ss")));
//    }

    public HashSet<PaperEdition> addPaperToSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition){
        if (hashSet==null){
            hashSet = new HashSet<PaperEdition>();
            hashSet.add(paperEdition);
        } else{
            if (hashSet.contains(paperEdition)){
                hashSet.remove(paperEdition);
            }
            hashSet.add(paperEdition);
        }
        return hashSet;
    }

    public HashSet<PaperEdition> removePaperFromSet(HashSet<PaperEdition> hashSet, PaperEdition paperEdition){
        if (hashSet==null){
            hashSet = new HashSet<PaperEdition>();
        } else{
            hashSet.remove(paperEdition);
        }
        return hashSet;
    }

    public void defineProductPrice(PaperEdition paperEdition, int duration){
        BigDecimal price = paperEdition.getPrice();
        price = price.multiply(new BigDecimal((double)duration/3));
        paperEdition.setPrice(price);
    }

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

    public PaperEdition findPaperById(long id) throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        try {
            return paperDAOImpl.findPaperById(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    public List<PaperEdition> findAllByDescription(String data, String dataCriteria) throws ServiceTechnicalException {
        PaperDAOImpl paperDAOImpl = new PaperDAOImpl();
        if (dataCriteria==null){
            dataCriteria="";
        }
        if (data==null){
            data="";
        }
        try {
            return paperDAOImpl.findPapersByDescription(data, defineGenreList(dataCriteria));
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e.getMessage(), e.getCause());
        }
    }

    private static ArrayList<String> defineGenreList(String dataCriteria){
        dataCriteria = dataCriteria.replaceAll("\"", "");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(dataCriteria.split(",")));
        if (list.isEmpty()){
            list.add("");
        }
        return list;
    }

}
