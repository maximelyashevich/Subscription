package com.elyashevich.subscription.service;

import com.elyashevich.subscription.dao.PaperDAOImpl;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.exception.ServiceTechnicalException;

import java.util.ArrayList;
import java.util.Arrays;
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
