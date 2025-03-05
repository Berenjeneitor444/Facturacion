package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.RectificativasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RectificativasClientesDAOImpl extends GenericDAOImpl<RectificativasClientes, Integer> implements RectificativasClientesDAO {

    public RectificativasClientesDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<RectificativasClientes> findByNumero(Integer numero) {
        try (Session session = sessionFactory.openSession()) {
            Query<RectificativasClientes> query = session.createQuery(
                    "FROM RectificativasClientes WHERE numero = :numero", RectificativasClientes.class);
            query.setParameter("numero", numero);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<RectificativasClientes> findByCliente(Clientes cliente) {
        try (Session session = sessionFactory.openSession()) {
            Query<RectificativasClientes> query = session.createQuery(
                    "FROM RectificativasClientes WHERE cliente = :cliente", RectificativasClientes.class);
            query.setParameter("cliente", cliente);
            return query.getResultList();
        }
    }

    @Override
    public List<RectificativasClientes> findByFecha(Date fecha) {
        try (Session session = sessionFactory.openSession()) {
            Query<RectificativasClientes> query = session.createQuery(
                    "FROM RectificativasClientes WHERE fecha = :fecha", RectificativasClientes.class);
            query.setParameter("fecha", fecha);
            return query.getResultList();
        }
    }

    @Override
    public List<RectificativasClientes> findByFechaBetween(Date fechaInicio, Date fechaFin) {
        try (Session session = sessionFactory.openSession()) {
            Query<RectificativasClientes> query = session.createQuery(
                    "FROM RectificativasClientes WHERE fecha BETWEEN :fechaInicio AND :fechaFin", RectificativasClientes.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            return query.getResultList();
        }
    }

    @Override
    public List<RectificativasClientes> findByFacturaCliente(FacturasClientes factura) {
        try (Session session = sessionFactory.openSession()) {
            Query<RectificativasClientes> query = session.createQuery(
                    "FROM RectificativasClientes WHERE facturaCliente = :factura", RectificativasClientes.class);
            query.setParameter("factura", factura);
            return query.getResultList();
        }
    }
}
