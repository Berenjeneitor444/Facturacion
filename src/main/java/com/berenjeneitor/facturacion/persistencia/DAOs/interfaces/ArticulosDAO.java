package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArticulosDAO extends GenericDAO<Articulos, Integer> {
    Optional<Articulos> findByCodigo(String codigo);
    Optional<Articulos> findByCodigoBarras(String codigoBarras);
    List<Articulos> findByFamilia(FamiliaArticulos familia);
    List<Articulos> findByProveedor(Proveedores proveedor);
    List<Articulos> findByPvpLessThan(BigDecimal precio);
    List<Articulos> findByPvpGreaterThan(BigDecimal precio);
    List<Articulos> findByStockLessThan(BigDecimal cantidad);
    List<Articulos> findByStockGreaterThan(BigDecimal cantidad);
    List<Articulos> findByStockBetween(BigDecimal cantidad1, BigDecimal cantidad2);
    List<Articulos> findByPvpBetween(BigDecimal precio1, BigDecimal precio2);
}
