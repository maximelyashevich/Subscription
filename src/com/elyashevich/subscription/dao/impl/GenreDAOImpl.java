package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.GenreDAO;
import com.elyashevich.subscription.entity.Genre;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl extends AbstractDAO<Genre> implements GenreDAO {
    private static final String SQL_SELECT_ALL_GENRES = "SELECT name FROM GENRES";
    public static final String SQL_SELECT_GENRE_BY_DESCRIPTION = "SELECT id FROM genres WHERE name LIKE ?";

    @Override
    public List<Genre> findAll() throws DAOTechnicalException {
        List<Genre> genres = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_GENRES);
            while (resultSet.next()) {
                genres.add(new Genre(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            close(cn);
        }
        return genres;
    }

    @Override
    public Genre findByDescription(String data) throws DAOTechnicalException {
        Genre genre = new Genre(data);
        ProxyConnection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SQL_SELECT_GENRE_BY_DESCRIPTION);
            preparedStatement.setString(1, "%" + data + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(preparedStatement);
            close(cn);
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
