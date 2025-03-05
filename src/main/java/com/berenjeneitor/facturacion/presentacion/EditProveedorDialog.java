package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ProveedoresService;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditProveedorDialog extends JDialog {
    private final ProveedoresService proveedoresService;
    private final Proveedores proveedor;
    private final JTextField nombreField;
    private final JTextField cifField;
    private final JTextField direccionField;
    private final JTextField cpField;
    private final JTextField poblacionField;
    private final JTextField provinciaField;
    private final JTextField paisField;
    private final JTextField telefonoField;
    private final JTextField emailField;
    private final JTextField ibanField;
    private final JTextArea observacionesArea;

    public EditProveedorDialog(Window owner, Proveedores proveedor, ProveedoresService proveedoresService) {
        super(owner, "Editar Proveedor", ModalityType.APPLICATION_MODAL);
        this.proveedoresService = proveedoresService;
        this.proveedor = proveedor;

        setLayout(new BorderLayout());
        setSize(400, 600);
        setLocationRelativeTo(owner);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize fields with current values
        nombreField = new JTextField(proveedor.getNombre(), 20);
        cifField = new JTextField(proveedor.getCif(), 20);
        direccionField = new JTextField(proveedor.getDireccion(), 20);
        cpField = new JTextField(proveedor.getCp(), 20);
        poblacionField = new JTextField(proveedor.getPoblacion(), 20);
        provinciaField = new JTextField(proveedor.getProvincia(), 20);
        paisField = new JTextField(proveedor.getPais(), 20);
        telefonoField = new JTextField(proveedor.getTelefono(), 20);
        emailField = new JTextField(proveedor.getEmail(), 20);
        ibanField = new JTextField(proveedor.getIban(), 20);
        observacionesArea = new JTextArea(proveedor.getObservaciones(), 5, 20);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);

        // Add components to form
        int row = 0;
        addFormField(formPanel, "Nombre:", nombreField, gbc, row++);
        addFormField(formPanel, "CIF:", cifField, gbc, row++);
        addFormField(formPanel, "Dirección:", direccionField, gbc, row++);
        addFormField(formPanel, "CP:", cpField, gbc, row++);
        addFormField(formPanel, "Población:", poblacionField, gbc, row++);
        addFormField(formPanel, "Provincia:", provinciaField, gbc, row++);
        addFormField(formPanel, "País:", paisField, gbc, row++);
        addFormField(formPanel, "Teléfono:", telefonoField, gbc, row++);
        addFormField(formPanel, "Email:", emailField, gbc, row++);
        addFormField(formPanel, "IBAN:", ibanField, gbc, row++);

        // Observaciones with JScrollPane
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(observacionesArea);
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> saveProveedor());
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel);
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
            proveedor.setNombre(nombreField.getText());
            proveedor.setCif(cifField.getText());
            proveedor.setDireccion(direccionField.getText());
            proveedor.setCp(cpField.getText());
            proveedor.setPoblacion(poblacionField.getText());
            proveedor.setProvincia(provinciaField.getText());
            proveedor.setPais(paisField.getText());
            proveedor.setTelefono(telefonoField.getText());
            proveedor.setEmail(emailField.getText());
            proveedor.setIban(ibanField.getText());
            proveedor.setObservaciones(observacionesArea.getText());

            proveedoresService.saveOrUpdate(proveedor);
            JOptionPane.showMessageDialog(this,
                    "Proveedor actualizado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el proveedor: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}