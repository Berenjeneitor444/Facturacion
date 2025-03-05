package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ArticulosService;
import com.berenjeneitor.facturacion.negocio.FamiliaArticulosService;
import com.berenjeneitor.facturacion.negocio.TiposIVAService;
import com.berenjeneitor.facturacion.persistencia.entidades.Articulos;
import com.berenjeneitor.facturacion.persistencia.entidades.FamiliaArticulos;
import com.berenjeneitor.facturacion.persistencia.entidades.TiposIVA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class FormularioArticulos extends JPanel {
    private final ArticulosService articulosService;
    private final FamiliaArticulosService familiaArticulosService;
    private final TiposIVAService tiposIVAService;

    private JTextField txtDescripcion;
    private JComboBox<FamiliaArticulos> cmbFamilia;
    private JComboBox<TiposIVA> cmbTipoIVA;
    private JTextField txtCoste;
    private JTextField txtPVP;
    private JTextField txtStock;
    private JTextArea txtObservaciones;

    public FormularioArticulos(ArticulosService articulosService,
                             FamiliaArticulosService familiaArticulosService,
                             TiposIVAService tiposIVAService) {
        this.articulosService = articulosService;
        this.familiaArticulosService = familiaArticulosService;
        this.tiposIVAService = tiposIVAService;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        initializeComponents();

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDescripcion, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Familia:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbFamilia, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tipo IVA:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbTipoIVA, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Coste:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCoste, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("PVP:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPVP, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtStock, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1; gbc.gridheight = 2;
        formPanel.add(new JScrollPane(txtObservaciones), gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarArticulo());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);

        // Add panels to main panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load initial data
        cargarFamilias();
        cargarTiposIVA();
    }

    private void initializeComponents() {
        txtDescripcion = new JTextField(20);
        cmbFamilia = new JComboBox<>();
        cmbTipoIVA = new JComboBox<>();
        txtCoste = new JTextField(20);
        txtPVP = new JTextField(20);
        txtStock = new JTextField(20);
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
    }

    private void cargarFamilias() {
        List<FamiliaArticulos> familias = familiaArticulosService.findAll();
        cmbFamilia.removeAllItems();
        for (FamiliaArticulos familia : familias) {
            cmbFamilia.addItem(familia);
        }
    }

    private void cargarTiposIVA() {
        List<TiposIVA> tiposIVA = tiposIVAService.findAll();
        cmbTipoIVA.removeAllItems();
        for (TiposIVA tipoIVA : tiposIVA) {
            cmbTipoIVA.addItem(tipoIVA);
        }
    }

    private void guardarArticulo() {
        try {
            Articulos articulo = new Articulos();
            articulo.setDescripcion(txtDescripcion.getText());
            articulo.setFamilia((FamiliaArticulos) cmbFamilia.getSelectedItem());
            articulo.setTipoIVA((TiposIVA) cmbTipoIVA.getSelectedItem());
            articulo.setCoste(new BigDecimal(txtCoste.getText()));
            articulo.setPvp(new BigDecimal(txtPVP.getText()));
            articulo.setStock(new BigDecimal(txtStock.getText()));
            articulo.setObservaciones(txtObservaciones.getText());

            articulosService.saveOrUpdate(articulo);
            JOptionPane.showMessageDialog(this,
                    "Artículo guardado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el artículo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDescripcion.setText("");
        cmbFamilia.setSelectedIndex(0);
        cmbTipoIVA.setSelectedIndex(0);
        txtCoste.setText("");
        txtPVP.setText("");
        txtStock.setText("");
        txtObservaciones.setText("");
    }
}