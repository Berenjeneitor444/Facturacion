package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.*;
import com.berenjeneitor.facturacion.persistencia.entidades.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JPanel statusBar;
    
    // Services
    private final ArticulosService articulosService;
    private final ClientesService clientesService;
    private final ProveedoresService proveedoresService;
    private final FamiliaArticulosService familiaArticulosService;
    private final TiposIVAService tiposIVAService;
    private final FormaPagoService formaPagoService;
    private final FacturasClientesService facturasService;

    public MainFrame(ArticulosService articulosService,
                   ClientesService clientesService,
                   ProveedoresService proveedoresService,
                   FamiliaArticulosService familiaArticulosService,
                   TiposIVAService tiposIVAService,
                   FormaPagoService formaPagoService,
                   FacturasClientesService facturasService) {
        setTitle("Sistema de Facturación");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize services
        this.articulosService = articulosService;
        this.clientesService = clientesService;
        this.proveedoresService = proveedoresService;
        this.familiaArticulosService = familiaArticulosService;
        this.tiposIVAService = tiposIVAService;
        this.formaPagoService = formaPagoService;
        this.facturasService = facturasService;

        // Initialize layouts
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        statusBar = new JPanel(new BorderLayout());
        
        // Setup status bar
        setupStatusBar();

        // Add components
        initializeComponents();

        // Setup menu
        setJMenuBar(new MyMenuBar(this));

        // Layout
        add(mainPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void setupStatusBar() {
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel(" Listo");
        statusBar.add(statusLabel, BorderLayout.WEST);
    }

    private void initializeComponents() {
        // Add home panel
        mainPanel.add(new Home(), "home");

        // Add forms
        mainPanel.add(new FormularioClientes(), "Clientes");
        mainPanel.add(new FormularioProveedores(proveedoresService), "Proveedores");
        mainPanel.add(new FormularioArticulos(articulosService, familiaArticulosService, tiposIVAService), "Articulos");
        mainPanel.add(new FormularioFormasPago(formaPagoService), "FormasPago");
        mainPanel.add(new FormulariosTiposIva(tiposIVAService), "TiposIVA");
        mainPanel.add(new FormularioFamiliaArticulos(familiaArticulosService), "FamiliaArticulos");
        mainPanel.add(new FormularioFacturas(facturasService, clientesService, articulosService), "CrearFactura");

        // Add listados
        mainPanel.add(new ListadoClientes(clientesService, this), "ListadoClientes");
        mainPanel.add(new ListadoArticulos(articulosService, this), "ListadoArticulos");
        mainPanel.add(new ListadoProveedores(proveedoresService, this), "ListadoProveedores");
        mainPanel.add(new ListadoFacturas(facturasService, this), "ListadoFacturas");
        mainPanel.add(new ListadoFormasPago(formaPagoService, this), "ListadoFormasPago");
        mainPanel.add(new ListadoTiposIVA(tiposIVAService, this), "ListadoTiposIVA");
        mainPanel.add(new ListadoFamiliasArticulos(familiaArticulosService, this), "ListadoFamiliasArticulos");
        mainPanel.add(new ListadoRectificativas(facturasService, this), "ListadoRectificativas");

        // Add configuration
        mainPanel.add(new ConfiguracionEmpresa(), "ConfiguracionEmpresa");
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}