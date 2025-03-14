package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.TiposIVAService;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class FormulariosTiposIva extends JPanel {
    private final TiposIVAService tiposIVAService;
    private final JTextField ivaField;
    private final JTextArea observacionesArea;

    public FormulariosTiposIva(TiposIVAService tiposIVAService) {
        this.tiposIVAService = tiposIVAService;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // IVA Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("IVA (%): "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        ivaField = new JTextField(10);
        formPanel.add(ivaField, gbc);

        // Observaciones Area
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Observaciones: "), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        observacionesArea = new JTextArea(5, 30);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(observacionesArea);
        formPanel.add(scrollPane, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton clearButton = new JButton("Limpiar");

        saveButton.addActionListener(e -> saveIVA());
        clearButton.addActionListener(e -> clearForm());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);

        // Add panels to main panel
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void saveIVA() {
        try {
            BigDecimal iva = new BigDecimal(ivaField.getText());
            String observaciones = observacionesArea.getText();

            TiposIVA tipoIVA = new TiposIVA();
            tipoIVA.setIva(iva);
            tipoIVA.setObservaciones(observaciones);

            tiposIVAService.saveOrUpdate(tipoIVA);
            JOptionPane.showMessageDialog(this,
                    "Tipo de IVA guardado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un valor válido para el IVA",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el tipo de IVA: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        ivaField.setText("");
        observacionesArea.setText("");
        ivaField.requestFocus();
    }
}