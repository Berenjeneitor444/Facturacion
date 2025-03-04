package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import java.util.Optional;

public interface FamiliaArticulosDAO extends GenericDAO<FamiliaArticulos, Integer> {
    Optional<FamiliaArticulos> findByCodigo(String codigo);
    Optional<FamiliaArticulos> findByDenominacion(String denominacion);
}