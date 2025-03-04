package com.berenjeneitor.facturacion.persistencia.entidades;

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

    @Column(length = 80)
    private String descripcion;

    @Column(length = 50, nullable = false)
    private String codigo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(name = "base_imponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseImponible;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    public LineasFacturasClientes() {}

    // Getters and Setters
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
