package com.berenjeneitor.facturacion.modelo.DB.DAOs.implementaciones;

import com.berenjeneitor.facturacion.modelo.DB.DAOs.interfaces.GenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected SessionFactory sessionFactory;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T findById(ID id) {
        return getCurrentSession().get(persistentClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getCurrentSession()
                .createQuery("from " + persistentClass.getName())
                .list();
    }

    @Override
    public void save(T entity) {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void update(T entity) {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}