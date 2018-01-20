package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaperDAO extends AbstractDAO<PaperEdition>{
    private static final String SQL_SELECT_PAPERS = "SELECT type, title, price, description, publishing_periodicity, age_restriction, is_available, image_path FROM PAPERS";

    @Override
    public List<PaperEdition> findAll() throws DAOTechnicalException {
        List<PaperEdition> paperEditions = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_PAPERS);

            while (resultSet.next()) {
                paperEditions.add(new PaperEdition(
                        PaperType.valueOf(resultSet.getString(1).toUpperCase()),
                                resultSet.getString(2).toUpperCase(),
                                resultSet.getBigDecimal(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(5),
                                        resultSet.getInt(6),
                                resultSet.getBoolean(7),
                                resultSet.getString(8)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            if (cn!=null) {
                close(cn);
            }
        }
        return paperEditions;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(PaperEdition entity) {
        return false;
    }

    @Override
    public boolean create(PaperEdition entity) {
        return false;
    }

    @Override
    public PaperEdition update(PaperEdition entity) {
        return null;
    }

    public static void main(String[] args) {
        PaperDAO paperDAO = new PaperDAO();
        try {
            System.out.println(paperDAO.findAll());
        } catch (DAOTechnicalException e) {
            e.printStackTrace();
        }
    }
}
