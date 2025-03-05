package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected final SessionFactory sessionFactory;
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "sessionFactory no puede ser null");
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(persistentClass, id));
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + persistentClass.getName(), persistentClass)
                    .getResultList();
        }
    }

    @Override
    public void saveOrUpdate(T entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
    @Override
    public int count(String whereClausule){
        try (Session session = sessionFactory.openSession()) {
            if(whereClausule != null){
                return session.createQuery("select count(*) from " + persistentClass.getName() + " where " + whereClausule, Number.class)
                        .getSingleResult().intValue();
            }
            return session.createQuery("select count(*) from " + persistentClass.getName(), Number.class)
                    .getSingleResult().intValue();
        }
    }
}
