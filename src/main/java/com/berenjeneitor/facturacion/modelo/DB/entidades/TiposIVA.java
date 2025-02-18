package com.berenjeneitor.facturacion.modelo.DB.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "TiposIVA")
public class TiposIVA implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal iva;

    @OneToMany(mappedBy = "iva", cascade = CascadeType.ALL)
    private List<LineasFacturasClientes> lineasFactura;

    @Lob
    private String observaciones;

    public TiposIVA() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<LineasFacturasClientes> getLineasFactura() {
        return lineasFactura;
    }

    public void setLineasFactura(List<LineasFacturasClientes> lineasFactura) {
        this.lineasFactura = lineasFactura;
    }
}

