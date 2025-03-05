package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.RectificativasClientesService;
import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormularioRectificativasClientes extends JPanel {
    private final RectificativasClientesService rectificativasService;
    private final ClientesService clientesService;
    private final ArticulosService articulosService;

    private JComboBox<Clientes> clienteComboBox;
    private JTextField numeroField;
    private JTextField fechaField;
    private JComboBox<FacturasClientes> facturaComboBox;
    private JTable lineasTable;
    private DefaultTableModel tableModel;
    private JTextField baseImponibleField;
    private JTextField ivaField;
    private JTextField totalField;
    private JTextArea observacionesArea;

    public FormularioRectificativasClientes(RectificativasClientesService rectificativasService,
                                           ClientesService clientesService,
                                           ArticulosService articulosService) {
        this.rectificativasService = rectificativasService;
        this.clientesService = clientesService;
        this.articulosService = articulosService;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create panels
        JPanel headerPanel = createHeaderPanel();
        JPanel lineasPanel = createLineasPanel();
        JPanel totalesPanel = createTotalesPanel();
        JPanel observacionesPanel = createObservacionesPanel();
        JPanel buttonsPanel = createButtonsPanel();

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(lineasPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(totalesPanel, BorderLayout.NORTH);
        rightPanel.add(observacionesPanel, BorderLayout.CENTER);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Load initial data
        loadClientes();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        clienteComboBox = new JComboBox<>();
        clienteComboBox.addActionListener(e -> loadFacturasCliente());
        panel.add(clienteComboBox, gbc);

        // Factura Original
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Factura Original:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        facturaComboBox = new JComboBox<>();
        panel.add(facturaComboBox, gbc);

        // Número
        gbc.gridx = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Número:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.5;
        numeroField = new JTextField();
        panel.add(numeroField, gbc);

        // Fecha
        gbc.gridx = 6;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 7;
        gbc.weightx = 0.5;
        fechaField = new JTextField();
        panel.add(fechaField, gbc);

        return panel;
    }

    private JPanel createLineasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Líneas de Rectificativa"));

        // Table
        String[] columnNames = {"Artículo", "Descripción", "Cantidad", "Precio", "IVA", "Base Imponible", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        lineasTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(lineasTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Añadir Línea");
        JButton removeButton = new JButton("Eliminar Línea");

        addButton.addActionListener(e -> addLinea());
        removeButton.addActionListener(e -> removeLinea());

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTotalesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Totales"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Base Imponible
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Base Imponible:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        baseImponibleField = new JTextField();
        baseImponibleField.setEditable(false);
        panel.add(baseImponibleField, gbc);

        // IVA
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("IVA:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        ivaField = new JTextField();
        ivaField.setEditable(false);
        panel.add(ivaField, gbc);

        // Total
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Total:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        totalField = new JTextField();
        totalField.setEditable(false);
        panel.add(totalField, gbc);

        return panel;
    }

    private JPanel createObservacionesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Observaciones"));

        observacionesArea = new JTextArea(5, 30);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);

        panel.add(new JScrollPane(observacionesArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Guardar");
        JButton clearButton = new JButton("Limpiar");

        saveButton.addActionListener(e -> saveRectificativa());
        clearButton.addActionListener(e -> clearForm());

        panel.add(saveButton);
        panel.add(clearButton);

        return panel;
    }

    private void loadClientes() {
        try {
            List<Clientes> clientes = clientesService.findAll();
            DefaultComboBoxModel<Clientes> model = new DefaultComboBoxModel<>();
            for (Clientes cliente : clientes) {
                model.addElement(cliente);
            }
            clienteComboBox.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los clientes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFacturasCliente() {
        Clientes cliente = (Clientes) clienteComboBox.getSelectedItem();
        if (cliente != null) {
            // TODO: Implement loading of client's invoices
            // facturaComboBox.setModel(new DefaultComboBoxModel<>(facturas.toArray(new FacturasClientes[0])));
        }
    }

    private void addLinea() {
        // TODO: Implement adding a new line to the rectification invoice
    }

    private void removeLinea() {
        int selectedRow = lineasTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
            updateTotals();
        }
    }

    private void updateTotals() {
        BigDecimal baseImponible = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        // Calculate totals from table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            baseImponible = baseImponible.add((BigDecimal) tableModel.getValueAt(i, 5));
            iva = iva.add((BigDecimal) tableModel.getValueAt(i, 4));
            total = total.add((BigDecimal) tableModel.getValueAt(i, 6));
        }

        baseImponibleField.setText(baseImponible.toString());
        ivaField.setText(iva.toString());
        totalField.setText(total.toString());
    }

    private void saveRectificativa() {
        try {
            // Validate form
            if (clienteComboBox.getSelectedItem() == null) {
                throw new Exception("Debe seleccionar un cliente");
            }
            if (facturaComboBox.getSelectedItem() == null) {
                throw new Exception("Debe seleccionar una factura original");
            }
            if (tableModel.getRowCount() == 0) {
                throw new Exception("Debe añadir al menos una línea a la rectificativa");
            }

            // Create rectificativa
            RectificativasClientes rectificativa = new RectificativasClientes();
            rectificativa.setCliente((Clientes) clienteComboBox.getSelectedItem());
            rectificativa.setFactura((FacturasClientes) facturaComboBox.getSelectedItem());
            rectificativa.setFecha(new Date()); // TODO: Parse from fechaField
            rectificativa.setNumero(Integer.parseInt(numeroField.getText()));
            rectificativa.setBaseImponible(new BigDecimal(baseImponibleField.getText()));
            rectificativa.setIva(new BigDecimal(ivaField.getText()));
            rectificativa.setTotal(new BigDecimal(totalField.getText()));
            rectificativa.setObservaciones(observacionesArea.getText());

            // Create lineas
            List<LineasRectificativa> lineas = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                LineasRectificativa linea = new LineasRectificativa();
                linea.setRectificativa(rectificativa);
                // TODO: Set line data from table
                lineas.add(linea);
            }
            rectificativa.setLineasRectificativa(lineas);

            // Save
            rectificativasService.saveOrUpdate(rectificativa);
            JOptionPane.showMessageDialog(this,
                    "Rectificativa guardada correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar la rectificativa: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        clienteComboBox.setSelectedIndex(-1);
        facturaComboBox.setSelectedIndex(-1);
        numeroField.setText("");
        fechaField.setText("");
        tableModel.setRowCount(0);
        baseImponibleField.setText("");
        ivaField.setText("");
        totalField.setText("");
        observacionesArea.setText("");
    }
}