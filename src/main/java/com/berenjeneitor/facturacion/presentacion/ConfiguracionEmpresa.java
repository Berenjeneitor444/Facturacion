package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;

public class ConfiguracionEmpresa extends FormularioBase {
    private JTextField txtNombre, txtCIF, txtDireccion, txtCP;
    private JTextField txtPoblacion, txtProvincia, txtPais;
    private JTextField txtTelefono, txtEmail, txtWeb;
    private JTextField txtRegistroMercantil;
    private JTextArea txtPieFactura;

    public ConfiguracionEmpresa() {
        super("Configuración de Empresa");
    }

    @Override
    protected void construirFormulario() {
        // Panel de datos básicos
        JPanel panelDatosBasicos = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDatosBasicos.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));

        panelDatosBasicos.add(new JLabel("Nombre:*"));
        txtNombre = createTextField(80);
        panelDatosBasicos.add(txtNombre);

        panelDatosBasicos.add(new JLabel("CIF:*"));
        txtCIF = createTextField(12);
        panelDatosBasicos.add(txtCIF);

        // Panel de dirección
        JPanel panelDireccion = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDireccion.setBorder(BorderFactory.createTitledBorder("Dirección"));

        panelDireccion.add(new JLabel("Dirección:"));
        txtDireccion = createTextField(100);
        panelDireccion.add(txtDireccion);

        panelDireccion.add(new JLabel("Código Postal:"));
        txtCP = createTextField(5);
        panelDireccion.add(txtCP);

        panelDireccion.add(new JLabel("Población:"));
        txtPoblacion = createTextField(50);
        panelDireccion.add(txtPoblacion);

        panelDireccion.add(new JLabel("Provincia:"));
        txtProvincia = createTextField(30);
        panelDireccion.add(txtProvincia);

        panelDireccion.add(new JLabel("País:"));
        txtPais = createTextField(30);
        txtPais.setText("España");
        panelDireccion.add(txtPais);

        // Panel de contacto
        JPanel panelContacto = new JPanel(new GridLayout(0, 2, 5, 5));
        panelContacto.setBorder(BorderFactory.createTitledBorder("Contacto"));

        panelContacto.add(new JLabel("Teléfono:"));
        txtTelefono = createTextField(15);
        panelContacto.add(txtTelefono);

        panelContacto.add(new JLabel("Email:"));
        txtEmail = createTextField(80);
        panelContacto.add(txtEmail);

        panelContacto.add(new JLabel("Web:"));
        txtWeb = createTextField(80);
        panelContacto.add(txtWeb);

        // Panel legal
        JPanel panelLegal = new JPanel(new GridLayout(0, 2, 5, 5));
        panelLegal.setBorder(BorderFactory.createTitledBorder("Información Legal"));

        panelLegal.add(new JLabel("Registro Mercantil:"));
        txtRegistroMercantil = createTextField(100);
        panelLegal.add(txtRegistroMercantil);

        panelLegal.add(new JLabel("Pie de Factura:"));
        txtPieFactura = new JTextArea(4, 20);
        txtPieFactura.setLineWrap(true);
        txtPieFactura.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtPieFactura);
        panelLegal.add(scrollPane);

        // Agregar todos los paneles al panel principal
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(panelDatosBasicos);
        panel.add(panelDireccion);
        panel.add(panelContacto);
        panel.add(panelLegal);
    }

    @Override
    protected void guardar() {
        try {
            // Validaciones básicas
            if (txtNombre.getText().trim().isEmpty()) {
                mostrarError("El nombre de la empresa es obligatorio", txtNombre);
                return;
            }

            if (txtCIF.getText().trim().isEmpty()) {
                mostrarError("El CIF es obligatorio", txtCIF);
                return;
            }

            // Aquí iría la lógica para guardar la configuración
            JOptionPane.showMessageDialog(this, 
                "Configuración guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar la configuración: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarError(String mensaje, JComponent componente) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error de validación", 
            JOptionPane.ERROR_MESSAGE);
        componente.requestFocus();
    }
}