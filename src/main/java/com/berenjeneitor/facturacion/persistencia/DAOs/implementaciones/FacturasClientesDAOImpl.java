package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FacturasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FacturasClientesDAOImpl extends GenericDAOImpl<FacturasClientes, Integer> implements FacturasClientesDAO {

    public FacturasClientesDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<FacturasClientes> findByNumero(Integer numero) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE numero = :numero", FacturasClientes.class);
            query.setParameter("numero", numero);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<FacturasClientes> findByCliente(Clientes cliente) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE cliente = :cliente", FacturasClientes.class);
            query.setParameter("cliente", cliente);
            return query.getResultList();
        }
    }
    @Override
    public List<FacturasClientes> findByFecha(Date fecha) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE fecha = :fecha", FacturasClientes.class);
            query.setParameter("fecha", fecha);
            return query.getResultList();
        }
    }

    @Override
    public List<FacturasClientes> findByFechaBetween(Date fechaInicio, Date fechaFin) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE fecha BETWEEN :fechaInicio AND :fechaFin",
                    FacturasClientes.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            return query.getResultList();
        }
    }

    @Override
    public List<FacturasClientes> findByCobrada(Boolean cobrada) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE cobrada = :cobrada", FacturasClientes.class);
            query.setParameter("cobrada", cobrada);
            return query.getResultList();
        }
    }

    @Override
    public List<FacturasClientes> findByFormaPago(FormaPago formaPago) {
        try (Session session = sessionFactory.openSession()) {
            Query<FacturasClientes> query = session.createQuery(
                    "FROM FacturasClientes WHERE formaPago = :formaPago", FacturasClientes.class);
            query.setParameter("formaPago", formaPago);
            return query.getResultList();
        }
    }
}