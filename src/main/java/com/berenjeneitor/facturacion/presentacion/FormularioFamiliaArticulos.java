package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FamiliaArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormularioFamiliaArticulos extends JPanel {
    private final FamiliaArticulosService familiaArticulosService;
    private final JTextField codigoField;
    private final JTextField denominacionField;
    private final JTextArea observacionesArea;

    public FormularioFamiliaArticulos(FamiliaArticulosService familiaArticulosService) {
        this.familiaArticulosService = familiaArticulosService;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize fields
        codigoField = new JTextField(20);
        denominacionField = new JTextField(20);
        observacionesArea = new JTextArea(5, 20);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);

        // Add components to form
        int row = 0;
        addFormField(formPanel, "Código:", codigoField, gbc, row++);
        addFormField(formPanel, "Denominación:", denominacionField, gbc, row++);

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

        saveButton.addActionListener(e -> saveFamiliaArticulos());
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

    private void saveFamiliaArticulos() {
        try {
            if (codigoField.getText().trim().isEmpty()) {
                throw new Exception("El código es obligatorio");
            }
            if (denominacionField.getText().trim().isEmpty()) {
                throw new Exception("La denominación es obligatoria");
            }

            FamiliaArticulos familia = new FamiliaArticulos();
            familia.setCodigo(codigoField.getText().trim());
            familia.setDenominacion(denominacionField.getText().trim());

            familiaArticulosService.saveOrUpdate(familia);
            JOptionPane.showMessageDialog(this,
                    "Familia de artículos guardada correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la familia de artículos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        codigoField.setText("");
        denominacionField.setText("");
        observacionesArea.setText("");
        codigoField.requestFocus();
    }
}