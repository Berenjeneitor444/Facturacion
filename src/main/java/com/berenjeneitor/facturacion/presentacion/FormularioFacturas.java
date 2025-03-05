package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.negocio.FacturasService;
import com.berenjeneitor.facturacion.negocio.FormaPagoService;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;
import com.berenjeneitor.facturacion.persistencia.entidades.Facturas;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;
import com.berenjeneitor.facturacion.persistencia.entidades.LineasFactura;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormularioFacturas extends FormularioBase {
    private JTextField txtNumero, txtFecha;
    private JComboBox<Clientes> cmbCliente;
    private JComboBox<FormaPago> cmbFormaPago;
    private JTable tablaLineas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarLinea, btnEliminarLinea;
    private JLabel lblSubtotal, lblIVA, lblTotal;
    
    private final FacturasService facturasService;
    private final ClientesService clientesService;
    private final ArticulosService articulosService;
    private final FormaPagoService formaPagoService;
    
    private Facturas facturaActual;
    private List<LineasFactura> lineasFactura;
    private NumberFormat currencyFormat;
    private DateTimeFormatter dateFormatter;

    public FormularioFacturas(FacturasService facturasService, 
                             ClientesService clientesService,
                             ArticulosService articulosService,
                             FormaPagoService formaPagoService) {
        super("Formulario de Factura");
        this.facturasService = facturasService;
        this.clientesService = clientesService;
        this.articulosService = articulosService;
        this.formaPagoService = formaPagoService;
        
        facturaActual = new Facturas();
        lineasFactura = new ArrayList<>();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Override
    protected void construirFormulario() {
        // Panel de cabecera
        JPanel panelCabecera = new JPanel(new GridLayout(0, 2, 5, 5));
        panelCabecera.setBorder(BorderFactory.createTitledBorder("Datos de Factura"));
        
        panelCabecera.add(new JLabel("Número:*"));
        txtNumero = createTextField(20);
        txtNumero.setEditable(false); // Se genera automáticamente
        panelCabecera.add(txtNumero);
        
        panelCabecera.add(new JLabel("Fecha:*"));
        txtFecha = createTextField(10);
        txtFecha.setText(LocalDate.now().format(dateFormatter));
        panelCabecera.add(txtFecha);
        
        panelCabecera.add(new JLabel("Cliente:*"));
        cmbCliente = new JComboBox<>();
        cargarClientes();
        panelCabecera.add(cmbCliente);
        
        panelCabecera.add(new JLabel("Forma de Pago:*"));
        cmbFormaPago = new JComboBox<>();
        cargarFormasPago();
        panelCabecera.add(cmbFormaPago);
        
        // Panel de líneas de factura
        JPanel panelLineas = new JPanel(new BorderLayout(5, 5));
        panelLineas.setBorder(BorderFactory.createTitledBorder("Líneas de Factura"));
        
        String[] columnas = {"ID", "Código", "Descripción", "Cantidad", "Precio", "IVA", "Importe"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la cantidad es editable
            }
        };
        
        tablaLineas = new JTable(modeloTabla);
        tablaLineas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLineas.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar la columna ID
        tablaLineas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaLineas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaLineas.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tablaLineas);
        panelLineas.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotonesLineas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAgregarLinea = new JButton("Agregar Artículo");
        btnEliminarLinea = new JButton("Eliminar Línea");
        
        panelBotonesLineas.add(btnAgregarLinea);
        panelBotonesLineas.add(btnEliminarLinea);
        panelLineas.add(panelBotonesLineas, BorderLayout.SOUTH);
        
        // Panel de totales
        JPanel panelTotales = new JPanel(new GridLayout(0, 2, 5, 5));
        panelTotales.setBorder(BorderFactory.createTitledBorder("Totales"));
        
        panelTotales.add(new JLabel("Subtotal:"));
        lblSubtotal = new JLabel(currencyFormat.format(0));
        lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        panelTotales.add(lblSubtotal);
        
        panelTotales.add(new JLabel("IVA:"));
        lblIVA = new JLabel(currencyFormat.format(0));
        lblIVA.setHorizontalAlignment(SwingConstants.RIGHT);
        panelTotales.add(lblIVA);
        
        panelTotales.add(new JLabel("Total:"));
        lblTotal = new JLabel(currencyFormat.format(0));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setFont(new Font(lblTotal.getFont().getName(), Font.BOLD, 14));
        panelTotales.add(lblTotal);
        
        // Configurar eventos
        btnAgregarLinea.addActionListener(e -> agregarLinea());
        btnEliminarLinea.addActionListener(e -> eliminarLinea());
        
        // Agregar todos los paneles al panel principal
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(panelCabecera);
        panel.add(panelLineas);
        panel.add(panelTotales);
    }
    
    private void cargarClientes() {
        cmbCliente.removeAllItems();
        List<Clientes> clientes = clientesService.findAll();
        for (Clientes cliente : clientes) {
            cmbCliente.addItem(cliente);
        }
    }
    
    private void cargarFormasPago() {
        cmbFormaPago.removeAllItems();
        List<FormaPago> formasPago = formaPagoService.findAll();
        for (FormaPago formaPago : formasPago) {
            cmbFormaPago.addItem(formaPago);
        }
    }
    
    private void agregarLinea() {
        // Mostrar diálogo para seleccionar artículo
        JDialog dialogoArticulo = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Seleccionar Artículo", true);
        dialogoArticulo.setLayout(new BorderLayout(10, 10));
        dialogoArticulo.setSize(600, 400);
        dialogoArticulo.setLocationRelativeTo(this);
        
        // Crear tabla de artículos
        String[] columnasArticulos = {"ID", "Código", "Descripción", "Precio", "Stock"};
        DefaultTableModel modeloArticulos = new DefaultTableModel(columnasArticulos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaArticulos = new JTable(modeloArticulos);
        tablaArticulos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ocultar la columna ID
        tablaArticulos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaArticulos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaArticulos.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollArticulos = new JScrollPane(tablaArticulos);
        dialogoArticulo.add(scrollArticulos, BorderLayout.CENTER);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtBusqueda = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnBuscar);
        dialogoArticulo.add(panelBusqueda, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        dialogoArticulo.add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar artículos
        List<Articulos> articulos = articulosService.findAll();
        for (Articulos articulo : articulos) {
            Object[] fila = {
                articulo.getId(),
                articulo.getCodigo(),
                articulo.getDescripcion(),
                currencyFormat.format(articulo.getPvp()),
                articulo.getStock()
            };
            modeloArticulos.addRow(fila);
        }
        
        // Configurar eventos
        btnBuscar.addActionListener(e -> {
            String termino = txtBusqueda.getText().trim();
            if (termino.isEmpty()) {
                // Mostrar todos los artículos
                while (modeloArticulos.getRowCount() > 0) {
                    modeloArticulos.removeRow(0);
                }
                
                for (Articulos articulo : articulos) {
                    Object[] fila = {
                        articulo.getId(),
                        articulo.getCodigo(),
                        articulo.getDescripcion(),
                        currencyFormat.format(articulo.getPvp()),
                        articulo.getStock()
                    };
                    modeloArticulos.addRow(fila);
                }
            } else {
                // Filtrar artículos
                List<Articulos> articulosFiltrados = articulosService.buscarPorTermino(termino);
                
                while (modeloArticulos.getRowCount() > 0) {
                    modeloArticulos.removeRow(0);
                }
                
                for (Articulos articulo : articulosFiltrados) {
                    Object[] fila = {
                        articulo.getId(),
                        articulo.getCodigo(),
                        articulo.getDescripcion(),
                        currencyFormat.format(articulo.getPvp()),
                        articulo.getStock()
                    };
                    modeloArticulos.addRow(fila);
                }
            }
        });
        
        btnAceptar.addActionListener(e -> {
            int filaSeleccionada = tablaArticulos.getSelectedRow();
            if (filaSeleccionada != -1) {
                Integer idArticulo = (Integer) modeloArticulos.getValueAt(filaSeleccionada, 0);
                articulosService.findById(idArticulo).ifPresent(articulo -> {
                    // Mostrar diálogo para cantidad
                    String cantidadStr = JOptionPane.showInputDialog(
                        dialogoArticulo,
                        "Introduzca la cantidad:",
                        "1"
                    );
                    
                    try {
                        BigDecimal cantidad = new BigDecimal(cantidadStr);
                        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                            JOptionPane.showMessageDialog(
                                dialogoArticulo,
                                "La cantidad debe ser mayor que cero",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        
                        // Crear línea de factura
                        LineasFactura linea = new LineasFactura();
                        linea.setArticulo(articulo);
                        linea.setCantidad(cantidad);
                        linea.setPrecio(articulo.getPvp());
                        linea.setIva(articulo.getTipoIVA().getIva());
                        
                        // Agregar a la lista y a la tabla
                        lineasFactura.add(linea);
                        
                        Object[] filaLinea = {
                            articulo.getId(),
                            articulo.getCodigo(),
                            articulo.getDescripcion(),
                            cantidad,
                            currencyFormat.format(articulo.getPvp()),
                            articulo.getTipoIVA().getIva() + "%",
                            currencyFormat.format(cantidad.multiply(articulo.getPvp()))
                        };
                        modeloTabla.addRow(filaLinea);
                        
                        // Actualizar totales
                        actualizarTotales();
                        
                        dialogoArticulo.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                            dialogoArticulo,
                            "Cantidad no válida",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
            } else {
                JOptionPane.showMessageDialog(
                    dialogoArticulo,
                    "Seleccione un artículo",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        btnCancelar.addActionListener(e -> dialogoArticulo.dispose());
        
        dialogoArticulo.setVisible(true);
    }
    
    private void eliminarLinea() {
        int filaSeleccionada = tablaLineas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una línea para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Eliminar de la lista y de la tabla
        lineasFactura.remove(filaSeleccionada);
        modeloTabla.removeRow(filaSeleccionada);
        
        // Actualizar totales
        actualizarTotales();
    }
    
    private void actualizarTotales() {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalIVA = BigDecimal.ZERO;
        
        for (LineasFactura linea : lineasFactura) {
            BigDecimal importe = linea.getPrecio().multiply(linea.getCantidad());
            subtotal = subtotal.add(importe);
            
            BigDecimal iva = importe.multiply(linea.getIva().divide(new BigDecimal("100")));
            totalIVA = totalIVA.add(iva);
        }
        
        BigDecimal total = subtotal.add(totalIVA);
        
        lblSubtotal.setText(currencyFormat.format(subtotal));
        lblIVA.setText(currencyFormat.format(totalIVA));
        lblTotal.setText(currencyFormat.format(total));
    }

    @Override
    protected void guardar() {
        try {
            // Validaciones básicas
            if (cmbCliente.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un cliente", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cmbFormaPago.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar una forma de pago", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (lineasFactura.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La factura debe tener al menos una línea", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parsear fecha
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(txtFecha.getText(), dateFormatter);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de fecha incorrecto. Use dd/MM/yyyy", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear o actualizar factura
            facturaActual.setFecha(fecha);
            facturaActual.setCliente((Clientes) cmbCliente.getSelectedItem());
            facturaActual.setFormaPago((FormaPago) cmbFormaPago.getSelectedItem());
            
            // Calcular totales
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal totalIVA = BigDecimal.ZERO;
            
            for (LineasFactura linea : lineasFactura) {
                BigDecimal importe = linea.getPrecio().multiply(linea.getCantidad());
                subtotal = subtotal.add(importe);
                
                BigDecimal iva = importe.multiply(linea.getIva().divide(new BigDecimal("100")));
                totalIVA = totalIVA.add(iva);
                
                // Asociar línea con factura
                linea.setFactura(facturaActual);
            }
            
            BigDecimal total = subtotal.add(totalIVA);
            
            facturaActual.setSubtotal(subtotal);
            facturaActual.setIva(totalIVA);
            facturaActual.setTotal(total);
            facturaActual.setLineasFactura(lineasFactura);
            
            // Guardar usando el servicio
            facturasService.saveOrUpdate(facturaActual);
            
            JOptionPane.showMessageDialog(this, 
                "Factura guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            facturaActual = new Facturas();
            lineasFactura = new ArrayList<>();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar la factura: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void limpiarCampos() {
        txtNumero.setText("");
        txtFecha.setText(LocalDate.now().format(dateFormatter));
        if (cmbCliente.getItemCount() > 0) {
            cmbCliente.setSelectedIndex(0);
        }
        if (cmbFormaPago.getItemCount() > 0) {
            cmbFormaPago.setSelectedIndex(0);
        }
        
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Limpiar líneas
        lineasFactura.clear();
        
        // Actualizar totales
        actualizarTotales();
    }
}