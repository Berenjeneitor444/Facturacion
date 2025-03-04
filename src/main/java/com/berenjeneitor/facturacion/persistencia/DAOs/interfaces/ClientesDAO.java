package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;

import java.util.List;
import java.util.Optional;

public interface ClientesDAO extends GenericDAO<Clientes, Integer> {
    Optional<Clientes> findByCif(String cif);
    Optional<Clientes> findByNombre(String nombre);
    List<Clientes> findByProvincia(String provincia);
    List<Clientes> findByPoblacion(String poblacion);
    List<Clientes> findByPais(String pais);
}