package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ClientesDAOImpl extends GenericDAOImpl<Clientes, Integer> implements ClientesDAO {

    public ClientesDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Clientes> findByCif(String cif) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clientes> query = session.createQuery(
                    "FROM Clientes WHERE cif = :cif", Clientes.class);
            query.setParameter("cif", cif);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<Clientes> findByNombre(String nombre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clientes> query = session.createQuery(
                    "FROM Clientes WHERE nombre = :nombre", Clientes.class);
            query.setParameter("nombre", nombre);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<Clientes> findByProvincia(String provincia) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clientes> query = session.createQuery(
                    "FROM Clientes WHERE provincia = :provincia", Clientes.class);
            query.setParameter("provincia", provincia);
            return query.getResultList();
        }
    }

    @Override
    public List<Clientes> findByPoblacion(String poblacion) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clientes> query = session.createQuery(
                    "FROM Clientes WHERE poblacion = :poblacion", Clientes.class);
            query.setParameter("poblacion", poblacion);
            return query.getResultList();
        }
    }

    @Override
    public List<Clientes> findByPais(String pais) {
        try (Session session = sessionFactory.openSession()) {
            Query<Clientes> query = session.createQuery(
                    "FROM Clientes WHERE pais = :pais", Clientes.class);
            query.setParameter("pais", pais);
            return query.getResultList();
        }
    }
}