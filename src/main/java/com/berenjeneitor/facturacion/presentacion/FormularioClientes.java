package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class FormularioClientes extends JPanel {
    private final ClientesService clientesService;

    private JTextField txtCif;
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtCP;
    private JTextField txtPoblacion;
    private JTextField txtProvincia;
    private JTextField txtPais;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtIBAN;
    private JTextField txtRiesgo;
    private JTextField txtDescuento;
    private JTextArea txtObservaciones;

    public FormularioClientes(ClientesService clientesService) {
        this.clientesService = clientesService;

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
        int row = 0;

        // Personal Information
        addFormField(formPanel, "CIF:", txtCif, gbc, row++);
        addFormField(formPanel, "Nombre:", txtNombre, gbc, row++);
        addFormField(formPanel, "Teléfono:", txtTelefono, gbc, row++);
        addFormField(formPanel, "Email:", txtEmail, gbc, row++);

        // Address Information
        addFormField(formPanel, "Dirección:", txtDireccion, gbc, row++);
        addFormField(formPanel, "CP:", txtCP, gbc, row++);
        addFormField(formPanel, "Población:", txtPoblacion, gbc, row++);
        addFormField(formPanel, "Provincia:", txtProvincia, gbc, row++);
        addFormField(formPanel, "País:", txtPais, gbc, row++);

        // Financial Information
        addFormField(formPanel, "IBAN:", txtIBAN, gbc, row++);
        addFormField(formPanel, "Riesgo:", txtRiesgo, gbc, row++);
        addFormField(formPanel, "Descuento (%):", txtDescuento, gbc, row++);

        // Observations
        gbc.gridx = 0;
        gbc.gridy = row++;
        formPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        formPanel.add(new JScrollPane(txtObservaciones), gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);

        // Add panels to main panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        txtCif = new JTextField(20);
        txtNombre = new JTextField(20);
        txtDireccion = new JTextField(20);
        txtCP = new JTextField(10);
        txtPoblacion = new JTextField(20);
        txtProvincia = new JTextField(20);
        txtPais = new JTextField(20);
        txtTelefono = new JTextField(16);
        txtEmail = new JTextField(20);
        txtIBAN = new JTextField(20);
        txtRiesgo = new JTextField(10);
        txtDescuento = new JTextField(10);
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
    }

    private void addFormField(JPanel panel, String label, JComponent field,
                            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridheight = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void guardarCliente() {
        try {
            Clientes cliente = new Clientes();
            cliente.setCif(txtCif.getText());
            cliente.setNombre(txtNombre.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setCp(txtCP.getText());
            cliente.setPoblacion(txtPoblacion.getText());
            cliente.setProvincia(txtProvincia.getText());
            cliente.setPais(txtPais.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setIban(txtIBAN.getText());
            
            String riesgoText = txtRiesgo.getText().trim();
            if (!riesgoText.isEmpty()) {
                cliente.setRiesgo(new BigDecimal(riesgoText));
            }
            
            String descuentoText = txtDescuento.getText().trim();
            if (!descuentoText.isEmpty()) {
                cliente.setDescuento(new BigDecimal(descuentoText));
            }
            
            cliente.setObservaciones(txtObservaciones.getText());

            clientesService.saveOrUpdate(cliente);
            JOptionPane.showMessageDialog(this,
                    "Cliente guardado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtCif.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCP.setText("");
        txtPoblacion.setText("");
        txtProvincia.setText("");
        txtPais.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtIBAN.setText("");
        txtRiesgo.setText("");
        txtDescuento.setText("");
        txtObservaciones.setText("");
    }
}