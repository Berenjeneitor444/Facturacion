package com.berenjeneitor.facturacion.persistencia.entidades;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FormaPago")
public class FormaPago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String tipo;


    @OneToMany(mappedBy = "formaPago", cascade = CascadeType.ALL)
    private List<FacturasClientes> facturas;

    @Lob
    private String observaciones;

    public FormaPago() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<FacturasClientes> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturasClientes> facturas) {
        this.facturas = facturas;
    }
}
