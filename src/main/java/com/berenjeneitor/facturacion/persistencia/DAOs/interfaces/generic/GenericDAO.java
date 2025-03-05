package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID extends Serializable> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void saveOrUpdate(T entity);
    void delete(T entity);
    int count(String whereClausule);
}