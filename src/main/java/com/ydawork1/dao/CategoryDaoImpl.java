package com.ydawork1.dao;

import com.ydawork1.model.Category;

public class CategoryDaoImpl extends AbstractDao<Category, Long> implements CategoryDao{
    public CategoryDaoImpl(Class<Category> clazz) {
        super(clazz);
    }
}
