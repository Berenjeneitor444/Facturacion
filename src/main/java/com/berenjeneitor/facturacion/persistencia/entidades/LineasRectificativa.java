package com.berenjeneitor.facturacion.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "LineasRectificativa")
public class LineasRectificativa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rectificativa_id", nullable = false)
    private RectificativasClientes rectificativa;

    @ManyToOne
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulos articulo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "base_imponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseImponible;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    public LineasRectificativa() {}

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RectificativasClientes getRectificativa() {
        return rectificativa;
    }

    public void setRectificativa(RectificativasClientes rectificativa) {
        this.rectificativa = rectificativa;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
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

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}