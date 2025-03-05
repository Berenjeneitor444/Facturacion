package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.FormaPagoDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;

public class FormaPagoService extends GenericServiceImpl<FormaPago, Integer> {
    private final FormaPagoDAO formaPagoDAO;

    public FormaPagoService(FormaPagoDAO formaPagoDAO) {
        super(formaPagoDAO);
        this.formaPagoDAO = formaPagoDAO;
    }

    @Override
    public void validate(FormaPago formaPago) throws ValidationException {
        // Validate tipo
        if (formaPago.getTipo() == null || formaPago.getTipo().trim().isEmpty()) {
            throw new ValidationException("El tipo de forma de pago es obligatorio");
        }

        // Check for existing tipo to prevent duplicates
        formaPagoDAO.findByTipo(formaPago.getTipo())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(formaPago.getId())) {
                        throw new ValidationException("Ya existe una forma de pago con este tipo");
                    }
                });
    }

    @Override
    public FormaPago processEntity(FormaPago entity) {
        validate(entity);
        return entity;
    }

    // Additional business logic method
    public FormaPago findByTipo(String tipo) {
        return formaPagoDAO.findByTipo(tipo)
                .orElseThrow(() -> new ValidationException("Forma de pago no encontrada"));
    }
}