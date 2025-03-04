package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;
import java.util.List;
import java.util.Optional;

public interface ProveedoresDAO extends GenericDAO<Proveedores, Integer> {
    Optional<Proveedores> findByCif(String cif);
    Optional<Proveedores> findByNombre(String nombre);
    List<Proveedores> findByProvincia(String provincia);
    List<Proveedores> findByPais(String pais);
    List<Proveedores> findByPoblacion(String poblacion);
}