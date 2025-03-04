package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FormaPagoDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class FormaPagoDAOImpl extends GenericDAOImpl<FormaPago, Integer> implements FormaPagoDAO {

    public FormaPagoDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<FormaPago> findByTipo(String tipo) {
        try (Session session = sessionFactory.openSession()) {
            Query<FormaPago> query = session.createQuery(
                    "FROM FormaPago WHERE tipo = :tipo", FormaPago.class);
            query.setParameter("tipo", tipo);
            return query.uniqueResultOptional();
        }
    }
}