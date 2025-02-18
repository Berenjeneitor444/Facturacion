package com.berenjeneitor.facturacion.modelo.DB.DAOs;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T,ID extends Serializable>{
    T create();
    void insert(T entity);
    void update(T entity);
    T get(ID id);
    void delete(ID id);
    List<T> findAll();

}
