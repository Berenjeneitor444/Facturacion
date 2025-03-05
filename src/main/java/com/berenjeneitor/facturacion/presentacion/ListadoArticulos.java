package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListadoArticulos extends JPanel {
    private final ArticulosService articulosService;
    private final MainFrame mainFrame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtBuscar;

    public ListadoArticulos(ArticulosService articulosService, MainFrame mainFrame) {
        this.articulosService = articulosService;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarArticulos());

        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);

        // Table
        String[] columnNames = {"Código", "Descripción", "Familia", "PVP", "Stock"};
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

        btnNuevo.addActionListener(e -> nuevoArticulo());
        btnEditar.addActionListener(e -> editarArticulo());
        btnEliminar.addActionListener(e -> eliminarArticulo());

        buttonsPanel.add(btnNuevo);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnEliminar);

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load initial data
        cargarArticulos();
    }

    private void cargarArticulos() {
        tableModel.setRowCount(0);
        List<Articulos> articulos = articulosService.findAll();
        for (Articulos articulo : articulos) {
            Object[] row = {
                articulo.getCodigo(),
                articulo.getDescripcion(),
                articulo.getFamilia().getDenominacion(),
                articulo.getPvp(),
                articulo.getStock()
            };
            tableModel.addRow(row);
        }
    }

    private void buscarArticulos() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        tableModel.setRowCount(0);

        List<Articulos> articulos = articulosService.findAll();
        for (Articulos articulo : articulos) {
            if (articulo.getDescripcion().toLowerCase().contains(busqueda) ||
                articulo.getCodigo().toLowerCase().contains(busqueda)) {
                Object[] row = {
                    articulo.getCodigo(),
                    articulo.getDescripcion(),
                    articulo.getFamilia().getDenominacion(),
                    articulo.getPvp(),
                    articulo.getStock()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void nuevoArticulo() {
        mainFrame.showCard("Articulos");
    }

    private void editarArticulo() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un artículo para editar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigo = (String) table.getValueAt(selectedRow, 0);
        Articulos articulo = articulosService.findByCodigo(codigo);
        // TODO: Implement edit functionality
        mainFrame.showCard("Articulos");
    }

    private void eliminarArticulo() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un artículo para eliminar",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar este artículo?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String codigo = (String) table.getValueAt(selectedRow, 0);
            Articulos articulo = articulosService.findByCodigo(codigo);
            articulosService.delete(articulo);
            cargarArticulos();
        }
    }
}