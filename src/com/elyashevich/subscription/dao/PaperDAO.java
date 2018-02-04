package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.exception.DAOTechnicalException;

import java.util.ArrayList;
import java.util.List;

public interface PaperDAO {
   List<PaperEdition> findPapersByDescription(String data, ArrayList<String> criteria) throws DAOTechnicalException;
   PaperEdition findPaperById(long id) throws DAOTechnicalException;
   boolean deleteById(long paperId) throws DAOTechnicalException;
}
