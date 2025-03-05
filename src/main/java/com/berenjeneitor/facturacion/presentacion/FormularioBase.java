package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class FormularioBase extends JPanel {
    protected JPanel panel;
    protected JButton btnGuardar, btnCancelar;

    public FormularioBase(String titulo) {

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // Formato de formulario
        add(panel, BorderLayout.CENTER);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardar());

        construirFormulario();
    }

    // Mét0do abstracto para que cada formulario implemente su propia estructura
    protected abstract void construirFormulario();

    // Mét0do abstracto para guardar los datos
    protected abstract void guardar();
}
