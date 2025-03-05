package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioProveedores extends FormularioBase {
    private JTextField txtCIF, txtNombre, txtDireccion, txtCP, txtPoblacion, txtProvincia, txtPais, txtTelefono, txtEmail, txtIBAN, txtObservaciones;

    public FormularioProveedores() {
        super("Formulario de Proveedor");
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("CIF:"));
        txtCIF = new JTextField();
        panel.add(txtCIF);

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);

        panel.add(new JLabel("Código Postal:"));
        txtCP = new JTextField();
        panel.add(txtCP);

        panel.add(new JLabel("Población:"));
        txtPoblacion = new JTextField();
        panel.add(txtPoblacion);

        panel.add(new JLabel("Provincia:"));
        txtProvincia = new JTextField();
        panel.add(txtProvincia);

        panel.add(new JLabel("País:"));
        txtPais = new JTextField();
        panel.add(txtPais);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("IBAN:"));
        txtIBAN = new JTextField();
        panel.add(txtIBAN);

        panel.add(new JLabel("Observaciones:"));
        txtObservaciones = new JTextField();
        panel.add(txtObservaciones);
    }

    @Override
    protected void guardar() {
        String cif = txtCIF.getText();
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();
        String cp = txtCP.getText();
        String poblacion = txtPoblacion.getText();
        String provincia = txtProvincia.getText();
        String pais = txtPais.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String iban = txtIBAN.getText();
        String observaciones = txtObservaciones.getText();

        System.out.println("Guardando proveedor: " + nombre);
    }
}
