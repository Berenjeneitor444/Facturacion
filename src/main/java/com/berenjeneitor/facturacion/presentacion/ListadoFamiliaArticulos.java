package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FamiliaArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListadoFamiliaArticulos extends JPanel {
    private final FamiliaArticulosService familiaArticulosService;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ListadoFamiliaArticulos(FamiliaArticulosService familiaArticulosService) {
        this.familiaArticulosService = familiaArticulosService;
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"Código", "Denominación", "Observaciones"};
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
        JButton addButton = new JButton("Añadir");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");

        refreshButton.addActionListener(e -> refreshTable());
        addButton.addActionListener(e -> showFamiliaArticulosDialog(null));
        editButton.addActionListener(e -> editSelectedFamiliaArticulos());
        deleteButton.addActionListener(e -> deleteSelectedFamiliaArticulos());

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        try {
            List<FamiliaArticulos> familias = familiaArticulosService.findAll();
            tableModel.setRowCount(0);
            for (FamiliaArticulos familia : familias) {
                Object[] rowData = {
                    familia.getCodigo(),
                    familia.getDenominacion(),
                    familia.getObservaciones()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las familias de artículos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFamiliaArticulosDialog(FamiliaArticulos familia) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                familia == null ? "Nueva Familia de Artículos" : "Editar Familia de Artículos",
                true);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField codigoField = new JTextField(20);
        JTextField denominacionField = new JTextField(20);
        JTextArea observacionesArea = new JTextArea(5, 20);
        observacionesArea.setLineWrap(true);
        observacionesArea.setWrapStyleWord(true);

        if (familia != null) {
            codigoField.setText(familia.getCodigo());
            denominacionField.setText(familia.getDenominacion());
            observacionesArea.setText(familia.getObservaciones());
        }

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        formPanel.add(codigoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Denominación:"), gbc);
        gbc.gridx = 1;
        formPanel.add(denominacionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(observacionesArea), gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> {
            try {
                FamiliaArticulos newFamilia = familia != null ? familia : new FamiliaArticulos();
                newFamilia.setCodigo(codigoField.getText());
                newFamilia.setDenominacion(denominacionField.getText());
                newFamilia.setObservaciones(observacionesArea.getText());

                familiaArticulosService.saveOrUpdate(newFamilia);
                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error al guardar la familia de artículos: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonsPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editSelectedFamiliaArticulos() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String codigo = (String) table.getValueAt(selectedRow, 0);
                FamiliaArticulos familia = familiaArticulosService.findByCodigo(codigo);
                if (familia != null) {
                    showFamiliaArticulosDialog(familia);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la familia de artículos: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una familia de artículos",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedFamiliaArticulos() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String codigo = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de que desea eliminar esta familia de artículos?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    familiaArticulosService.delete(codigo);
                    refreshTable();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar la familia de artículos: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una familia de artículos",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}