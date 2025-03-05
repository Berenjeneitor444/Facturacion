package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.negocio.FamiliaArticulosService;
import com.berenjeneitor.facturacion.negocio.TiposIVAService;
import com.berenjeneitor.facturacion.negocio.ValidationException;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FormularioArticulos extends FormularioBase {
    private JTextField txtCodigo, txtDescripcion;
    private JComboBox<FamiliaArticulos> cmbFamilia;
    private JComboBox<TiposIVA> cmbTipoIVA;
    private JFormattedTextField txtPrecio, txtCoste, txtStock;
    private JTextArea txtObservaciones;
    private NumberFormat currencyFormat;
    private NumberFormat numberFormat;
    
    private final ArticulosService articulosService;
    private final FamiliaArticulosService familiaArticulosService;
    private final TiposIVAService tiposIVAService;
    
    private Articulos articuloActual;

    public FormularioArticulos(ArticulosService articulosService, 
                              FamiliaArticulosService familiaArticulosService,
                              TiposIVAService tiposIVAService) {
        super("Formulario de Artículo");
        this.articulosService = articulosService;
        this.familiaArticulosService = familiaArticulosService;
        this.tiposIVAService = tiposIVAService;
        
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        numberFormat = NumberFormat.getNumberInstance(new Locale("es", "ES"));
        
        articuloActual = new Articulos();
    }

    @Override
    protected void construirFormulario() {
        panel.add(new JLabel("Código:*"));
        txtCodigo = createTextField(20);
        panel.add(txtCodigo);

        panel.add(new JLabel("Descripción:*"));
        txtDescripcion = createTextField(100);
        panel.add(txtDescripcion);

        panel.add(new JLabel("Familia:*"));
        cmbFamilia = new JComboBox<>();
        cargarFamilias();
        panel.add(cmbFamilia);

        panel.add(new JLabel("Tipo IVA:*"));
        cmbTipoIVA = new JComboBox<>();
        cargarTiposIVA();
        panel.add(cmbTipoIVA);

        panel.add(new JLabel("Coste:*"));
        txtCoste = new JFormattedTextField(currencyFormat);
        txtCoste.setValue(0.0);
        txtCoste.setColumns(10);
        panel.add(txtCoste);

        panel.add(new JLabel("Precio Venta:*"));
        txtPrecio = new JFormattedTextField(currencyFormat);
        txtPrecio.setValue(0.0);
        txtPrecio.setColumns(10);
        panel.add(txtPrecio);

        panel.add(new JLabel("Stock:"));
        txtStock = new JFormattedTextField(numberFormat);
        txtStock.setValue(0);
        txtStock.setColumns(10);
        panel.add(txtStock);

        panel.add(new JLabel("Observaciones:"));
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservaciones);
        panel.add(scrollPane);
    }
    
    private void cargarFamilias() {
        cmbFamilia.removeAllItems();
        List<FamiliaArticulos> familias = familiaArticulosService.findAll();
        for (FamiliaArticulos familia : familias) {
            cmbFamilia.addItem(familia);
        }
    }
    
    private void cargarTiposIVA() {
        cmbTipoIVA.removeAllItems();
        List<TiposIVA> tiposIVA = tiposIVAService.findAll();
        for (TiposIVA tipoIVA : tiposIVA) {
            cmbTipoIVA.addItem(tipoIVA);
        }
    }

    @Override
    protected void guardar() {
        try {
            // Recoger datos
            String codigo = txtCodigo.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            FamiliaArticulos familia = (FamiliaArticulos) cmbFamilia.getSelectedItem();
            TiposIVA tipoIVA = (TiposIVA) cmbTipoIVA.getSelectedItem();
            BigDecimal coste = BigDecimal.valueOf(((Number)txtCoste.getValue()).doubleValue());
            BigDecimal precio = BigDecimal.valueOf(((Number)txtPrecio.getValue()).doubleValue());
            BigDecimal stock = BigDecimal.valueOf(((Number)txtStock.getValue()).doubleValue());
            String observaciones = txtObservaciones.getText();

            // Validar datos básicos antes de crear el objeto
            if (codigo.isEmpty() || descripcion.isEmpty() || familia == null || tipoIVA == null) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos marcados con * son obligatorios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear o actualizar el objeto
            articuloActual.setCodigo(codigo);
            articuloActual.setDescripcion(descripcion);
            articuloActual.setFamilia(familia);
            articuloActual.setTipoIVA(tipoIVA);
            articuloActual.setCoste(coste);
            articuloActual.setPvp(precio);
            articuloActual.setStock(stock);
            articuloActual.setObservaciones(observaciones);

            // Guardar usando el servicio
            articulosService.saveOrUpdate(articuloActual);

            JOptionPane.showMessageDialog(this, 
                "Artículo guardado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            articuloActual = new Articulos();
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el artículo: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    protected void limpiarCampos() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        if (cmbFamilia.getItemCount() > 0) {
            cmbFamilia.setSelectedIndex(0);
        }
        if (cmbTipoIVA.getItemCount() > 0) {
            cmbTipoIVA.setSelectedIndex(0);
        }
        txtCoste.setValue(0.0);
        txtPrecio.setValue(0.0);
        txtStock.setValue(0);
        txtObservaciones.setText("");
    }
}