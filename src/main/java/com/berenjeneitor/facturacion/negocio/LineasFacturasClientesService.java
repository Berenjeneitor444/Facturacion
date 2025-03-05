package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.LineasFacturasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasFacturasClientes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class LineasFacturasClientesService extends GenericServiceImpl<LineasFacturasClientes, Integer> {

    private final LineasFacturasClientesDAO lineasFacturasDAO;
    private final ArticulosService articuloService;

    public LineasFacturasClientesService(
            LineasFacturasClientesDAO lineasFacturasDAO,
            ArticulosService articuloService
    ) {
        super(lineasFacturasDAO);
        this.lineasFacturasDAO = lineasFacturasDAO;
        this.articuloService = articuloService;
    }

    @Override
    public void validate(LineasFacturasClientes linea) throws ValidationException {
        // Validate factura
        if (linea.getFactura() == null) {
            throw new ValidationException("La línea de factura debe pertenecer a una factura");
        }
        // Validate articulo
        if (linea.getArticulo() == null) {
            throw new ValidationException("La línea de factura debe tener un artículo asociado");
        }

        // Validate cantidad
        if (linea.getCantidad() == null || linea.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("La cantidad debe ser un valor positivo");
        }

        // Check stock availability
        articuloService.actualizarStock(linea.getArticulo(), linea.getCantidad().negate());
    }


    @Override
    public LineasFacturasClientes processEntity(LineasFacturasClientes entity) {
        validate(entity);
        calculateFinancial(entity);
        return entity;
    }

    public BigDecimal calculateBaseImponible(LineasFacturasClientes linea) {
        return linea.getCantidad().multiply(linea.getPrecioUnitario()).setScale(2, RoundingMode.HALF_UP);
    }

    private void calculateFinancial(LineasFacturasClientes linea) {
        linea.setPrecioUnitario(linea.getArticulo().getPvp());
        BigDecimal baseImponible = calculateBaseImponible(linea);
        linea.setBaseImponible(baseImponible);
        BigDecimal iva = calculateIva(linea);
        linea.setIva(iva);
        BigDecimal total = baseImponible.add(iva).setScale(2, RoundingMode.HALF_UP);
        linea.setTotal(total);
    }

    public BigDecimal calculateIva(LineasFacturasClientes linea) {
        BigDecimal baseImponible = calculateBaseImponible(linea);
        return baseImponible.multiply(linea.getIva().divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
    }

    // Additional business logic methods
    public List<LineasFacturasClientes> findByFactura(FacturasClientes factura) {
        return lineasFacturasDAO.findByFactura(factura);
    }
}