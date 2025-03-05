package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ClientesService;
import com.berenjeneitor.facturacion.negocio.RectificativasClientesService;
import com.berenjeneitor.facturacion.negocio.ValidationException;
import com.berenjeneitor.facturacion.persistencia.entidades.Cliente;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativaCliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FormularioRectificativas extends FormularioBase {
    private JTextField txtNumero, txtFecha, txtBaseImponible, txtIva, txtTotal;
    private JComboBox<Cliente> cmbCliente;
    private JTextArea txtObservaciones;
    private JTextField txtFacturaOriginal;
    private JButton btnBuscarFactura;
    
    private final RectificativasClientesService rectificativasService;
    private final ClientesService clientesService;
    
    public FormularioRectificativas(RectificativasClientesService rectificativasService, ClientesService clientesService) {
        super("Formulario de Factura Rectificativa");
        this.rectificativasService = rectificativasService;
        this.clientesService = clientesService;
    }
    
    @Override
    protected void construirFormulario() {
        // Panel de datos básicos
        JPanel panelDatosBasicos = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDatosBasicos.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
        
        panelDatosBasicos.add(new JLabel("Número:*"));
        txtNumero = createTextField(20);
        panelDatosBasicos.add(txtNumero);
        
        panelDatosBasicos.add(new JLabel("Fecha:*"));
        txtFecha = createTextField(10);
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelDatosBasicos.add(txtFecha);
        
        panelDatosBasicos.add(new JLabel("Cliente:*"));
        cmbCliente = new JComboBox<>();
        cargarClientes();
        panelDatosBasicos.add(cmbCliente);
        
        panelDatosBasicos.add(new JLabel("Factura Original:*"));
        JPanel panelFacturaOriginal = new JPanel(new BorderLayout());
        txtFacturaOriginal = createTextField(20);
        btnBuscarFactura = new JButton("Buscar");
        panelFacturaOriginal.add(txtFacturaOriginal, BorderLayout.CENTER);
        panelFacturaOriginal.add(btnBuscarFactura, BorderLayout.EAST);
        panelDatosBasicos.add(panelFacturaOriginal);
        
        // Panel de importes
        JPanel panelImportes = new JPanel(new GridLayout(0, 2, 5, 5));
        panelImportes.setBorder(BorderFactory.createTitledBorder("Importes"));
        
        panelImportes.add(new JLabel("Base Imponible:*"));
        txtBaseImponible = createTextField(15);
        panelImportes.add(txtBaseImponible);
        
        panelImportes.add(new JLabel("IVA:*"));
        txtIva = createTextField(15);
        panelImportes.add(txtIva);
        
        panelImportes.add(new JLabel("Total:*"));
        txtTotal = createTextField(15);
        panelImportes.add(txtTotal);
        
        // Panel de observaciones
        JPanel panelObservaciones = new JPanel(new BorderLayout());
        panelObservaciones.setBorder(BorderFactory.createTitledBorder("Observaciones"));
        
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservaciones);
        panelObservaciones.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar todos los paneles al panel principal
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(panelDatosBasicos);
        panel.add(panelImportes);
        panel.add(panelObservaciones);
        
        // Configurar eventos adicionales
        btnBuscarFactura.addActionListener(e -> buscarFacturaOriginal());
    }
    
    private void cargarClientes() {
        try {
            List<Cliente> clientes = clientesService.findAll();
            for (Cliente cliente : clientes) {
                cmbCliente.addItem(cliente);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar los clientes: " + e.getMessage(), null);
        }
    }
    
    private void buscarFacturaOriginal() {
        // Aquí iría la lógica para buscar y seleccionar una factura original
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de búsqueda de facturas pendiente de implementar", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    protected void guardar() {
        try {
            // Validar campos obligatorios
            if (txtNumero.getText().trim().isEmpty()) {
                mostrarError("El número de factura es obligatorio", txtNumero);
                return;
            }
            
            if (txtFecha.getText().trim().isEmpty()) {
                mostrarError("La fecha es obligatoria", txtFecha);
                return;
            }
            
            if (cmbCliente.getSelectedItem() == null) {
                mostrarError("Debe seleccionar un cliente", cmbCliente);
                return;
            }
            
            if (txtFacturaOriginal.getText().trim().isEmpty()) {
                mostrarError("Debe indicar la factura original", txtFacturaOriginal);
                return;
            }
            
            if (txtBaseImponible.getText().trim().isEmpty()) {
                mostrarError("La base imponible es obligatoria", txtBaseImponible);
                return;
            }
            
            if (txtIva.getText().trim().isEmpty()) {
                mostrarError("El IVA es obligatorio", txtIva);
                return;
            }
            
            if (txtTotal.getText().trim().isEmpty()) {
                mostrarError("El total es obligatorio", txtTotal);
                return;
            }
            
            // Crear objeto RectificativaCliente
            RectificativaCliente rectificativa = new RectificativaCliente();
            rectificativa.setNumero(txtNumero.getText().trim());
            
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), 
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                rectificativa.setFecha(fecha);
            } catch (DateTimeParseException e) {
                mostrarError("Formato de fecha incorrecto. Use dd/MM/yyyy", txtFecha);
                return;
            }
            
            rectificativa.setCliente((Cliente) cmbCliente.getSelectedItem());
            rectificativa.setFacturaOriginal(txtFacturaOriginal.getText().trim());
            
            try {
                rectificativa.setBaseImponible(Double.parseDouble(txtBaseImponible.getText().trim()));
                rectificativa.setIva(Double.parseDouble(txtIva.getText().trim()));
                rectificativa.setTotal(Double.parseDouble(txtTotal.getText().trim()));
            } catch (NumberFormatException e) {
                mostrarError("Los importes deben ser valores numéricos", null);
                return;
            }
            
            rectificativa.setObservaciones(txtObservaciones.getText());
            
            // Guardar la rectificativa
            rectificativasService.save(rectificativa);
            
            JOptionPane.showMessageDialog(this, 
                "Factura rectificativa guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            
        } catch (ValidationException e) {
            mostrarError("Error de validación: " + e.getMessage(), null);
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage(), null);
        }
    }
    
    @Override
    protected void limpiarCampos() {
        txtNumero.setText("");
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cmbCliente.setSelectedIndex(-1);
        txtFacturaOriginal.setText("");
        txtBaseImponible.setText("");
        txtIva.setText("");
        txtTotal.setText("");
        txtObservaciones.setText("");
        txtNumero.requestFocus();
    }
}