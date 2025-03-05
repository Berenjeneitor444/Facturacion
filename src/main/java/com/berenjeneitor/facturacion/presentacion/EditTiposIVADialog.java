package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.TiposIVAService;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class EditTiposIVADialog extends JDialog {
    private final TiposIVAService tiposIVAService;
    private final TiposIVA tipoIVA;
    private final JTextField ivaField;
    private final JTextArea observacionesArea;

    public EditTiposIVADialog(JFrame parent, TiposIVA tipoIVA, TiposIVAService tiposIVAService) {
        super(parent, "Editar Tipo de IVA", true);
        this.tipoIVA = tipoIVA;
        this.tiposIVAService = tiposIVAService;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

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
        ivaField.setText(tipoIVA.getIva().toString());
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
        observacionesArea.setText(tipoIVA.getObservaciones());
        JScrollPane scrollPane = new JScrollPane(observacionesArea);
        formPanel.add(scrollPane, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> {
            if (saveChanges()) {
                dispose();
            }
        });
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        // Add panels
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private boolean saveChanges() {
        try {
            BigDecimal iva = new BigDecimal(ivaField.getText());
            String observaciones = observacionesArea.getText();

            tipoIVA.setIva(iva);
            tipoIVA.setObservaciones(observaciones);

            tiposIVAService.saveOrUpdate(tipoIVA);
            JOptionPane.showMessageDialog(this,
                    "Tipo de IVA actualizado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un valor válido para el IVA",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el tipo de IVA: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}