package com.berenjeneitor.facturacion.negocio;
import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ArticulosDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import java.math.BigDecimal;
import java.util.List;

public class ArticulosService extends GenericServiceImpl<Articulos, Integer> {
    private final ArticulosDAO articuloDAO;

    public ArticulosService(ArticulosDAO articuloDAO) {
        super(articuloDAO);
        this.articuloDAO = articuloDAO;
    }

    @Override
    public void validate(Articulos articulo) throws ValidationException {
        // Validate descripcion
        if (articulo.getDescripcion() == null || articulo.getDescripcion().trim().isEmpty()) {
            throw new ValidationException("La descripción del artículo es obligatoria");
        }

        // Validate familia (must exist)
        if (articulo.getFamilia() == null) {
            throw new ValidationException("El artículo debe pertenecer a una familia");
        }

        // Validate coste (must be positive)
        if (articulo.getCoste() == null || articulo.getCoste().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El coste debe ser un valor positivo");
        }

        // Validate PVP (must be positive)
        if (articulo.getPvp() == null || articulo.getPvp().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El PVP debe ser un valor positivo");
        }

        // Validate stock (must be non-negative)
        if (articulo.getStock() == null || articulo.getStock().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("El stock no puede ser negativo");
        }
    }

    @Override
    public Articulos processEntity(Articulos entity) {
        validate(entity);
        // calcular margen
        entity.setMargen(entity.getPvp().subtract(entity.getCoste()));
        // calcular codigo
        entity.setCodigo(calcularCodigo(entity));
        return entity;
    }

    public List<Articulos> findByFamilia(FamiliaArticulos familia) {
        return articuloDAO.findByFamilia(familia);
    }

    public List<Articulos> findByPrecioRange(BigDecimal minPrecio, BigDecimal maxPrecio) {
        return articuloDAO.findByPvpBetween(minPrecio, maxPrecio);
    }

    public Articulos findByCodigo(String codigo) {
        return articuloDAO.findByCodigo(codigo).orElseThrow(() -> new ValidationException("Artículo no encontrado"));
    }

    public Articulos findByCodigoBarras(String codigoBarras) {
        return articuloDAO.findByCodigoBarras(codigoBarras).orElseThrow(() -> new ValidationException("Artículo no encontrado"));
    }
    public List<Articulos> findByStockRange(BigDecimal minStock, BigDecimal maxStock) {
        return articuloDAO.findByStockBetween(minStock, maxStock);
    }

    public List<Articulos> findByProveedor(Proveedores proveedor) {
        return articuloDAO.findByProveedor(proveedor);
    }




    public void actualizarStock(Articulos articulo, BigDecimal cantidad) {
        if (articulo.getStock().add(cantidad).compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("No hay suficiente stock");
        }
        articulo.setStock(articulo.getStock().add(cantidad));
        saveOrUpdate(articulo);
    }
    private String calcularCodigo(Articulos articulo) {
        return articulo.getFamilia().getCodigo() + "-" + articuloDAO.count("familia = " + articulo.getFamilia().getId());
    }
}