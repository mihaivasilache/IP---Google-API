package com.tourism.microservices.services;

import java.util.List;

/**
 * Created by apiriu on 4/28/2017.
 */
public interface CrudService<T> {
    T save(T entity);
    List<T> getAll();
    T getById(Long id);
    void delete(Long id);
}
