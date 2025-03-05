package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RectificativasClientesDAO extends GenericDAO<RectificativasClientes, Integer> {
    Optional<RectificativasClientes> findByNumero(Integer numero);
    List<RectificativasClientes> findByCliente(Clientes cliente);
    List<RectificativasClientes> findByFecha(Date fecha);
    List<RectificativasClientes> findByFechaBetween(Date fechaInicio, Date fechaFin);
    List<RectificativasClientes> findByFacturaCliente(FacturasClientes factura);
}