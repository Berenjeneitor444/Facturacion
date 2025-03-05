package com.berenjeneitor.facturacion.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FacturasClientes")
public class FacturasClientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal baseImponible;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(length = 128)
    private String hash;

    @Column(length = 4296)
    private String qr;

    @Column(nullable = false)
    private Boolean cobrada;

    @ManyToOne
    @JoinColumn(name = "forma_pago_id")
    private FormaPago formaPago;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<LineasFacturasClientes> lineasFactura;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RectificativasClientes> rectificativas;

    @Column(name = "fecha_cobro")
    private Date fechaCobro;

    @Lob
    private String observaciones;

    public FacturasClientes() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Boolean getCobrada() {
        return cobrada;
    }

    public void setCobrada(Boolean cobrada) {
        this.cobrada = cobrada;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
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

    public List<RectificativasClientes> getRectificativas() {
        return rectificativas;
    }

    public void setRectificativas(List<RectificativasClientes> rectificativas) {
        this.rectificativas = rectificativas;
    }
}
