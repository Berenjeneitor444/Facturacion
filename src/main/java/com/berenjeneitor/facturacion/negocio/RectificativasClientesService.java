package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.RectificativasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasRectificativa;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RectificativasClientesService extends GenericServiceImpl<RectificativasClientes, Integer> {
    private final RectificativasClientesDAO rectificativaDAO;
    private final LineasRectificativasClientesService lineasRectificativasClientesService;

    public RectificativasClientesService(RectificativasClientesDAO rectificativaDAO, LineasRectificativasClientesService lineasRectificativasClientesService) {
        super(rectificativaDAO);
        this.rectificativaDAO = rectificativaDAO;
        this.lineasRectificativasClientesService = lineasRectificativasClientesService;
    }

    @Override
    public void validate(RectificativasClientes rectificativa) throws ValidationException {
        if (rectificativa == null) {
            throw new ValidationException("La rectificativa no puede ser nula");
        }
        if (rectificativa.getFactura() == null) {
            throw new ValidationException("La rectificativa debe tener una factura asociada");
        }
        // Validar fecha (no puede ser en el futuro)
        if (rectificativa.getFecha() == null || rectificativa.getFecha().after(new Date())) {
            throw new ValidationException("Fecha de rectificativa inválida");
        }

        // Validar cliente
        if (rectificativa.getCliente() == null) {
            throw new ValidationException("La rectificativa debe tener un cliente asociado");
        }

        // Validar líneas
        if (rectificativa.getLineasRectificativa() == null || rectificativa.getLineasRectificativa().isEmpty()) {
            throw new ValidationException("La rectificativa debe tener al menos una línea");
        }

        // Validar cada línea
        for (LineasRectificativa linea : rectificativa.getLineasRectificativa()) {
            lineasRectificativasClientesService.validate(linea);
        }
    }

    @Override
    public RectificativasClientes processEntity(RectificativasClientes entity) {
        validate(entity);
        for (LineasRectificativa linea : entity.getLineasRectificativa()) {
            lineasRectificativasClientesService.processEntity(linea);
        }
        entity.setNumero(calcularNumero(entity));
        entity.setBaseImponible(calculateBaseImponible(entity));
        entity.setIva(calculateIVA(entity));
        entity.setTotal(entity.getBaseImponible().add(entity.getIva()));
        return entity;
    }

    private BigDecimal calculateBaseImponible(RectificativasClientes rectificativa) {
        return rectificativa.getLineasRectificativa().stream()
                .map(lineasRectificativasClientesService::calculateBaseImponible)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateIVA(RectificativasClientes rectificativa) {
        return rectificativa.getLineasRectificativa().stream()
                .map(lineasRectificativasClientesService::calculateIva)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Métodos de lógica de negocio
    public List<RectificativasClientes> findByCliente(Clientes cliente) {
        return rectificativaDAO.findByCliente(cliente);
    }

    public List<RectificativasClientes> findByFechaRange(Date inicio, Date fin) {
        return rectificativaDAO.findByFechaBetween(inicio, fin);
    }


    private int calcularNumero(RectificativasClientes rectificativa) {
        return rectificativaDAO.count(null);
    }
}