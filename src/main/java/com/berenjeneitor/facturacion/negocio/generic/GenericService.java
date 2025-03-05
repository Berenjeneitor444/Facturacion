package com.berenjeneitor.facturacion.negocio.generic;

import com.berenjeneitor.facturacion.negocio.ValidationException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID extends Serializable> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void saveOrUpdate(T entity);
    void delete(T entity);
    void validate(T entity) throws ValidationException;
    T processEntity(T entity);
}