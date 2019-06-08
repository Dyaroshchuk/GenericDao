package com.ydawork1.dao;

import com.ydawork1.model.User;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {
    public UserDaoImpl(Class<User> clazz) {
        super(clazz);
    }
}
