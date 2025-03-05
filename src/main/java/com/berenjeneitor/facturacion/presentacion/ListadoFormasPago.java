package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.FormaPagoService;
import com.berenjeneitor.facturacion.persistencia.entidades.FormaPago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListadoFormasPago extends ListadoBase {
    private final FormaPagoService formaPagoService;

    public ListadoFormasPago(FormaPagoService formaPagoService, MainFrame mainFrame) {
        super("Listado de Formas de Pago", mainFrame);
        this.formaPagoService = formaPagoService;
    }

    @Override
    protected void inicializarTabla() {
        String[] columnas = {"ID", "Tipo", "Observaciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
    }

    @Override
    protected void cargarDatos() {
        modeloTabla.setRowCount(0);
        try {
            List<FormaPago> formasPago = formaPagoService.findAll();
            for (FormaPago formaPago : formasPago) {
                Object[] fila = {
                    formaPago.getId(),
                    formaPago.getTipo(),
                    formaPago.getObservaciones()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar las formas de pago: " + e.getMessage());
        }
    }

    @Override
    protected void buscar() {
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);
        try {
            List<FormaPago> formasPago = formaPagoService.findAll();
            for (FormaPago formaPago : formasPago) {
                if (formaPago.getTipo().toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        formaPago.getId(),
                        formaPago.getTipo(),
                        formaPago.getObservaciones()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar formas de pago: " + e.getMessage());
        }
    }

    @Override
    protected void nuevo() {
        mainFrame.showCard("FormasPago");
    }

    @Override
    protected void editar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            // Aquí iría la lógica para cargar el formulario de edición
            mainFrame.showCard("FormasPago");
        } else {
            mostrarError("Por favor, seleccione una forma de pago para editar");
        }
    }

    @Override
    protected void eliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Long id = (Long) tabla.getValueAt(filaSeleccionada, 0);
            String tipo = (String) tabla.getValueAt(filaSeleccionada, 1);
            
            if (confirmarEliminacion("la forma de pago '" + tipo + "'")) {
                try {
                    formaPagoService.delete(id);
                    mostrarConfirmacion("Forma de pago eliminada correctamente");
                    cargarDatos();
                } catch (Exception e) {
                    mostrarError("Error al eliminar la forma de pago: " + e.getMessage());
                }
            }
        } else {
            mostrarError("Por favor, seleccione una forma de pago para eliminar");
        }
    }
}