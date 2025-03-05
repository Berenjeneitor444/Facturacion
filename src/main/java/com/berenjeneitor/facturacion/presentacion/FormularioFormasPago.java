package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FormaPagoService;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormularioFormasPago extends JPanel {
    private final FormaPagoService formaPagoService;

    private JTextField txtTipo;
    private JTextArea txtObservaciones;

    public FormularioFormasPago(FormaPagoService formaPagoService) {
        this.formaPagoService = formaPagoService;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        initializeComponents();

        // Add components to form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        formPanel.add(new JScrollPane(txtObservaciones), gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarFormaPago());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);

        // Add panels to main panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        txtTipo = new JTextField(20);
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
    }

    private void guardarFormaPago() {
        try {
            FormaPago formaPago = new FormaPago();
            formaPago.setTipo(txtTipo.getText());
            formaPago.setObservaciones(txtObservaciones.getText());

            formaPagoService.saveOrUpdate(formaPago);
            JOptionPane.showMessageDialog(this,
                    "Forma de pago guardada correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la forma de pago: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtTipo.setText("");
        txtObservaciones.setText("");
    }
}