package com.berenjeneitor.facturacion.persistencia.DAOs.interfaces;

import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.generic.GenericDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasRectificativa;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import java.util.List;
import java.util.Optional;

public interface LineasRectificativasClientesDAO extends GenericDAO<LineasRectificativa, Integer> {

    List<LineasRectificativa> findByRectificativa(RectificativasClientes rectificativa);

    List<LineasRectificativa> findByArticulo(Articulos articulo);

    List<LineasRectificativa> findByIva(TiposIVA iva);

    List<LineasRectificativa> findByCodigo(String codigo);
}
