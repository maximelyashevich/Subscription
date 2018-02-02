package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaperDAOImpl extends AbstractDAO<PaperEdition> implements PaperDAO {
    private static final String SQL_SELECT_PAPERS = "SELECT id FROM PAPERS";
    private static final String SQL_SELECT_PAPERS_BY_ID = "SELECT id, type, title, price, description, publishing_periodicity, age_restriction, is_available, image_path FROM PAPERS WHERE id=?";
    private static final String SQL_DELETE_PAPERS_BY_ID = "DELETE FROM PAPERS WHERE id=?";
    private static final String SQL_SELECT_PAPERS_BY_GENRES = "SELECT SUBSCRIPTION_DB.papers.id, description, " +
            "GROUP_CONCAT(DISTINCT genres.name ORDER BY genres.name SEPARATOR ',') AS 'papers' FROM SUBSCRIPTION_DB.PAPERS " +
            "JOIN SUBSCRIPTION_DB.genres_papers " +
            "ON papers.id=GENRES_PAPERS.papers_id " +
            "JOIN SUBSCRIPTION_DB.genres " +
            "ON GENRES_PAPERS.genre_id=GENRES.id " +
            "GROUP BY PAPERS.id " +
            "HAVING ";

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
                paperEditions.add(findPaperById(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
                close(st);
                close(cn);
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
    public boolean update(PaperEdition entity) {
        return false;
    }

    @Override
    public List<PaperEdition> findPapersByDescription(String data, ArrayList<String> criteria) throws DAOTechnicalException {
        List<PaperEdition> paperEditions = new ArrayList<>();
        ProxyConnection cn = null;
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder(SQL_SELECT_PAPERS_BY_GENRES);
        try {
            cn = ConnectionPool.getInstance().getConnection();
            sql.append(" description LIKE ? AND papers ");
            if (criteria.size()>1) {
                for (int i = 0; i < criteria.size() - 1; i++) {
                    sql.append(" LIKE ? AND papers ");
                }
                sql.append(" LIKE ?");
            }else{
                sql.append(" LIKE ?");
            }
            preparedStatement = cn.prepareStatement(sql.toString());
            preparedStatement.setString(1, "%"+data+"%");
            for (int i=0; i<criteria.size(); i++){
                preparedStatement.setString(i+2, "%"+criteria.get(i)+"%");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paperEditions.add(findPaperById(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            if (preparedStatement!=null)
            close(preparedStatement);
            if (cn != null) {
                close(cn);
            }
        }
        return paperEditions;
    }

    public PaperEdition findPaperById(long id) throws DAOTechnicalException {
        PaperEdition paperEdition = null;
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_PAPERS_BY_ID);
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                paperEdition = new PaperEdition(
                        PaperType.valueOf(resultSet.getString(2).toUpperCase()),
                        resultSet.getString(3).toUpperCase(),
                        resultSet.getBigDecimal(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getBoolean(8),
                        resultSet.getString(9));
                paperEdition.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
                close(st);
                close(cn);
        }
        return paperEdition;
    }
    public boolean deleteById(long paperId) throws DAOTechnicalException {
        ProxyConnection cn = null;
        PreparedStatement st = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_DELETE_PAPERS_BY_ID);
            st.setLong(1, paperId);
            result = st.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getMessage(), e.getCause());
        } finally {
            close(st);
            close(cn);
        }
        return result;
    }

}
