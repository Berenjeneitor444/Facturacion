package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ConfiguracionEmpresaService;
import com.berenjeneitor.facturacion.persistencia.entidades.ConfiguracionEmpresa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormularioConfiguracionEmpresa extends JPanel {
    private final ConfiguracionEmpresaService configuracionEmpresaService;
    private final JTextField nombreField;
    private final JTextField cifField;
    private final JTextField direccionField;
    private final JTextField cpField;
    private final JTextField poblacionField;
    private final JTextField provinciaField;
    private final JTextField paisField;
    private final JTextField telefonoField;
    private final JTextField emailField;
    private final JTextField webField;
    private final JTextArea observacionesArea;

    public FormularioConfiguracionEmpresa(ConfiguracionEmpresaService configuracionEmpresaService) {
        this.configuracionEmpresaService = configuracionEmpresaService;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize fields
        nombreField = new JTextField(40);
        cifField = new JTextField(20);
        direccionField = new JTextField(40);
        cpField = new JTextField(10);
        poblacionField = new JTextField(30);
        provinciaField = new JTextField(30);
        paisField = new JTextField(30);
        telefonoField = new JTextField(20);
        emailField = new JTextField(40);
        webField = new JTextField(40);
        observacionesArea = new JTextArea(5, 40);
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
        addFormField(formPanel, "Web:", webField, gbc, row++);

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

        saveButton.addActionListener(e -> saveConfiguracion());
        clearButton.addActionListener(e -> clearForm());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);

        // Add panels to main panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load current configuration if exists
        loadConfiguracion();
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

    private void loadConfiguracion() {
        try {
            ConfiguracionEmpresa config = configuracionEmpresaService.getCurrent();
            if (config != null) {
                nombreField.setText(config.getNombre());
                cifField.setText(config.getCif());
                direccionField.setText(config.getDireccion());
                cpField.setText(config.getCp());
                poblacionField.setText(config.getPoblacion());
                provinciaField.setText(config.getProvincia());
                paisField.setText(config.getPais());
                telefonoField.setText(config.getTelefono());
                emailField.setText(config.getEmail());
                webField.setText(config.getWeb());
                observacionesArea.setText(config.getObservaciones());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar la configuración: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveConfiguracion() {
        try {
            if (nombreField.getText().trim().isEmpty()) {
                throw new Exception("El nombre de la empresa es obligatorio");
            }
            if (cifField.getText().trim().isEmpty()) {
                throw new Exception("El CIF es obligatorio");
            }

            ConfiguracionEmpresa config = new ConfiguracionEmpresa();
            config.setNombre(nombreField.getText().trim());
            config.setCif(cifField.getText().trim());
            config.setDireccion(direccionField.getText().trim());
            config.setCp(cpField.getText().trim());
            config.setPoblacion(poblacionField.getText().trim());
            config.setProvincia(provinciaField.getText().trim());
            config.setPais(paisField.getText().trim());
            config.setTelefono(telefonoField.getText().trim());
            config.setEmail(emailField.getText().trim());
            config.setWeb(webField.getText().trim());
            config.setObservaciones(observacionesArea.getText().trim());

            configuracionEmpresaService.saveOrUpdate(config);
            JOptionPane.showMessageDialog(this,
                    "Configuración guardada correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la configuración: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nombreField.setText("");
        cifField.setText("");
        direccionField.setText("");
        cpField.setText("");
        poblacionField.setText("");
        provinciaField.setText("");
        paisField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        webField.setText("");
        observacionesArea.setText("");
        nombreField.requestFocus();
    }
}