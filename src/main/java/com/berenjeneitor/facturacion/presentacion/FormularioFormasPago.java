package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FormaPagoService;
import com.berenjeneitor.facturacion.negocio.ValidationException;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;

import javax.swing.*;
import java.awt.*;

public class FormularioFormasPago extends FormularioBase {
    private JTextField txtTipo;
    private JTextArea txtObservaciones;
    
    private final FormaPagoService formaPagoService;
    private FormaPago formaPagoActual;

    public FormularioFormasPago(FormaPagoService formaPagoService) {
        super("Formulario de Forma de Pago");
        this.formaPagoService = formaPagoService;
        formaPagoActual = new FormaPago();
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("Tipo:*"));
        txtTipo = createTextField(40);
        panel.add(txtTipo);

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
            String tipo = txtTipo.getText().trim();
            String observaciones = txtObservaciones.getText();
            
            // Validación básica
            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El tipo de forma de pago es obligatorio", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear o actualizar el objeto
            formaPagoActual.setTipo(tipo);
            formaPagoActual.setObservaciones(observaciones);
            
            // Guardar usando el servicio
            formaPagoService.saveOrUpdate(formaPagoActual);
            
            JOptionPane.showMessageDialog(this, 
                "Forma de pago guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            formaPagoActual = new FormaPago();
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void limpiarCampos() {
        txtTipo.setText("");
        txtObservaciones.setText("");
    }
}