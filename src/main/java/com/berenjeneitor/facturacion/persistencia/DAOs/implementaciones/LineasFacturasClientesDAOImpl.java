package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.LineasFacturasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasFacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LineasFacturasClientesDAOImpl extends GenericDAOImpl<LineasFacturasClientes, Integer> implements LineasFacturasClientesDAO {

    public LineasFacturasClientesDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<LineasFacturasClientes> findByFactura(FacturasClientes factura) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasFacturasClientes> query = session.createQuery(
                    "FROM LineasFacturasClientes WHERE factura = :factura", LineasFacturasClientes.class);
            query.setParameter("factura", factura);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasFacturasClientes> findByArticulo(Articulos articulo) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasFacturasClientes> query = session.createQuery(
                    "FROM LineasFacturasClientes WHERE articulo = :articulo", LineasFacturasClientes.class);
            query.setParameter("articulo", articulo);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasFacturasClientes> findByCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasFacturasClientes> query = session.createQuery(
                    "FROM LineasFacturasClientes WHERE codigo = :codigo", LineasFacturasClientes.class);
            query.setParameter("codigo", codigo);
            return query.getResultList();
        }
    }

    @Override
    public List<LineasFacturasClientes> findByIva(TiposIVA iva) {
        try (Session session = sessionFactory.openSession()) {
            Query<LineasFacturasClientes> query = session.createQuery(
                    "FROM LineasFacturasClientes WHERE iva = :iva", LineasFacturasClientes.class);
            query.setParameter("iva", iva);
            return query.getResultList();
        }
    }
}
