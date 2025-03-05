package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.TiposIVADAO;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import java.math.BigDecimal;
import java.util.Optional;

public class TiposIVAService extends GenericServiceImpl<TiposIVA, Integer> {
    private final TiposIVADAO tiposIVADAO;

    public TiposIVAService(TiposIVADAO tiposIVADAO) {
        super(tiposIVADAO);
        this.tiposIVADAO = tiposIVADAO;
    }

    @Override
    public void validate(TiposIVA tiposIVA) throws ValidationException {
        // Validate IVA percentage
        if (tiposIVA.getIva() == null) {
            throw new ValidationException("El porcentaje de IVA es obligatorio");
        }

        // Validate IVA range (reasonable percentage)
        BigDecimal iva = tiposIVA.getIva();
        if (iva.compareTo(BigDecimal.ZERO) < 0 || iva.compareTo(new BigDecimal("100")) > 0) {
            throw new ValidationException("Porcentaje de IVA debe estar entre 0 y 100");
        }

        // Check for existing IVA percentage to prevent duplicates
        Optional<TiposIVA> existingByIva = tiposIVADAO.findByIva(iva);
        if (existingByIva.isPresent() && !existingByIva.get().getId().equals(tiposIVA.getId())) {
            throw new ValidationException("Ya existe un tipo de IVA con este porcentaje");
        }
    }

    @Override
    public TiposIVA processEntity(TiposIVA entity) {
        validate(entity);
        return entity;
    }

    // Additional business logic method
    public TiposIVA findByIva(BigDecimal iva) {
        return tiposIVADAO.findByIva(iva)
                .orElseThrow(() -> new ValidationException("Tipo de IVA no encontrado"));
    }
}