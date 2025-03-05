package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenuBar extends JMenuBar implements ActionListener {
    private final MainFrame father;
    // Menús
    private final JMenu fichas;
    private final JMenu facturas;
    private final JMenu rectificativas;
    private final JMenu listados;
    private final JMenu configuracion;
    private final JMenu help;

    // Items de Fichas
    private final JMenuItem clientes;
    private final JMenuItem articulos;
    private final JMenuItem formasPago;
    private final JMenuItem tiposIVA;
    private final JMenuItem familiaArticulos;
    private final JMenuItem proveedores;

    // Items de Facturas
    private final JMenuItem crearFactura;
    private final JMenuItem verFacturas;

    // Items de Rectificativas
    private final JMenuItem crearRectificativa;
    private final JMenuItem verRectificativas;

    // Items de Listados
    private final JMenuItem listadoClientes;
    private final JMenuItem listadoArticulos;
    private final JMenuItem listadoFormasPago;
    private final JMenuItem listadoTiposIVA;
    private final JMenuItem listadoFamiliasArticulos;
    private final JMenuItem listadoProveedores;

    // Items de Configuración
    private final JMenuItem configurarEmpresa;

    // Items de Ayuda
    private final JMenuItem manualUsuario;
    private final JMenuItem acercaDe;

    public MyMenuBar(MainFrame father) {
        this.father = father;
        // Inicializar Menús
        fichas = new JMenu("Fichas");
        facturas = new JMenu("Facturas");
        rectificativas = new JMenu("Rectificativas");
        listados = new JMenu("Listados");
        configuracion = new JMenu("Configuración");
        help = new JMenu("Ayuda");

        // Inicializar Items
        clientes = new JMenuItem("Clientes");
        articulos = new JMenuItem("Articulos");
        formasPago = new JMenuItem("Formas de Pago");
        tiposIVA = new JMenuItem("Tipos de IVA");
        familiaArticulos = new JMenuItem("Familias de Articulos");
        proveedores = new JMenuItem("Proveedores");

        crearFactura = new JMenuItem("Crear Factura");
        verFacturas = new JMenuItem("Ver Facturas");

        crearRectificativa = new JMenuItem("Crear Rectificativa");
        verRectificativas = new JMenuItem("Ver Rectificativas");

        listadoClientes = new JMenuItem("Clientes");
        listadoArticulos = new JMenuItem("Articulos");
        listadoFormasPago = new JMenuItem("Formas de Pago");
        listadoTiposIVA = new JMenuItem("Tipos de IVA");
        listadoFamiliasArticulos = new JMenuItem("Familias de Articulos");
        listadoProveedores = new JMenuItem("Proveedores");

        configurarEmpresa = new JMenuItem("Configurar Empresa");

        manualUsuario = new JMenuItem("Manual de Usuario");
        acercaDe = new JMenuItem("Acerca de");

        // Agregar Items a sus Menús
        fichas.add(clientes);
        fichas.add(articulos);
        fichas.add(formasPago);
        fichas.add(tiposIVA);
        fichas.add(familiaArticulos);
        fichas.add(proveedores);

        facturas.add(crearFactura);
        facturas.add(verFacturas);

        rectificativas.add(crearRectificativa);
        rectificativas.add(verRectificativas);

        listados.add(listadoClientes);
        listados.add(listadoArticulos);
        listados.add(listadoFormasPago);
        listados.add(listadoTiposIVA);
        listados.add(listadoFamiliasArticulos);
        listados.add(listadoProveedores);

        configuracion.add(configurarEmpresa);

        help.add(manualUsuario);
        help.add(acercaDe);

        // Agregar Menús a la Barra
        add(fichas);
        add(facturas);
        add(rectificativas);
        add(listados);
        add(configuracion);
        add(help);

        // Agregar ActionListeners
        clientes.addActionListener(this);
        articulos.addActionListener(this);
        formasPago.addActionListener(this);
        tiposIVA.addActionListener(this);
        familiaArticulos.addActionListener(this);
        proveedores.addActionListener(this);
        crearFactura.addActionListener(this);
        verFacturas.addActionListener(this);
        crearRectificativa.addActionListener(this);
        verRectificativas.addActionListener(this);
        listadoClientes.addActionListener(this);
        listadoArticulos.addActionListener(this);
        listadoFormasPago.addActionListener(this);
        listadoTiposIVA.addActionListener(this);
        listadoFamiliasArticulos.addActionListener(this);
        listadoProveedores.addActionListener(this);
        configurarEmpresa.addActionListener(this);
        manualUsuario.addActionListener(this);
        acercaDe.addActionListener(this);
    }
    // codigo para redirigir
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if(source == clientes){
            // redirigir a clientes
            new FormularioClientes().setVisible(true);
            ((JFrame)this.getParent()).dispose();
        }
    }
}
