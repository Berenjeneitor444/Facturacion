package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListadoClientes extends JPanel {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBusqueda;
    private JButton btnBuscar, btnNuevo, btnEditar, btnEliminar;
    
    private final ClientesService clientesService;
    private MainFrame mainFrame;

    public ListadoClientes(ClientesService clientesService, MainFrame mainFrame) {
        this.clientesService = clientesService;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Listado de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBusqueda = new JTextField(30);
        panelBusqueda.add(txtBusqueda);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.SOUTH);
        
        // Panel de tabla
        String[] columnas = {"ID", "CIF", "Nombre", "Teléfono", "Email"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar la columna ID
        tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.EAST);
        
        // Configurar eventos
        btnBuscar.addActionListener(e -> buscarClientes());
        btnNuevo.addActionListener(e -> nuevoCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarCliente();
                }
            }
        });
        
        // Cargar datos iniciales
        cargarClientes();
    }
    
    private void cargarClientes() {
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Obtener clientes y cargar en tabla
        List<Clientes> clientes = clientesService.findAll();
        for (Clientes cliente : clientes) {
            Object[] fila = {
                cliente.getId(),
                cliente.getCif(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getEmail()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void buscarClientes() {
        String termino = txtBusqueda.getText().trim();
        if (termino.isEmpty()) {
            cargarClientes();
            return;
        }
        
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Buscar clientes por término
        List<Clientes> clientes = clientesService.buscarPorTermino(termino);
        for (Clientes cliente : clientes) {
            Object[] fila = {
                cliente.getId(),
                cliente.getCif(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getEmail()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void nuevoCliente() {
        mainFrame.showCard("Clientes");
    }
    
    private void editarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idCliente = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        // Aquí iría la lógica para cargar el cliente en el formulario
        mainFrame.showCard("Clientes");
        // Y luego cargar los datos del cliente en el formulario
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idCliente = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar el cliente '" + nombreCliente + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                clientesService.findById(idCliente).ifPresent(cliente -> {
                    clientesService.delete(cliente);
                    cargarClientes();
                    JOptionPane.showMessageDialog(this,
                        "Cliente eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}