package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasFacturasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;
import java.util.List;

public interface LineasFacturasClientesDAO extends GenericDAO<LineasFacturasClientes, Integer> {
    List<LineasFacturasClientes> findByFactura(FacturasClientes factura);
    List<LineasFacturasClientes> findByArticulo(Articulos articulo);
    List<LineasFacturasClientes> findByCodigo(String codigo);
    List<LineasFacturasClientes> findByIva(TiposIVA iva);
}
