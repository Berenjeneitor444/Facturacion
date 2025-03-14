package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ProveedoresService;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormularioProveedores extends JPanel {
    private final ProveedoresService proveedoresService;
    private final JTextField codigoField;
    private final JTextField nombreField;
    private final JTextField cifField;
    private final JTextField direccionField;
    private final JTextField telefonoField;
    private final JTextField emailField;
    private final JTextArea observacionesArea;

    public FormularioProveedores(ProveedoresService proveedoresService) {
        this.proveedoresService = proveedoresService;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize fields
        codigoField = new JTextField(20);
        nombreField = new JTextField(20);
        cifField = new JTextField(20);
        direccionField = new JTextField(20);
        telefonoField = new JTextField(20);
        emailField = new JTextField(20);
        observacionesArea = new JTextArea(5, 20);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);

        // Add components to form
        int row = 0;
        addFormField(formPanel, "Código:", codigoField, gbc, row++);
        addFormField(formPanel, "Nombre:", nombreField, gbc, row++);
        addFormField(formPanel, "CIF:", cifField, gbc, row++);
        addFormField(formPanel, "Dirección:", direccionField, gbc, row++);
        addFormField(formPanel, "Teléfono:", telefonoField, gbc, row++);
        addFormField(formPanel, "Email:", emailField, gbc, row++);

        // Observaciones with JScrollPane
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(observacionesArea);
        formPanel.add(scrollPane, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton clearButton = new JButton("Limpiar");

        saveButton.addActionListener(e -> saveProveedor());
        clearButton.addActionListener(e -> clearForm());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);

        // Add panels to main panel
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String label, JComponent field,
                            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void saveProveedor() {
        try {
            Proveedores proveedor = new Proveedores();
            proveedor.setCodigo(codigoField.getText());
            proveedor.setNombre(nombreField.getText());
            proveedor.setCif(cifField.getText());
            proveedor.setDireccion(direccionField.getText());
            proveedor.setTelefono(telefonoField.getText());
            proveedor.setEmail(emailField.getText());
            proveedor.setObservaciones(observacionesArea.getText());

            proveedoresService.saveOrUpdate(proveedor);
            JOptionPane.showMessageDialog(this,
                    "Proveedor guardado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el proveedor: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        codigoField.setText("");
        nombreField.setText("");
        cifField.setText("");
        direccionField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        observacionesArea.setText("");
        codigoField.requestFocus();
    }
}