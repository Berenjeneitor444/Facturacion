package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListadoClientes extends JPanel {
    private final ClientesService clientesService;
    private final MainFrame mainFrame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtBuscar;

    public ListadoClientes(ClientesService clientesService, MainFrame mainFrame) {
        this.clientesService = clientesService;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarClientes());

        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);

        // Table
        String[] columnNames = {"CIF", "Nombre", "Teléfono", "Email", "Población"};
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

        btnNuevo.addActionListener(e -> nuevoCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        buttonsPanel.add(btnNuevo);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnEliminar);

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load initial data
        cargarClientes();
    }

    private void cargarClientes() {
        tableModel.setRowCount(0);
        List<Clientes> clientes = clientesService.findAll();
        for (Clientes cliente : clientes) {
            Object[] row = {
                cliente.getCif(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getPoblacion()
            };
            tableModel.addRow(row);
        }
    }

    private void buscarClientes() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        tableModel.setRowCount(0);

        List<Clientes> clientes = clientesService.findAll();
        for (Clientes cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(busqueda) ||
                cliente.getCif().toLowerCase().contains(busqueda)) {
                Object[] row = {
                    cliente.getCif(),
                    cliente.getNombre(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.getPoblacion()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void nuevoCliente() {
        mainFrame.showCard("Clientes");
    }

    private void editarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente para editar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cif = (String) table.getValueAt(selectedRow, 0);
        Clientes cliente = clientesService.findByCif(cif);
        // TODO: Implement edit functionality
        mainFrame.showCard("Clientes");
    }

    private void eliminarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar este cliente?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String cif = (String) table.getValueAt(selectedRow, 0);
            Clientes cliente = clientesService.findByCif(cif);
            clientesService.delete(cliente);
            cargarClientes();
        }
    }
}