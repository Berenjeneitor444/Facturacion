package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.RectificativasClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativasClientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListadoRectificativasClientes extends JPanel {
    private final RectificativasClientesService rectificativasService;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat;

    public ListadoRectificativasClientes(RectificativasClientesService rectificativasService) {
        this.rectificativasService = rectificativasService;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"Número", "Fecha", "Cliente", "Base Imponible", "IVA", "Total", "Observaciones"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Actualizar");
        JButton viewButton = new JButton("Ver Detalle");

        refreshButton.addActionListener(e -> refreshTable());
        viewButton.addActionListener(e -> viewSelectedRectificativa());

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(viewButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        try {
            List<RectificativasClientes> rectificativas = rectificativasService.findAll();
            tableModel.setRowCount(0);
            for (RectificativasClientes rectificativa : rectificativas) {
                Object[] rowData = {
                    rectificativa.getNumero(),
                    dateFormat.format(rectificativa.getFecha()),
                    rectificativa.getCliente().getNombre(),
                    rectificativa.getBaseImponible(),
                    rectificativa.getIva(),
                    rectificativa.getTotal(),
                    rectificativa.getObservaciones()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las facturas rectificativas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSelectedRectificativa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                Integer numero = (Integer) table.getValueAt(selectedRow, 0);
                RectificativasClientes rectificativa = rectificativasService.findByNumero(numero);
                if (rectificativa != null) {
                    showRectificativaDetailsDialog(rectificativa);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la factura rectificativa: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una factura rectificativa",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showRectificativaDetailsDialog(RectificativasClientes rectificativa) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Detalle de Factura Rectificativa", true);
        dialog.setLayout(new BorderLayout());

        // Create details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add header information
        addDetailField(detailsPanel, "Número:", String.valueOf(rectificativa.getNumero()), gbc, 0);
        addDetailField(detailsPanel, "Fecha:", dateFormat.format(rectificativa.getFecha()), gbc, 1);
        addDetailField(detailsPanel, "Cliente:", rectificativa.getCliente().getNombre(), gbc, 2);
        addDetailField(detailsPanel, "Base Imponible:", rectificativa.getBaseImponible().toString(), gbc, 3);
        addDetailField(detailsPanel, "IVA:", rectificativa.getIva().toString(), gbc, 4);
        addDetailField(detailsPanel, "Total:", rectificativa.getTotal().toString(), gbc, 5);

        // Add line items table
        String[] columnNames = {"Artículo", "Descripción", "Cantidad", "Precio", "IVA", "Total"};
        DefaultTableModel lineItemsModel = new DefaultTableModel(columnNames, 0);
        JTable lineItemsTable = new JTable(lineItemsModel);

        rectificativa.getLineasRectificativa().forEach(linea -> {
            Object[] rowData = {
                linea.getArticulo().getCodigo(),
                linea.getArticulo().getDescripcion(),
                linea.getCantidad(),
                linea.getPrecio(),
                linea.getIva(),
                linea.getTotal()
            };
            lineItemsModel.addRow(rowData);
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        detailsPanel.add(new JScrollPane(lineItemsTable), gbc);

        // Add observations if any
        if (rectificativa.getObservaciones() != null && !rectificativa.getObservaciones().isEmpty()) {
            gbc.gridy = 7;
            gbc.weighty = 0.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextArea observacionesArea = new JTextArea(rectificativa.getObservaciones());
            observacionesArea.setEditable(false);
            observacionesArea.setLineWrap(true);
            observacionesArea.setWrapStyleWord(true);
            detailsPanel.add(new JScrollPane(observacionesArea), gbc);
        }

        // Add close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        dialog.add(new JScrollPane(detailsPanel), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addDetailField(JPanel panel, String label, String value,
                              GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(new JLabel(value), gbc);
    }
}