package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FacturasClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.FacturasClientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class ListadoFacturas extends JPanel {
    private final FacturasClientesService facturasService;
    private final MainFrame father;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat;

    public ListadoFacturas(FacturasClientesService facturasService, MainFrame father) {
        this.facturasService = facturasService;
        this.father = father;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"ID", "Número", "Fecha", "Cliente", "Base Imponible", "IVA", "Total", "Cobrada"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class;
                return Object.class;
            }
        };

        // Create table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Actualizar");
        JButton viewButton = new JButton("Ver Detalle");
        JButton deleteButton = new JButton("Eliminar");
        JButton markAsPaidButton = new JButton("Marcar como Cobrada");

        refreshButton.addActionListener(e -> refreshTable());
        viewButton.addActionListener(e -> viewSelectedFactura());
        deleteButton.addActionListener(e -> deleteSelectedFactura());
        markAsPaidButton.addActionListener(e -> markSelectedFacturaAsPaid());

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(viewButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(markAsPaidButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<FacturasClientes> facturas = facturasService.findAll();
            for (FacturasClientes factura : facturas) {
                Object[] row = {
                    factura.getId(),
                    factura.getNumero(),
                    dateFormat.format(factura.getFecha()),
                    factura.getCliente().getNombre(),
                    factura.getBaseImponible(),
                    factura.getIva(),
                    factura.getTotal(),
                    factura.getCobrada()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las facturas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSelectedFactura() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            try {
                Optional<FacturasClientes> factura = facturasService.findById(id);
                if (factura.isPresent()) {
                    // Show view dialog
                    ViewFacturaDialog dialog = new ViewFacturaDialog(father, factura);
                    dialog.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la factura: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una factura para ver",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedFactura() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar esta factura?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    facturasService.deleteById(id);
                    refreshTable();
                    JOptionPane.showMessageDialog(this,
                            "Factura eliminada correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar la factura: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una factura para eliminar",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void markSelectedFacturaAsPaid() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            try {
                Optional<FacturasClientes> factura = facturasService.findById(id);
                if (factura.isPresent()) {
                    FacturasClientes facturaObject = factura.get();
                    facturaObject.setCobrada(true);
                    facturasService.saveOrUpdate(facturaObject);
                    refreshTable();
                    JOptionPane.showMessageDialog(this,
                            "Factura marcada como cobrada correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar la factura: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una factura para marcar como cobrada",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}