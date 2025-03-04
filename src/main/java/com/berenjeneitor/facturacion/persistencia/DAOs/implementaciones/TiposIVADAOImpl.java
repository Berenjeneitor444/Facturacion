package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.TiposIVADAO;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Optional;

public class TiposIVADAOImpl extends GenericDAOImpl<TiposIVA, Integer> implements TiposIVADAO {

    public TiposIVADAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<TiposIVA> findByIva(BigDecimal iva) {
        try (Session session = sessionFactory.openSession()) {
            Query<TiposIVA> query = session.createQuery(
                    "FROM TiposIVA WHERE iva = :iva", TiposIVA.class);
            query.setParameter("iva", iva);
            return query.uniqueResultOptional();
        }
    }
}