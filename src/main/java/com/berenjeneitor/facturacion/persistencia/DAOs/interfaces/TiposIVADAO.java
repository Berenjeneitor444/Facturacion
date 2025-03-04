package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;
import java.math.BigDecimal;
import java.util.Optional;

public interface TiposIVADAO extends GenericDAO<TiposIVA, Integer> {
    Optional<TiposIVA> findByIva(BigDecimal iva);
}