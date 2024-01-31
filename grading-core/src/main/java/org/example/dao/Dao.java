package org.example.dao;

import java.util.List;

public interface Dao<T> {
    T get (int id);
    List<T> getAll();
    int insert(T t);
    int update(T t);
    int delete(T t);
}
