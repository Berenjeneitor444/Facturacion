package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListadoArticulos extends ListadoBase {
    private JComboBox<String> cmbFiltroFamilia;
    private final ArticulosService articulosService;
    private NumberFormat currencyFormat;

    public ListadoArticulos(ArticulosService articulosService, MainFrame mainFrame) {
        super("Listado de Artículos", mainFrame);
        this.articulosService = articulosService;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        
        // Agregar filtro de familia
        JPanel panelBusqueda = (JPanel) getComponent(2); // El panel de búsqueda está en el SOUTH
        panelBusqueda.add(new JLabel("Familia:"));
        cmbFiltroFamilia = new JComboBox<>(new String[]{"Todas"});
        panelBusqueda.add(cmbFiltroFamilia);
        
        // Cargar datos iniciales
        cargarFamilias();
    }
    
    private void cargarFamilias() {
        // Aquí se cargarían las familias desde el servicio
        // Por ahora solo tenemos "Todas" como ejemplo
    }
    
    @Override
    protected void inicializarTabla() {
        String[] columnas = {"ID", "Código", "Descripción", "Familia", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        // Ocultar la columna ID
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);
    }

    @Override
    protected void cargarDatos() {
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Obtener artículos y cargar en tabla
        List<Articulos> articulos = articulosService.findAll();
        for (Articulos articulo : articulos) {
            Object[] fila = {
                articulo.getId(),
                articulo.getCodigo(),
                articulo.getDescripcion(),
                articulo.getFamilia() != null ? articulo.getFamilia().getDenominacion() : "",
                currencyFormat.format(articulo.getPvp()),
                articulo.getStock()
            };
            modeloTabla.addRow(fila);
        }
    
    @Override
    protected void buscar() {
        String termino = txtBusqueda.getText().trim();
        String familia = cmbFiltroFamilia.getSelectedItem().toString();
        
        // Limpiar tabla
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
        
        // Buscar artículos según filtros
        List<Articulos> articulos;
        if (termino.isEmpty() && "Todas".equals(familia)) {
            articulos = articulosService.findAll();
        } else {
            articulos = articulosService.buscarPorFiltros(termino, familia);
        }
        
        for (Articulos articulo : articulos) {
            Object[] fila = {
                articulo.getId(),
                articulo.getCodigo(),
                articulo.getDescripcion(),
                articulo.getFamilia() != null ? articulo.getFamilia().getDenominacion() : "",
                currencyFormat.format(articulo.getPvp()),
                articulo.getStock()
            };
            modeloTabla.addRow(fila);
        }
    
    @Override
    protected void nuevo() {
        mainFrame.showCard("Articulos");
    }
    
    @Override
    protected void editar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un artículo para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idArticulo = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        // Aquí iría la lógica para cargar el artículo en el formulario
        mainFrame.showCard("Articulos");
        // Y luego cargar los datos del artículo en el formulario
    }
    
    @Override
    protected void eliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un artículo para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idArticulo = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String codigoArticulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String descripcionArticulo = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar el artículo '" + codigoArticulo + " - " + descripcionArticulo + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                articulosService.findById(idArticulo).ifPresent(articulo -> {
                    articulosService.delete(articulo);
                    cargarDatos();
                    JOptionPane.showMessageDialog(this,
                        "Artículo eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el artículo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}