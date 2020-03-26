package com.maksym.projectmanagement.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();

    T get(ID id);

    T save(T entity);

    T update(T entity);

    boolean delete(ID id);

}
