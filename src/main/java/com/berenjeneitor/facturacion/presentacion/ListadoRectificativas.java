package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.RectificativasClientesService;
import com.berenjeneitor.facturacion.persistencia.entidades.RectificativaCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListadoRectificativas extends ListadoBase {
    private final RectificativasClientesService rectificativasService;

    public ListadoRectificativas(RectificativasClientesService rectificativasService, MainFrame mainFrame) {
        super("Listado de Facturas Rectificativas", mainFrame);
        this.rectificativasService = rectificativasService;
    }

    @Override
    protected void inicializarTabla() {
        String[] columnas = {"ID", "Número", "Fecha", "Cliente", "Base Imponible", "IVA", "Total"};
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
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
    }

    @Override
    protected void cargarDatos() {
        modeloTabla.setRowCount(0);
        try {
            List<RectificativaCliente> rectificativas = rectificativasService.findAll();
            for (RectificativaCliente rectificativa : rectificativas) {
                Object[] fila = {
                    rectificativa.getId(),
                    rectificativa.getNumero(),
                    rectificativa.getFecha(),
                    rectificativa.getCliente().getNombre(),
                    rectificativa.getBaseImponible(),
                    rectificativa.getIva(),
                    rectificativa.getTotal()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar las facturas rectificativas: " + e.getMessage());
        }
    }

    @Override
    protected void buscar() {
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);
        try {
            List<RectificativaCliente> rectificativas = rectificativasService.findAll();
            for (RectificativaCliente rectificativa : rectificativas) {
                if (rectificativa.getNumero().toLowerCase().contains(textoBusqueda) ||
                    rectificativa.getCliente().getNombre().toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        rectificativa.getId(),
                        rectificativa.getNumero(),
                        rectificativa.getFecha(),
                        rectificativa.getCliente().getNombre(),
                        rectificativa.getBaseImponible(),
                        rectificativa.getIva(),
                        rectificativa.getTotal()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar facturas rectificativas: " + e.getMessage());
        }
    }

    @Override
    protected void nuevo() {
        mainFrame.showCard("CrearRectificativa");
    }

    @Override
    protected void editar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            // Aquí iría la lógica para cargar el formulario de edición
            mainFrame.showCard("CrearRectificativa");
        } else {
            mostrarError("Por favor, seleccione una factura rectificativa para editar");
        }
    }

    @Override
    protected void eliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            String numero = (String) tabla.getValueAt(filaSeleccionada, 1);
            
            if (confirmarEliminacion("la factura rectificativa '" + numero + "'")) {
                try {
                    rectificativasService.delete(id);
                    mostrarConfirmacion("Factura rectificativa eliminada correctamente");
                    cargarDatos();
                } catch (Exception e) {
                    mostrarError("Error al eliminar la factura rectificativa: " + e.getMessage());
                }
            }
        } else {
            mostrarError("Por favor, seleccione una factura rectificativa para eliminar");
        }
    }
}