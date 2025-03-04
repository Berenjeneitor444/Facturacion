package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;
import java.util.Optional;

public interface FormaPagoDAO extends GenericDAO<FormaPago, Integer> {
    Optional<FormaPago> findByTipo(String tipo);
}