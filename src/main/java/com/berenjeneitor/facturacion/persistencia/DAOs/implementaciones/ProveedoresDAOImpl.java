package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ProveedoresDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProveedoresDAOImpl extends GenericDAOImpl<Proveedores, Integer> implements ProveedoresDAO {

    public ProveedoresDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Proveedores> findByCif(String cif) {
        try (Session session = sessionFactory.openSession()) {
            Query<Proveedores> query = session.createQuery(
                    "FROM Proveedores WHERE cif = :cif", Proveedores.class);
            query.setParameter("cif", cif);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<Proveedores> findByNombre(String nombre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Proveedores> query = session.createQuery(
                    "FROM Proveedores WHERE nombre = :nombre", Proveedores.class);
            query.setParameter("nombre", nombre);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<Proveedores> findByProvincia(String provincia) {
        try (Session session = sessionFactory.openSession()) {
            Query<Proveedores> query = session.createQuery(
                    "FROM Proveedores WHERE provincia = :provincia", Proveedores.class);
            query.setParameter("provincia", provincia);
            return query.getResultList();
        }
    }

    @Override
    public List<Proveedores> findByPoblacion(String poblacion) {
        try (Session session = sessionFactory.openSession()) {
            Query<Proveedores> query = session.createQuery(
                    "FROM Proveedores WHERE poblacion = :poblacion", Proveedores.class);
            query.setParameter("poblacion", poblacion);
            return query.getResultList();
        }
    }

    @Override
    public List<Proveedores> findByPais(String pais) {
        try (Session session = sessionFactory.openSession()) {
            Query<Proveedores> query = session.createQuery(
                    "FROM Proveedores WHERE pais = :pais", Proveedores.class);
            query.setParameter("pais", pais);
            return query.getResultList();
        }
    }
}