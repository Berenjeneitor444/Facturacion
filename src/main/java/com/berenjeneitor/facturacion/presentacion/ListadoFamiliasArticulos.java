package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FamiliaArticulosService;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListadoFamiliasArticulos extends ListadoBase {
    private final FamiliaArticulosService familiaArticulosService;

    public ListadoFamiliasArticulos(FamiliaArticulosService familiaArticulosService, MainFrame mainFrame) {
        super("Listado de Familias de Artículos", mainFrame);
        this.familiaArticulosService = familiaArticulosService;
    }

    @Override
    protected void inicializarTabla() {
        String[] columnas = {"ID", "Código", "Denominación", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
    }

    @Override
    protected void cargarDatos() {
        modeloTabla.setRowCount(0);
        try {
            List<FamiliaArticulos> familias = familiaArticulosService.findAll();
            for (FamiliaArticulos familia : familias) {
                Object[] fila = {
                    familia.getId(),
                    familia.getCodigo(),
                    familia.getDenominacion(),
                    familia.getDescripcion()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar las familias de artículos: " + e.getMessage());
        }
    }

    @Override
    protected void buscar() {
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);
        try {
            List<FamiliaArticulos> familias = familiaArticulosService.findAll();
            for (FamiliaArticulos familia : familias) {
                if (familia.getCodigo().toLowerCase().contains(textoBusqueda) ||
                    familia.getDenominacion().toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        familia.getId(),
                        familia.getCodigo(),
                        familia.getDenominacion(),
                        familia.getDescripcion()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar familias de artículos: " + e.getMessage());
        }
    }

    @Override
    protected void nuevo() {
        mainFrame.showCard("FamiliaArticulos");
    }

    @Override
    protected void editar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            // Aquí iría la lógica para cargar el formulario de edición
            mainFrame.showCard("FamiliaArticulos");
        } else {
            mostrarError("Por favor, seleccione una familia de artículos para editar");
        }
    }

    @Override
    protected void eliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            String denominacion = (String) tabla.getValueAt(filaSeleccionada, 2);
            
            if (confirmarEliminacion("la familia de artículos '" + denominacion + "'")) {
                try {
                    familiaArticulosService.delete(id);
                    mostrarConfirmacion("Familia de artículos eliminada correctamente");
                    cargarDatos();
                } catch (Exception e) {
                    mostrarError("Error al eliminar la familia de artículos: " + e.getMessage());
                }
            }
        } else {
            mostrarError("Por favor, seleccione una familia de artículos para eliminar");
        }
    }
}