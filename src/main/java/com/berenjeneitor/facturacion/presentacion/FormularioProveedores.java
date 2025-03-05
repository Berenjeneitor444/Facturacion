package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ProveedoresService;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;
import com.berenjeneitor.facturacion.negocio.ValidationException;

import javax.swing.*;
import java.awt.*;

public class FormularioProveedores extends FormularioBase {
    private JTextField txtCIF, txtNombre, txtDireccion, txtCP, txtPoblacion, 
                      txtProvincia, txtPais, txtTelefono, txtEmail, txtIBAN;
    private JTextArea txtObservaciones;
    private final ProveedoresService proveedoresService;

    public FormularioProveedores(ProveedoresService proveedoresService) {
        super("Formulario de Proveedor");
        this.proveedoresService = proveedoresService;
    }

    @Override
    protected void construirFormulario() {
        // Campos obligatorios
        panel.add(new JLabel("CIF:*"));
        txtCIF = createTextField(12);
        panel.add(txtCIF);

        panel.add(new JLabel("Nombre:*"));
        txtNombre = createTextField(80);
        panel.add(txtNombre);

        // Campos opcionales
        panel.add(new JLabel("Dirección:"));
        txtDireccion = createTextField(80);
        panel.add(txtDireccion);

        panel.add(new JLabel("Código Postal:"));
        txtCP = createTextField(10);
        panel.add(txtCP);

        panel.add(new JLabel("Población:"));
        txtPoblacion = createTextField(80);
        panel.add(txtPoblacion);

        panel.add(new JLabel("Provincia:"));
        txtProvincia = createTextField(60);
        panel.add(txtProvincia);

        panel.add(new JLabel("País:"));
        txtPais = createTextField(60);
        panel.add(txtPais);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = createTextField(16);
        panel.add(txtTelefono);

        panel.add(new JLabel("Email:"));
        txtEmail = createTextField(80);
        panel.add(txtEmail);

        panel.add(new JLabel("IBAN:"));
        txtIBAN = createTextField(40);
        panel.add(txtIBAN);

        panel.add(new JLabel("Observaciones:"));
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservaciones);
        panel.add(scrollPane);
    }

    @Override
    protected void guardar() {
        try {
            Proveedores proveedor = new Proveedores();
            proveedor.setCif(txtCIF.getText());
            proveedor.setNombre(txtNombre.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setCp(txtCP.getText());
            proveedor.setPoblacion(txtPoblacion.getText());
            proveedor.setProvincia(txtProvincia.getText());
            proveedor.setPais(txtPais.getText());
            proveedor.setTelefono(txtTelefono.getText());
            proveedor.setEmail(txtEmail.getText());
            proveedor.setIban(txtIBAN.getText());
            proveedor.setObservaciones(txtObservaciones.getText());

            proveedoresService.save(proveedor);
            JOptionPane.showMessageDialog(this, 
                "Proveedor guardado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (ValidationException e) {
            mostrarError("Error al guardar: " + e.getMessage(), null);
        }
    }
    
    @Override
    protected void limpiarCampos() {
        txtCIF.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCP.setText("");
        txtPoblacion.setText("");
        txtProvincia.setText("");
        txtPais.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtIBAN.setText("");
        txtObservaciones.setText("");
        txtCIF.requestFocus();
    }
}