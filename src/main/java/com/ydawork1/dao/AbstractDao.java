package com.ydawork1.dao;

import com.ydawork1.dao.helper.DbConnector;
import com.ydawork1.dao.helper.QueryCreator;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, ID> implements GenericDao<T, ID> {
    private final Class<T> clazz;
    private final Connection connection = DbConnector.connect().get();

    protected AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T save(T t) {
        QueryCreator<T, ID> queryCreator = new QueryCreator<>(clazz);
        String query = queryCreator.saveQuery(t);
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public Optional<T> get(ID id) {
        QueryCreator<T, ID> queryCreator = new QueryCreator<>(clazz);
        String query = queryCreator.getQuery(id);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            T entity = clazz.newInstance();
            if (resultSet.next()) {
                Field [] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(field.getName()));
                }
                return Optional.of(entity);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public T update(T t) {
        QueryCreator<T, ID> queryCreator = new QueryCreator<>(clazz);
        String query = queryCreator.updateQuery(t);
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void delete(ID id) {
        QueryCreator<T, ID> queryCreator = new QueryCreator<>(clazz);
        String query = queryCreator.deleteQuery(id);
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> entityList = new ArrayList<>();
        QueryCreator<T, ID> queryCreator = new QueryCreator<>(clazz);
        String query = queryCreator.getAll();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                T entity = clazz.newInstance();
                Field [] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(field.getName()));
                }
                entityList.add(entity);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return entityList;
    }
}
