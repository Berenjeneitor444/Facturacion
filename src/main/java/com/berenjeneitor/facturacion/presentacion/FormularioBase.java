package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;

public abstract class FormularioBase extends JPanel {
    protected JPanel panel;
    protected JButton btnGuardar, btnLimpiar, btnCerrar;
    protected String titulo;

    public FormularioBase(String titulo) {
        this.titulo = titulo;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel principal para los campos del formulario
        panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(panel), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Configurar eventos
        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> cerrar());

        // Construir el formulario específico
        construirFormulario();
    }

    protected JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setMaximumSize(new Dimension(
            textField.getPreferredSize().width,
            textField.getPreferredSize().height));
        return textField;
    }

    protected void cerrar() {
        // Buscar el MainFrame padre
        Container parent = getParent();
        while (parent != null && !(parent instanceof MainFrame)) {
            parent = parent.getParent();
        }

        if (parent instanceof MainFrame) {
            ((MainFrame) parent).showCard("home");
        }
    }

    protected void mostrarError(String mensaje, JComponent componente) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Error de validación",
            JOptionPane.ERROR_MESSAGE);
        if (componente != null) {
            componente.requestFocus();
        }
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void construirFormulario();
    protected abstract void guardar();
    protected abstract void limpiarCampos();
}