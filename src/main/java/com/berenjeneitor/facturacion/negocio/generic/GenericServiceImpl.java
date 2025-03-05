package com.berenjeneitor.facturacion.negocio.generic;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {
    protected final GenericDAO<T, ID> dao;

    public GenericServiceImpl(GenericDAO<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public Optional<T> findById(ID id) {
        return dao.findById(id);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public void saveOrUpdate(T entity) {
        dao.saveOrUpdate(processEntity(entity));
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        Optional<T> entity = findById(id);
        entity.ifPresent(this::delete);
    }

}