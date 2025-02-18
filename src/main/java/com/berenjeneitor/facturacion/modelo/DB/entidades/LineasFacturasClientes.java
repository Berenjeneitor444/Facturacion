package com.berenjeneitor.facturacion.modelo.DB.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Table(name = "LineasFacturasClientes")
public class LineasFacturasClientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private FacturasClientes factura;

    @ManyToOne
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulos articulo;

    @Column(length = 80, nullable = false)
    private String descripcion;

    @Column(length = 40, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private BigDecimal pvp;

    @ManyToOne
    @JoinColumn(name = "iva_id")
    private TiposIVA iva;

    public LineasFacturasClientes(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FacturasClientes getFactura() {
        return factura;
    }

    public void setFactura(FacturasClientes factura) {
        this.factura = factura;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getPvp() {
        return pvp;
    }

    public void setPvp(BigDecimal pvp) {
        this.pvp = pvp;
    }

    public TiposIVA getIva() {
        return iva;
    }

    public void setIva(TiposIVA iva) {
        this.iva = iva;
    }
}

