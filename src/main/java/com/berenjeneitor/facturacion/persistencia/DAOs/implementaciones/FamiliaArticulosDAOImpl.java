package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FamiliaArticulosDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class FamiliaArticulosDAOImpl extends GenericDAOImpl<FamiliaArticulos, Integer> implements FamiliaArticulosDAO {

    public FamiliaArticulosDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<FamiliaArticulos> findByCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            Query<FamiliaArticulos> query = session.createQuery(
                    "FROM FamiliaArticulos WHERE codigo = :codigo", FamiliaArticulos.class);
            query.setParameter("codigo", codigo);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<FamiliaArticulos> findByDenominacion(String denominacion) {
        try (Session session = sessionFactory.openSession()) {
            Query<FamiliaArticulos> query = session.createQuery(
                    "FROM FamiliaArticulos WHERE denominacion = :denominacion", FamiliaArticulos.class);
            query.setParameter("denominacion", denominacion);
            return query.uniqueResultOptional();
        }
    }
}