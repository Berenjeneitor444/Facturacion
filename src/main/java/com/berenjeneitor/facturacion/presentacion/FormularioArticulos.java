package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;

public class FormularioArticulos extends FormularioBase {
    private JTextField txtCodigo, txtDescripcion, txtCoste, txtMargen, txtPVP, txtStock, txtObservaciones;
    private JComboBox<String> cmbFamilia, cmbIVA, cmbProveedor;

    public FormularioArticulos() {
        super("Formulario de Artículo");
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panel.add(txtDescripcion);

        panel.add(new JLabel("Familia:"));
        cmbFamilia = new JComboBox<>();
        panel.add(cmbFamilia);

        panel.add(new JLabel("IVA:"));
        cmbIVA = new JComboBox<>();
        panel.add(cmbIVA);

        panel.add(new JLabel("Coste:"));
        txtCoste = new JTextField();
        panel.add(txtCoste);

        panel.add(new JLabel("Margen:"));
        txtMargen = new JTextField();
        panel.add(txtMargen);

        panel.add(new JLabel("PVP:"));
        txtPVP = new JTextField();
        panel.add(txtPVP);

        panel.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panel.add(txtStock);

        panel.add(new JLabel("Proveedor:"));
        cmbProveedor = new JComboBox<>();
        panel.add(cmbProveedor);

        panel.add(new JLabel("Observaciones:"));
        txtObservaciones = new JTextField();
        panel.add(txtObservaciones);
    }

    @Override
    protected void guardar() {
        String codigo = txtCodigo.getText();
        String descripcion = txtDescripcion.getText();
        String familia = cmbFamilia.getSelectedItem().toString();
        String iva = cmbIVA.getSelectedItem().toString();
        String coste = txtCoste.getText();
        String margen = txtMargen.getText();
        String pvp = txtPVP.getText();
        String stock = txtStock.getText();
        String proveedor = cmbProveedor.getSelectedItem().toString();
        String observaciones = txtObservaciones.getText();

        System.out.println("Guardando artículo: " + descripcion);
    }
}