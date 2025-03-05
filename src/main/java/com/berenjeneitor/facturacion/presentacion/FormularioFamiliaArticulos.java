package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;

public class FormularioFamiliaArticulos extends FormularioBase {
    private JTextField txtCodigo, txtDenominacion;
    private JTextArea txtDescripcion;

    public FormularioFamiliaArticulos() {
        super("Formulario de Familia de Artículos");
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("Código:*"));
        txtCodigo = createTextField(20);
        panel.add(txtCodigo);

        panel.add(new JLabel("Denominación:*"));
        txtDenominacion = createTextField(50);
        panel.add(txtDenominacion);

        panel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtDescripcion);
        panel.add(scrollPane);
    }

    @Override
    protected void guardar() {
        try {
            String codigo = txtCodigo.getText().trim();
            String denominacion = txtDenominacion.getText().trim();
            
            // Validaciones básicas
            if (codigo.isEmpty()) {
                mostrarError("El código es obligatorio", txtCodigo);
                return;
            }

            if (denominacion.isEmpty()) {
                mostrarError("La denominación es obligatoria", txtDenominacion);
                return;
            }

            // Aquí iría la lógica para guardar la familia de artículos
            JOptionPane.showMessageDialog(this, 
                "Familia de artículos guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage(), null);
        }
    }

    @Override
    protected void limpiarCampos() {
        txtCodigo.setText("");
        txtDenominacion.setText("");
        txtDescripcion.setText("");
        txtCodigo.requestFocus();
    }
}