package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Facturación");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Agregar los diferentes formularios al mainPanel
        mainPanel.add(new FormularioClientes(), "Clientes");
        mainPanel.add(new FormularioArticulos(), "Articulos");
        mainPanel.add(new FormularioFormasPago(), "FormasPago");
        mainPanel.add(new FormularioTiposIVA(), "TiposIVA");
        mainPanel.add(new FormularioFamiliaArticulos(), "FamiliaArticulos");
        mainPanel.add(new FormularioProveedores(), "Proveedores");
        mainPanel.add(new FormularioCrearFactura(), "CrearFactura");
        mainPanel.add(new FormularioVerFacturas(), "VerFacturas");
        mainPanel.add(new FormularioCrearRectificativa(), "CrearRectificativa");
        mainPanel.add(new FormularioVerRectificativas(), "VerRectificativas");
        mainPanel.add(new ListadoClientes(), "ListadoClientes");
        mainPanel.add(new ListadoArticulos(), "ListadoArticulos");
        mainPanel.add(new ListadoFormasPago(), "ListadoFormasPago");
        mainPanel.add(new ListadoTiposIVA(), "ListadoTiposIVA");
        mainPanel.add(new ListadoFamiliasArticulos(), "ListadoFamiliasArticulos");
        mainPanel.add(new ListadoProveedores(), "ListadoProveedores");
        mainPanel.add(new FormularioConfigurarEmpresa(), "ConfigurarEmpresa");
        mainPanel.add(new ManualUsuario(), "ManualUsuario");
        mainPanel.add(new AcercaDe(), "AcercaDe");

        add(mainPanel, BorderLayout.CENTER);
        setJMenuBar(new MyMenuBar(this));

        setVisible(true);
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}