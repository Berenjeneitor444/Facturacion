package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FormaPagoService;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListadoFormasPago extends JPanel {
    private final FormaPagoService formaPagoService;
    private final MainFrame mainFrame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtBuscar;

    public ListadoFormasPago(FormaPagoService formaPagoService, MainFrame mainFrame) {
        this.formaPagoService = formaPagoService;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarFormasPago());

        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);

        // Table
        String[] columnNames = {"Tipo", "Observaciones"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnNuevo.addActionListener(e -> nuevaFormaPago());
        btnEditar.addActionListener(e -> editarFormaPago());
        btnEliminar.addActionListener(e -> eliminarFormaPago());

        buttonsPanel.add(btnNuevo);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnEliminar);

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load initial data
        cargarFormasPago();
    }

    private void cargarFormasPago() {
        tableModel.setRowCount(0);
        List<FormaPago> formasPago = formaPagoService.findAll();
        for (FormaPago formaPago : formasPago) {
            Object[] row = {
                formaPago.getTipo(),
                formaPago.getObservaciones()
            };
            tableModel.addRow(row);
        }
    }

    private void buscarFormasPago() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        tableModel.setRowCount(0);

        List<FormaPago> formasPago = formaPagoService.findAll();
        for (FormaPago formaPago : formasPago) {
            if (formaPago.getTipo().toLowerCase().contains(busqueda)) {
                Object[] row = {
                    formaPago.getTipo(),
                    formaPago.getObservaciones()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void nuevaFormaPago() {
        mainFrame.showCard("FormasPago");
    }

    private void editarFormaPago() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una forma de pago para editar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = (String) table.getValueAt(selectedRow, 0);
        FormaPago formaPago = formaPagoService.findByTipo(tipo);
        // TODO: Implement edit functionality
        mainFrame.showCard("FormasPago");
    }

    private void eliminarFormaPago() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una forma de pago para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar esta forma de pago?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String tipo = (String) table.getValueAt(selectedRow, 0);
            FormaPago formaPago = formaPagoService.findByTipo(tipo);
            formaPagoService.delete(formaPago);
            cargarFormasPago();
        }
    }
}