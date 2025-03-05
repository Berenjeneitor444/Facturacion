package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class FormularioTiposIVA extends FormularioBase {
    private JTextField txtTipo, txtPorcentaje;
    private JTextArea txtDescripcion;

    public FormularioTiposIVA() {
        super("Formulario de Tipo de IVA");
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("Tipo:*"));
        txtTipo = createTextField(30);
        panel.add(txtTipo);

        panel.add(new JLabel("Porcentaje:*"));
        txtPorcentaje = createTextField(10);
        panel.add(txtPorcentaje);

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
            String tipo = txtTipo.getText().trim();
            String porcentajeStr = txtPorcentaje.getText().trim();
            
            // Validaciones básicas
            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El tipo de IVA es obligatorio", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                BigDecimal porcentaje = new BigDecimal(porcentajeStr);
                if (porcentaje.compareTo(BigDecimal.ZERO) < 0 || porcentaje.compareTo(new BigDecimal("100")) > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "El porcentaje debe estar entre 0 y 100", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "El porcentaje debe ser un número válido", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aquí iría la lógica para guardar el tipo de IVA
            JOptionPane.showMessageDialog(this, 
                "Tipo de IVA guardado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}