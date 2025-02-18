package com.berenjeneitor.facturacion.modelo.DB.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Articulos")
public class Articulos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false, unique = true)
    private String codigo;

    @Column(length = 80)
    private String codigoBarras;

    @Column(length = 60)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "familia_id")
    private FamiliaArticulos familia;

    private BigDecimal coste;

    private BigDecimal margen;

    private BigDecimal pvp;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedores proveedor;

    private BigDecimal stock;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<LineasFacturasClientes> lineasFactura;

    @Lob
    private String observaciones;

    public Articulos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public FamiliaArticulos getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaArticulos familia) {
        this.familia = familia;
    }

    public BigDecimal getCoste() {
        return coste;
    }

    public void setCoste(BigDecimal coste) {
        this.coste = coste;
    }

    public BigDecimal getMargen() {
        return margen;
    }

    public void setMargen(BigDecimal margen) {
        this.margen = margen;
    }

    public BigDecimal getPvp() {
        return pvp;
    }

    public void setPvp(BigDecimal pvp) {
        this.pvp = pvp;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
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
