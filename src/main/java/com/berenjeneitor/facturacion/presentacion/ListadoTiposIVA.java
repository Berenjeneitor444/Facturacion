package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.TiposIVAService;
import com.berenjeneitor.facturacion.persistencia.entidades.TipoIVA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListadoTiposIVA extends ListadoBase {
    private final TiposIVAService tiposIVAService;

    public ListadoTiposIVA(TiposIVAService tiposIVAService, MainFrame mainFrame) {
        super("Listado de Tipos de IVA", mainFrame);
        this.tiposIVAService = tiposIVAService;
    }

    @Override
    protected void inicializarTabla() {
        String[] columnas = {"ID", "Tipo", "Porcentaje", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
    }

    @Override
    protected void cargarDatos() {
        modeloTabla.setRowCount(0);
        try {
            List<TipoIVA> tiposIVA = tiposIVAService.findAll();
            for (TipoIVA tipoIVA : tiposIVA) {
                Object[] fila = {
                    tipoIVA.getId(),
                    tipoIVA.getTipo(),
                    tipoIVA.getPorcentaje(),
                    tipoIVA.getDescripcion()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar los tipos de IVA: " + e.getMessage());
        }
    }

    @Override
    protected void buscar() {
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);
        try {
            List<TipoIVA> tiposIVA = tiposIVAService.findAll();
            for (TipoIVA tipoIVA : tiposIVA) {
                if (tipoIVA.getTipo().toLowerCase().contains(textoBusqueda) ||
                    tipoIVA.getDescripcion().toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        tipoIVA.getId(),
                        tipoIVA.getTipo(),
                        tipoIVA.getPorcentaje(),
                        tipoIVA.getDescripcion()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar tipos de IVA: " + e.getMessage());
        }
    }

    @Override
    protected void nuevo() {
        mainFrame.showCard("TiposIVA");
    }

    @Override
    protected void editar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            // Aquí iría la lógica para cargar el formulario de edición
            mainFrame.showCard("TiposIVA");
        } else {
            mostrarError("Por favor, seleccione un tipo de IVA para editar");
        }
    }

    @Override
    protected void eliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            String tipo = (String) tabla.getValueAt(filaSeleccionada, 1);
            
            if (confirmarEliminacion("el tipo de IVA '" + tipo + "'")) {
                try {
                    tiposIVAService.delete(id);
                    mostrarConfirmacion("Tipo de IVA eliminado correctamente");
                    cargarDatos();
                } catch (Exception e) {
                    mostrarError("Error al eliminar el tipo de IVA: " + e.getMessage());
                }
            }
        } else {
            mostrarError("Por favor, seleccione un tipo de IVA para eliminar");
        }
    }
}