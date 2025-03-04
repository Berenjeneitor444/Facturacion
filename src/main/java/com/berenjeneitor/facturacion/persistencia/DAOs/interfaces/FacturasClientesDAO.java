package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FacturasClientesDAO extends GenericDAO<FacturasClientes, Integer> {
    Optional<FacturasClientes> findByNumero(Integer numero);
    List<FacturasClientes> findByCliente(Clientes cliente);
    List<FacturasClientes> findByFecha(Date fecha);
    List<FacturasClientes> findByFechaBetween(Date fechaInicio, Date fechaFin);
    List<FacturasClientes> findByCobrada(Boolean cobrada);
    List<FacturasClientes> findByFormaPago(FormaPago formaPago);
}