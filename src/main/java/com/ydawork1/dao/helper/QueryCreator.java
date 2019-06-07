package com.ydawork1.dao.helper;

import java.lang.reflect.Field;
import java.util.Arrays;

public class QueryCreator<T, ID> {
    private final Class<T> clazz;

    public QueryCreator(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String saveQuery(T entity) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(getTableName(entity))
                .append("(")
                .append(getFieldNames(entity.getClass()))
                .append(") VALUES (")
                .append(getFieldValues(entity))
                .append(")");
        return query.toString();
    }

    public String getQuery(ID id) {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(clazz.getSimpleName().toUpperCase())
                .append(" WHERE id = ")
                .append(id);
        return query.toString();
    }

    public String updateQuery(T entity) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(getTableName(entity))
                .append(" SET ")
                .append(getFieldNamesAndValues(entity))
                .append(" WHERE id = ")
                .append(getIdValue(entity));
        return query.toString();
    }

    public String deleteQuery(ID id) {
        StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(clazz.getSimpleName().toUpperCase())
                .append(" WHERE id = ")
                .append(id);
        return query.toString();
    }

    public String getAll() {
        StringBuilder query = new StringBuilder("SELECT ");
        query.append("id, ")
                .append(getFieldNames(clazz))
                .append(" FROM ")
                .append(clazz.getSimpleName().toUpperCase());
        return query.toString();
    }

    private String getFieldNames(Class clazz) {
        StringBuilder column = new StringBuilder();
        Field [] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> column.append(field.getName()).append(", "));
        column.delete(0, 4).delete(column.length() -2, column.length());
        return column.toString();
    }

    private String getTableName(T entity) {
        String tableName = entity.getClass().getSimpleName().toUpperCase();
        return tableName;
    }

    private String getFieldNamesAndValues(T entity) {
        StringBuilder result = new StringBuilder();
        String [] fieldNames = getFieldNames(entity.getClass()).split(", ");
        String [] fieldValues = getFieldValues(entity).split(", ");
        for (int i = 0; i < fieldNames.length; i++) {
            result.append(fieldNames[i])
                    .append(" = ")
                    .append(fieldValues[i])
            .append(", ");
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    private String getFieldValues(T entity) {
        Field [] fields = entity.getClass().getDeclaredFields();
        StringBuilder value = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getName().equals("id")) {
                    //ignore id field as id autoincrement
                } else if (field.getType().isPrimitive()) {
                    value.append(field.get(entity).toString());
                } else {
                    value.append('\'' + field.get(entity).toString() + '\'');
                }
                value.append(", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        value.delete(0, 2).delete(value.length() - 2, value.length());

        return value.toString();
    }

    private String getIdValue(T entity) {
        StringBuilder idValue =new StringBuilder();
        Field [] fields = entity.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> field.getName().equals("id"))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        idValue.append(field.get(entity).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return idValue.toString();
    }
}
