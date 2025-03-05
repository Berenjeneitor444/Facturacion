package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FacturasClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasFacturasClientes;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacturasClientesService extends GenericServiceImpl<FacturasClientes, Integer> {
    private final FacturasClientesDAO facturaDAO;
    private final LineasFacturasClientesService lineasFacturasClientesService;

    public FacturasClientesService(FacturasClientesDAO facturaDAO, LineasFacturasClientesService lineasFacturasClientesService) {
        super(facturaDAO);
        this.facturaDAO = facturaDAO;
        this.lineasFacturasClientesService = lineasFacturasClientesService;
    }

    @Override
    public void validate(FacturasClientes factura) throws ValidationException {
        // Validar fecha (no puede ser en el futuro)
        if (factura.getFecha() == null || factura.getFecha().after(new Date())) {
            throw new ValidationException("Fecha de factura inválida");
        }

        // Validar cliente
        if (factura.getCliente() == null) {
            throw new ValidationException("La factura debe tener un cliente asociado");
        }

        // Validar líneas
        if (factura.getLineasFactura() == null || factura.getLineasFactura().isEmpty()) {
            throw new ValidationException("La factura debe tener al menos una línea");
        }

        // Validar cada línea
        for (LineasFacturasClientes linea : factura.getLineasFactura()) {
            lineasFacturasClientesService.validate(linea);
        }
    }

    @Override
    public FacturasClientes processEntity(FacturasClientes entity) {
        validate(entity);
        for (LineasFacturasClientes linea : entity.getLineasFactura()) {
            lineasFacturasClientesService.processEntity(linea);
        }
        entity.setNumero(calcularNumero(entity));
        entity.setBaseImponible(calculateBaseImponible(entity));
        entity.setIva(calculateIVA(entity));
        entity.setTotal(entity.getBaseImponible().add(entity.getIva()));
        return entity;
    }

    private BigDecimal calculateBaseImponible(FacturasClientes factura) {
        return factura.getLineasFactura().stream()
                .map(lineasFacturasClientesService::calculateBaseImponible)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateIVA(FacturasClientes factura) {
        return factura.getLineasFactura().stream()
                .map(lineasFacturasClientesService::calculateIva)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Métodos de lógica de negocio
    public List<FacturasClientes> findByCliente(Clientes cliente) {
        return facturaDAO.findByCliente(cliente);
    }

    public List<FacturasClientes> findByFechaRange(Date inicio, Date fin) {
        return facturaDAO.findByFechaBetween(inicio, fin);
    }

    public void marcarCobrada(FacturasClientes factura, Date fechaCobro) {
        if (Boolean.TRUE.equals(factura.getCobrada())) {
            throw new ValidationException("La factura ya está marcada como cobrada");
        }
        factura.setCobrada(true);
        factura.setFechaCobro(fechaCobro);
        saveOrUpdate(factura);
    }

    private int calcularNumero(FacturasClientes factura) {
        return facturaDAO.count(null);
    }
}