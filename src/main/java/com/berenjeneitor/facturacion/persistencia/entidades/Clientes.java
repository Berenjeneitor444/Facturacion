package com.berenjeneitor.facturacion.persistencia.entidades;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Clientes")
public class Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 12, unique = true)
    private String cif;

    @Column(length = 80, nullable = false, unique = true)
    private String nombre;

    @Column(length = 80)
    private String direccion;

    @Column(length = 10)
    private String cp;

    @Column(length = 80)
    private String poblacion;

    @Column(length = 60)
    private String provincia;

    @Column(length = 60)
    private String pais;

    @Column(length = 16)
    private String telefono;

    @Column(length = 80)
    private String email;

    @Column(length = 40)
    private String iban;

    private BigDecimal riesgo;

    private BigDecimal descuento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<FacturasClientes> facturas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<RectificativasClientes> rectificativas;

    @Lob
    private String observaciones;


    public Clientes() {


    }

    public String getCif() {
        return cif;
    }

    public int getId() {
        return id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(BigDecimal riesgo) {
        this.riesgo = riesgo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FacturasClientes> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturasClientes> facturas) {
        this.facturas = facturas;
    }

    public List<RectificativasClientes> getRectificativas() {
        return rectificativas;
    }

    public void setRectificativas(List<RectificativasClientes> rectificativas) {
        this.rectificativas = rectificativas;
    }
}
