package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ProveedoresService;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListadoProveedores extends JPanel {
    private final ProveedoresService proveedoresService;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ListadoProveedores(ProveedoresService proveedoresService) {
        this.proveedoresService = proveedoresService;
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"Código", "Nombre", "CIF", "Dirección", "Teléfono", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Actualizar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");

        refreshButton.addActionListener(e -> refreshTable());
        editButton.addActionListener(e -> editSelectedProveedor());
        deleteButton.addActionListener(e -> deleteSelectedProveedor());

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Proveedores> proveedores = proveedoresService.findAll();
            for (Proveedores proveedor : proveedores) {
                Object[] row = {
                    proveedor.getCodigo(),
                    proveedor.getNombre(),
                    proveedor.getCif(),
                    proveedor.getDireccion(),
                    proveedor.getTelefono(),
                    proveedor.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los proveedores: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editSelectedProveedor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                Proveedores proveedor = proveedoresService.findByCodigo(codigo);
                if (proveedor != null) {
                    EditProveedorDialog dialog = new EditProveedorDialog(SwingUtilities.getWindowAncestor(this),
                            proveedor, proveedoresService);
                    dialog.setVisible(true);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Proveedor no encontrado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar el proveedor: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un proveedor para editar",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedProveedor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar este proveedor?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Proveedores proveedor = proveedoresService.findByCodigo(codigo);
                    if (proveedor != null) {
                        proveedoresService.deleteById(proveedor.getId());
                        refreshTable();
                        JOptionPane.showMessageDialog(this,
                                "Proveedor eliminado correctamente",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar el proveedor: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un proveedor para eliminar",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}