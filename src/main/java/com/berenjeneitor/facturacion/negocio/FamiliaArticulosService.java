package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FamiliaArticulosDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;

import java.util.Optional;

public class FamiliaArticulosService extends GenericServiceImpl<FamiliaArticulos, Integer> {
    private final FamiliaArticulosDAO familiaArticulosDAO;

    public FamiliaArticulosService(FamiliaArticulosDAO familiaArticulosDAO) {
        super(familiaArticulosDAO);
        this.familiaArticulosDAO = familiaArticulosDAO;
    }

    @Override
    public void validate(FamiliaArticulos familiaArticulos) throws ValidationException {
        // Validate codigo
        if (familiaArticulos.getCodigo() == null || familiaArticulos.getCodigo().trim().isEmpty()) {
            throw new ValidationException("El código de la familia de artículos es obligatorio");
        }

        // Validate denominacion
        if (familiaArticulos.getDenominacion() == null || familiaArticulos.getDenominacion().trim().isEmpty()) {
            throw new ValidationException("La denominación de la familia de artículos es obligatoria");
        }

        // Check for existing codigo
        Optional<FamiliaArticulos> existingByCodigo = familiaArticulosDAO.findByCodigo(familiaArticulos.getCodigo());
        if (existingByCodigo.isPresent() && !existingByCodigo.get().getId().equals(familiaArticulos.getId())) {
            throw new ValidationException("Ya existe una familia de artículos con este código");
        }

        // Check for existing denominacion
        Optional<FamiliaArticulos> existingByDenominacion = familiaArticulosDAO.findByDenominacion(familiaArticulos.getDenominacion());
        if (existingByDenominacion.isPresent() && !existingByDenominacion.get().getId().equals(familiaArticulos.getId())) {
            throw new ValidationException("Ya existe una familia de artículos con esta denominación");
        }
    }

    @Override
    public FamiliaArticulos processEntity(FamiliaArticulos entity) {
        validate(entity);
        return entity;
    }

    // Additional business logic methods
    public Optional<FamiliaArticulos> findByCodigo(String codigo) {
        return familiaArticulosDAO.findByCodigo(codigo);
    }

    public Optional<FamiliaArticulos> findByDenominacion(String denominacion) {
        return familiaArticulosDAO.findByDenominacion(denominacion);
    }
}