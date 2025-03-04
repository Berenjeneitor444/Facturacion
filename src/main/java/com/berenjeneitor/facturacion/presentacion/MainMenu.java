package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        super("Facturación");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Declaración de componentes
        JLabel lblEmpresa = new JLabel("Mi Empresa");
        ImageIcon imgEmpresa = new ImageIcon("src/main/resources/empresa.png");
        JLabel lblImgEmpresa = new JLabel(imgEmpresa);
        JTable tblFacturas = createStatisticsTable();

        // Añadir componentes al layout
        add(lblEmpresa, gbc);
        add(lblImgEmpresa, gbc);
        add(new JScrollPane(tblFacturas), gbc);

        // Barra de navegación
        setJMenuBar(createMenuBar());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JTable createStatisticsTable(){
        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Tipo", "Cantidad"}, 0);
        return new JTable(dtm);
    }
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fichas = new JMenu("Fichas");
        JMenuItem clientes = new JMenuItem("Clientes");
        JMenuItem articulos = new JMenuItem("Articulos");
        JMenuItem formasPago = new JMenuItem("Formas de Pago");
        JMenuItem tiposIVA = new JMenuItem("Tipos de IVA");
        JMenuItem familiaArticulos = new JMenuItem("Familias de Articulos");
        JMenuItem proveedores = new JMenuItem("Proveedores");
        fichas.add(clientes);
        fichas.add(articulos);
        fichas.add(formasPago);
        fichas.add(tiposIVA);
        fichas.add(familiaArticulos);
        fichas.add(proveedores);
        JMenu facturas = new JMenu("Facturas");
        JMenuItem crearFactura = new JMenuItem("Crear Factura");
        JMenuItem verFacturas = new JMenuItem("Ver Facturas");
        facturas.add(crearFactura);
        facturas.add(verFacturas);
        JMenu rectificativas = new JMenu("Rectificativas");
        JMenuItem crearRectificativa = new JMenuItem("Crear Rectificativa");
        JMenuItem verRectificativas = new JMenuItem("Ver Rectificativas");
        rectificativas.add(crearRectificativa);
        rectificativas.add(verRectificativas);
        JMenu configuracion = new JMenu("Configuración");
        JMenuItem configurarEmpresa = new JMenuItem("Configurar Empresa");
        configuracion.add(configurarEmpresa);
        JMenu help = new JMenu("Ayuda");
        JMenuItem manualUsuario = new JMenuItem("Manual de Usuario");
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        help.add(manualUsuario);
        help.add(acercaDe);
        menuBar.add(fichas);
        menuBar.add(facturas);
        menuBar.add(rectificativas);
        menuBar.add(configuracion);
        menuBar.add(help);
        return menuBar;
    }
}
