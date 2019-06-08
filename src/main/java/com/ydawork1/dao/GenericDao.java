package com.ydawork1.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {

    T save(T t);

    Optional<T> get(ID id);

    T update(T t);

    void delete(ID id);

    List<T> getAll();
}
