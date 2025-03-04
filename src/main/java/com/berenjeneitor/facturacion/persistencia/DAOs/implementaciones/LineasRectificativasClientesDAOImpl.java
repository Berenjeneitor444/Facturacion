package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.LineasRectificativasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasRectificativa;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LineasRectificativasClientesDAOImpl extends GenericDAOImpl<LineasRectificativa, Integer> implements LineasRectificativasClientesDAO {

    public LineasRectificativasClientesDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<LineasRectificativa> findByRectificativa(RectificativasClientes rectificativa) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasRectificativa> query = session.createQuery(
                    "FROM LineasRectificativa WHERE rectificativa = :rectificativa", LineasRectificativa.class);
            query.setParameter("rectificativa", rectificativa);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasRectificativa> findByArticulo(Articulos articulo) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasRectificativa> query = session.createQuery(
                    "FROM LineasRectificativa WHERE articulo = :articulo", LineasRectificativa.class);
            query.setParameter("articulo", articulo);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasRectificativa> findByCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasRectificativa> query = session.createQuery(
                    "FROM LineasRectificativa WHERE codigo = :codigo", LineasRectificativa.class);
            query.setParameter("codigo", codigo);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasRectificativa> findByIva(TiposIVA iva) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasRectificativa> query = session.createQuery(
                    "FROM LineasRectificativa WHERE iva = :iva", LineasRectificativa.class);
            query.setParameter("iva", iva);
            return query.getResultList();
        }
    }
}
