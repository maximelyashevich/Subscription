package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.GenreDAO;
import com.elyashevich.subscription.dao.PaperDAO;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.PaperType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;
import com.elyashevich.subscription.util.TextConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaperDAOImpl extends AbstractDAO<PaperEdition> implements PaperDAO {
    private static final String SQL_SELECT_PAPERS = "SELECT id FROM PAPERS";
    private static final String SQL_SELECT_PAPERS_WITH_RESTRICTION = "SELECT id FROM PAPERS WHERE papers.is_available=1 and papers.age_restriction<=(YEAR(CURRENT_DATE)-YEAR(?))-(DATE_FORMAT(CURRENT_DATE, '%m%d')<DATE_FORMAT(?, '%m%d'))";
    private static final String SQL_SELECT_PAPERS_BY_ID = "SELECT id, type, title, price, description, publishing_periodicity, age_restriction, is_available, image_path FROM PAPERS WHERE id=?";
    private static final String SQL_DELETE_PAPERS_BY_ID = "DELETE FROM PAPERS WHERE id=?";
    private static final String SQL_INSERT_PAPER = "INSERT INTO papers(type, title, description, publishing_periodicity, price, age_restriction) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PAPER = "UPDATE papers SET type=?, title=?, description=?, publishing_periodicity=?, price=?, age_restriction=?, image_path=?, is_available=?  WHERE id=?";
    private static final String SQL_INSERT_PAPER_WITH_GENRE = "INSERT INTO genres_papers(genre_id, papers_id) VALUES(?, ?)";
    private static final String SQL_SELECT_PAPERS_BY_GENRES = "SELECT SUBSCRIPTION_DB.papers.id, description, " +
            "GROUP_CONCAT(DISTINCT genres.name ORDER BY genres.name SEPARATOR ',') AS 'papers', " +
            "papers.is_available as 'availability', papers.age_restriction as 'ageR'," +
            "papers.age_restriction as 'ageR' FROM SUBSCRIPTION_DB.PAPERS " +
            "JOIN SUBSCRIPTION_DB.genres_papers " +
            "ON papers.id=GENRES_PAPERS.papers_id " +
            "JOIN SUBSCRIPTION_DB.genres " +
            "ON GENRES_PAPERS.genre_id=GENRES.id " +
            "GROUP BY PAPERS.id " +
            "HAVING ";

    private static final String DESCRIPTION_PAPER_LIKE = " description LIKE ? AND papers ";
    private static final String PAPER_LIKE = " LIKE ? AND papers ";
    private static final String LIKE_SQL = " LIKE ?";
    private static final String AVAILABILITY_SQL = " AND availability=1 ";
    private static final String AGE_SQL = " AND ageR<=(YEAR(CURRENT_DATE)-YEAR(?))-(DATE_FORMAT(CURRENT_DATE, '%m%d')<DATE_FORMAT(?, '%m%d'))";

    @Override
    public List<PaperEdition> findAllWithRestriction(User user) throws DAOTechnicalException {
        List<PaperEdition> paperEditions = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_PAPERS_WITH_RESTRICTION);
            preparedStatement.setDate(1, Date.valueOf(user.getBirthday()));
            preparedStatement.setDate(2, Date.valueOf(user.getBirthday()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paperEditions.add(findPaperById(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return paperEditions;
    }


    @Override
    public List<PaperEdition> findAll() throws DAOTechnicalException {
        List<PaperEdition> paperEditions = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_PAPERS);
            while (resultSet.next()) {
                paperEditions.add(findPaperById(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
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
    public boolean create(PaperEdition paperEdition) {
        return false;
    }

    @Override
    public boolean create(PaperEdition paperEdition, String[] genreNames) throws DAOTechnicalException {
        boolean flag;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            GenreDAO genreDAO = new GenreDAOImpl();
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_PAPER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paperEdition.getType().name());
            preparedStatement.setString(2, paperEdition.getTitle());
            preparedStatement.setString(3, paperEdition.getDescription());
            preparedStatement.setInt(4, paperEdition.getPublishingPeriodicity());
            preparedStatement.setBigDecimal(5, paperEdition.getPrice());
            preparedStatement.setInt(6, paperEdition.getAgeRestriction());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                paperEdition.setId(rs.getInt(1));
            }
            for (String genreName : genreNames) {
                insertGenreToPaper(genreDAO.findByDescription(genreName), paperEdition);
            }
            flag = true;
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return flag;
    }

    @Override
    public boolean update(PaperEdition paperEdition) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PAPER);
            statement.setString(1, paperEdition.getType().name());
            statement.setString(2, paperEdition.getTitle());
            statement.setString(3, paperEdition.getDescription());
            statement.setInt(4, paperEdition.getPublishingPeriodicity());
            statement.setBigDecimal(5, paperEdition.getPrice());
            statement.setInt(6, paperEdition.getAgeRestriction());
            statement.setString(7, paperEdition.getImagePath());
            statement.setInt(8, paperEdition.isAvailability() ? 1 : 0);
            statement.setLong(9, paperEdition.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<PaperEdition> findPapersByDescription(User user, String data, ArrayList<String> criteria) throws DAOTechnicalException {
        List<PaperEdition> paperEditions = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder(SQL_SELECT_PAPERS_BY_GENRES);
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            sql.append(DESCRIPTION_PAPER_LIKE);
            if (criteria.size() > 1) {
                for (int i = 0; i < criteria.size() - 1; i++) {
                    sql.append(PAPER_LIKE);
                }
                sql.append(LIKE_SQL);
            } else {
                sql.append(LIKE_SQL);
            }
            sql.append(AVAILABILITY_SQL).append(AGE_SQL);
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, TextConstant.LIKE_COMPONENT_SQL + data + TextConstant.LIKE_COMPONENT_SQL);
            for (int i = 0; i < criteria.size(); i++) {
                preparedStatement.setString(i + 2, TextConstant.LIKE_COMPONENT_SQL + criteria.get(i) + TextConstant.LIKE_COMPONENT_SQL);
            }
            preparedStatement.setDate(criteria.size() + 2, Date.valueOf(user.getBirthday()));
            preparedStatement.setDate(criteria.size() + 3, Date.valueOf(user.getBirthday()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paperEditions.add(findPaperById(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return paperEditions;
    }

    @Override
    public PaperEdition findPaperById(long id) throws DAOTechnicalException {
        PaperEdition paperEdition = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_PAPERS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
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
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return paperEdition;
    }

    @Override
    public boolean deleteById(long paperId) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_DELETE_PAPERS_BY_ID);
            statement.setLong(1, paperId);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public void insertGenreToPaper(Genre genre, PaperEdition paperEdition) throws DAOTechnicalException {

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_PAPER_WITH_GENRE);
            preparedStatement.setLong(1, genre.getId());
            preparedStatement.setLong(2, paperEdition.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOTechnicalException("Exception in PaperDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

}
