package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FacturasService;
import com.berenjeneitor.facturacion.persistencia.entidades.Facturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ListadoFacturas extends JPanel {
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JTextField txtBusqueda;
    private JButton btnBuscar, btnNueva, btnEditar, btnEliminar, btnImprimir;
    
    private final FacturasService facturasService;
    private MainFrame mainFrame;
    private NumberFormat currencyFormat;
    private DateTimeFormatter dateFormatter;

    public ListadoFacturas(FacturasService facturasService, MainFrame mainFrame) {
        this.facturasService = facturasService;
        this.mainFrame = mainFrame;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Listado de Facturas");
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
        String[] columnas = {"ID", "Número", "Fecha", "Cliente", "Total", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaFacturas = new JTable(modeloTabla);
        tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaFacturas.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar la columna ID
        tablaFacturas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaFacturas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaFacturas.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        btnNueva = new JButton("Nueva");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnImprimir = new JButton("Imprimir");
        
        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnImprimir);
        add(panelBotones, BorderLayout.EAST);
        
        // Configurar eventos
        btnBuscar.addActionListener(e -> buscarFacturas());
        btnNueva.addActionListener(e -> nuevaFactura());
        btnEditar.addActionListener(e -> editarFactura());
        btnEliminar.addActionListener(e -> eliminarFactura());
        btnImprimir.addActionListener(e -> imprimirFactura());
        
        tablaFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarFactura();
                }
            }
        });
        
        // Cargar datos iniciales
        cargarFacturas();
    }
    
    private void cargarFacturas() {
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Obtener facturas y cargar en tabla
        List<Facturas> facturas = facturasService.findAll();
        for (Facturas factura : facturas) {
            Object[] fila = {
                factura.getId(),
                factura.getNumero(),
                factura.getFecha().format(dateFormatter),
                factura.getCliente().getNombre(),
                currencyFormat.format(factura.getTotal()),
                factura.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void buscarFacturas() {
        String termino = txtBusqueda.getText().trim();
        if (termino.isEmpty()) {
            cargarFacturas();
            return;
        }
        
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Buscar facturas por término
        List<Facturas> facturas = facturasService.buscarPorTermino(termino);
        for (Facturas factura : facturas) {
            Object[] fila = {
                factura.getId(),
                factura.getNumero(),
                factura.getFecha().format(dateFormatter),
                factura.getCliente().getNombre(),
                currencyFormat.format(factura.getTotal()),
                factura.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void nuevaFactura() {
        mainFrame.showCard("Facturas");
    }
    
    private void editarFactura() {
        int filaSeleccionada = tablaFacturas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idFactura = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        // Aquí iría la lógica para cargar la factura en el formulario
        mainFrame.showCard("Facturas");
        // Y luego cargar los datos de la factura en el formulario
    }
    
    private void eliminarFactura() {
        int filaSeleccionada = tablaFacturas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idFactura = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String numeroFactura = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar la factura '" + numeroFactura + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                facturasService.findById(idFactura).ifPresent(factura -> {
                    facturasService.delete(factura);
                    cargarFacturas();
                    JOptionPane.showMessageDialog(this,
                        "Factura eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar la factura: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void imprimirFactura() {
        int filaSeleccionada = tablaFacturas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para imprimir",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idFactura = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        
        // Aquí iría la lógica para imprimir la factura
        JOptionPane.showMessageDialog(this,
            "Funcionalidad de impresión no implementada",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
}