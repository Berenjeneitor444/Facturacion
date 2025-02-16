package com.berenjeneitor.facturacion.modelo.DB.entidades;
import javax.persistence.*;

@Entity
@Table(name = "Clientes")
public class Clientes {
    @Id
    @Column
    private int id;

    @Column
    private String cif;

    @Column
    private String nombre;

    @Column
    private String direccion;

    @Column
    private String cp;

    @Column
    private String poblacion;

    @Column
    private String provincia;

    @Column
    private String pais;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private String iban;

    @Column
    private double riesgo;

    @Column
    private double descuento;

    @Column
    private String observaciones;
}
