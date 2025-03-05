package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class ListadoBase extends JPanel {
    protected JTable tabla;
    protected DefaultTableModel modeloTabla;
    protected JTextField txtBusqueda;
    protected JButton btnBuscar, btnNuevo, btnEditar, btnEliminar;
    protected MainFrame mainFrame;
    protected String titulo;

    public ListadoBase(String titulo, MainFrame mainFrame) {
        this.titulo = titulo;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBusqueda = new JTextField(20);
        panelBusqueda.add(txtBusqueda);
        
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.SOUTH);
        
        // Panel de tabla
        inicializarTabla();
        JScrollPane scrollPane = new JScrollPane(tabla);
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
        configurarEventos();
        
        // Cargar datos iniciales
        cargarDatos();
    }

    protected void configurarEventos() {
        btnBuscar.addActionListener(e -> buscar());
        btnNuevo.addActionListener(e -> nuevo());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editar();
                }
            }
        });
    }

    protected void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean confirmarEliminacion(String elemento) {
        return JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar " + elemento + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void inicializarTabla();
    protected abstract void cargarDatos();
    protected abstract void buscar();
    protected abstract void nuevo();
    protected abstract void editar();
    protected abstract void eliminar();
}