package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.LineasRectificativasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasRectificativa;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class LineasRectificativasClientesService extends GenericServiceImpl<LineasRectificativa, Integer> {

    private final LineasRectificativasClientesDAO lineasRectificativasDAO;
    private final ArticulosService articuloService;

    public LineasRectificativasClientesService(
            LineasRectificativasClientesDAO lineasRectificativasDAO,
            ArticulosService articuloService
    ) {
        super(lineasRectificativasDAO);
        this.lineasRectificativasDAO = lineasRectificativasDAO;
        this.articuloService = articuloService;
    }

    @Override
    public void validate(LineasRectificativa linea) throws ValidationException {
        // Validate rectificativa
        if (linea.getRectificativa() == null) {
            throw new ValidationException("La línea de rectificativa debe pertenecer a una rectificativa");
        }
        // Validate articulo
        if (linea.getArticulo() == null) {
            throw new ValidationException("La línea de rectificativa debe tener un artículo asociado");
        }

        // Validate cantidad
        if (linea.getCantidad() == null || linea.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("La cantidad debe ser un valor positivo");
        }

        // Check stock availability
        articuloService.actualizarStock(linea.getArticulo(), linea.getCantidad().negate());
    }

    @Override
    public LineasRectificativa processEntity(LineasRectificativa entity) {
        validate(entity);
        calculateFinancial(entity);
        return entity;
    }

    public BigDecimal calculateBaseImponible(LineasRectificativa linea) {
        return linea.getCantidad().multiply(linea.getPrecioUnitario()).setScale(2, RoundingMode.HALF_UP);
    }

    private void calculateFinancial(LineasRectificativa linea) {
        linea.setPrecioUnitario(linea.getArticulo().getPvp());
        BigDecimal baseImponible = calculateBaseImponible(linea);
        linea.setBaseImponible(baseImponible);
        BigDecimal iva = calculateIva(linea);
        linea.setIva(iva);
        BigDecimal total = baseImponible.add(iva).setScale(2, RoundingMode.HALF_UP);
        linea.setTotal(total);
    }

    public BigDecimal calculateIva(LineasRectificativa linea) {
        BigDecimal baseImponible = calculateBaseImponible(linea);
        return baseImponible.multiply(linea.getIva().divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
    }

    // Additional business logic methods
    public List<LineasRectificativa> findByRectificativa(RectificativasClientes rectificativa) {
        return lineasRectificativasDAO.findByRectificativa(rectificativa);
    }
}