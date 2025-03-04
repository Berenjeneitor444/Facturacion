package com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones;

import com.berenjeneitor.facturacion.persistencia.DAOs.implementaciones.generic.GenericDAOImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ArticulosDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ArticulosDAOImpl extends GenericDAOImpl<Articulos, Integer> implements ArticulosDAO {

    public ArticulosDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Articulos> findByCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE codigo = :codigo", Articulos.class);
            query.setParameter("codigo", codigo);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<Articulos> findByCodigoBarras(String codigoBarras) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE codigoBarras = :codigoBarras", Articulos.class);
            query.setParameter("codigoBarras", codigoBarras);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public List<Articulos> findByFamilia(FamiliaArticulos familia) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE familia = :familia", Articulos.class);
            query.setParameter("familia", familia);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByProveedor(Proveedores proveedor) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE proveedor = :proveedor", Articulos.class);
            query.setParameter("proveedor", proveedor);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByPvpLessThan(BigDecimal precio) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE pvp < :precio", Articulos.class);
            query.setParameter("precio", precio);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByPvpGreaterThan(BigDecimal precio) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE pvp > :precio", Articulos.class);
            query.setParameter("precio", precio);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByStockLessThan(BigDecimal cantidad) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE stock < :cantidad", Articulos.class);
            query.setParameter("cantidad", cantidad);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByStockGreaterThan(BigDecimal cantidad) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE stock > :cantidad", Articulos.class);
            query.setParameter("cantidad", cantidad);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByStockBetween(BigDecimal cantidad1, BigDecimal cantidad2) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE stock BETWEEN :cantidad1 AND :cantidad2", Articulos.class);
            query.setParameter("cantidad1", cantidad1);
            query.setParameter("cantidad2", cantidad2);
            return query.getResultList();
        }
    }

    @Override
    public List<Articulos> findByPvpBetween(BigDecimal precio1, BigDecimal precio2) {
        try (Session session = sessionFactory.openSession()) {
            Query<Articulos> query = session.createQuery(
                    "FROM Articulos WHERE pvp BETWEEN :precio1 AND :precio2", Articulos.class);
            query.setParameter("precio1", precio1);
            query.setParameter("precio2", precio2);
            return query.getResultList();
        }
    }

}