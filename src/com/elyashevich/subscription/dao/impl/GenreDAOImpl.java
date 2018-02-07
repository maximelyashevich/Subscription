package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.GenreDAO;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;
import com.elyashevich.subscription.util.TextConstant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl extends AbstractDAO<Genre> implements GenreDAO {
    private static final String SQL_SELECT_ALL_GENRES = "SELECT name FROM GENRES";
    private static final String SQL_SELECT_GENRE_BY_DESCRIPTION = "SELECT id FROM genres WHERE name LIKE ?";

    @Override
    public List<Genre> findAll() throws DAOTechnicalException {
        List<Genre> genres = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GENRES);
            while (resultSet.next()) {
                genres.add(new Genre(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in GenreDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return genres;
    }

    @Override
    public Genre findByDescription(String data) throws DAOTechnicalException {
        Genre genre = new Genre(data);
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_GENRE_BY_DESCRIPTION);
            preparedStatement.setString(1, TextConstant.LIKE_COMPONENT_SQL + data + TextConstant.LIKE_COMPONENT_SQL);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in GenreDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return genre;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Genre entity) {
        return false;
    }

    @Override
    public boolean create(Genre entity) {
        return false;
    }

    @Override
    public boolean update(Genre entity) {
        return false;
    }
}
