package com.berenjeneitor.facturacion.presentacion;

import com.berenjeneitor.facturacion.negocio.ProveedoresService;
import com.berenjeneitor.facturacion.negocio.ValidationException;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import javax.swing.*;
import java.awt.*;

public class FormularioProveedores extends FormularioBase {
    private JTextField txtCIF, txtNombre, txtDireccion, txtCP;
    private JTextField txtPoblacion, txtProvincia, txtPais;
    private JTextField txtTelefono, txtEmail, txtWeb;
    private JTextField txtPersonaContacto, txtTelefonoContacto;
    private JTextArea txtObservaciones;
    
    private final ProveedoresService proveedoresService;
    private Proveedores proveedorActual;

    public FormularioProveedores(ProveedoresService proveedoresService) {
        super("Formulario de Proveedor");
        this.proveedoresService = proveedoresService;
        proveedorActual = new Proveedores();
    }

    @Override
    protected void construirFormulario() {
        // Panel de datos básicos
        JPanel panelDatosBasicos = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDatosBasicos.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));

        panelDatosBasicos.add(new JLabel("CIF/NIF:*"));
        txtCIF = createTextField(12);
        panelDatosBasicos.add(txtCIF);

        panelDatosBasicos.add(new JLabel("Nombre:*"));
        txtNombre = createTextField(80);
        panelDatosBasicos.add(txtNombre);

        // Panel de dirección
        JPanel panelDireccion = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDireccion.setBorder(BorderFactory.createTitledBorder("Dirección"));

        panelDireccion.add(new JLabel("Dirección:"));
        txtDireccion = createTextField(100);
        panelDireccion.add(txtDireccion);

        panelDireccion.add(new JLabel("Código Postal:"));
        txtCP = createTextField(5);
        panelDireccion.add(txtCP);

        panelDireccion.add(new JLabel("Población:"));
        txtPoblacion = createTextField(50);
        panelDireccion.add(txtPoblacion);

        panelDireccion.add(new JLabel("Provincia:"));
        txtProvincia = createTextField(30);
        panelDireccion.add(txtProvincia);

        panelDireccion.add(new JLabel("País:"));
        txtPais = createTextField(30);
        txtPais.setText("España");
        panelDireccion.add(txtPais);

        // Panel de contacto
        JPanel panelContacto = new JPanel(new GridLayout(0, 2, 5, 5));
        panelContacto.setBorder(BorderFactory.createTitledBorder("Contacto"));

        panelContacto.add(new JLabel("Teléfono:"));
        txtTelefono = createTextField(15);
        panelContacto.add(txtTelefono);

        panelContacto.add(new JLabel("Email:"));
        txtEmail = createTextField(80);
        panelContacto.add(txtEmail);

        panelContacto.add(new JLabel("Web:"));
        txtWeb = createTextField(80);
        panelContacto.add(txtWeb);

        panelContacto.add(new JLabel("Persona de Contacto:"));
        txtPersonaContacto = createTextField(50);
        panelContacto.add(txtPersonaContacto);

        panelContacto.add(new JLabel("Teléfono de Contacto:"));
        txtTelefonoContacto = createTextField(15);
        panelContacto.add(txtTelefonoContacto);

        // Panel de observaciones
        JPanel panelObservaciones = new JPanel(new BorderLayout());
        panelObservaciones.setBorder(BorderFactory.createTitledBorder("Observaciones"));

        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservaciones);
        panelObservaciones.add(scrollPane, BorderLayout.CENTER);

        // Agregar todos los paneles al panel principal
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(panelDatosBasicos);
        panel.add(panelDireccion);
        panel.add(panelContacto);
        panel.add(panelObservaciones);
    }

    @Override
    protected void guardar() {
        try {
            // Validaciones básicas
            String cif = txtCIF.getText().trim();
            String nombre = txtNombre.getText().trim();
            
            if (cif.isEmpty()) {
                mostrarError("El CIF/NIF es obligatorio", txtCIF);
                return;
            }

            if (nombre.isEmpty()) {
                mostrarError("El nombre es obligatorio", txtNombre);
                return;
            }

            // Crear o actualizar el objeto
            proveedorActual.setCif(cif);
            proveedorActual.setNombre(nombre);
            proveedorActual.setDireccion(txtDireccion.getText().trim());
            proveedorActual.setCp(txtCP.getText().trim());
            proveedorActual.setPoblacion(txtPoblacion.getText().trim());
            proveedorActual.setProvincia(txtProvincia.getText().trim());
            proveedorActual.setPais(txtPais.getText().trim());
            proveedorActual.setTelefono(txtTelefono.getText().trim());
            proveedorActual.setEmail(txtEmail.getText().trim());
            proveedorActual.setWeb(txtWeb.getText().trim());
            proveedorActual.setPersonaContacto(txtPersonaContacto.getText().trim());
            proveedorActual.setTelefonoContacto(txtTelefonoContacto.getText().trim());
            proveedorActual.setObservaciones(txtObservaciones.getText());
            
            // Guardar usando el servicio
            proveedoresService.saveOrUpdate(proveedorActual);
            
            JOptionPane.showMessageDialog(this, 
                "Proveedor guardado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            proveedorActual = new Proveedores();
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el proveedor: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarError(String mensaje, JComponent componente) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error de validación", 
            JOptionPane.ERROR_MESSAGE);
        componente.requestFocus();
    }
    
    @Override
    protected void limpiarCampos() {
        txtCIF.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCP.setText("");
        txtPoblacion.setText("");
        txtProvincia.setText("");
        txtPais.setText("España");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtWeb.setText("");
        txtPersonaContacto.setText("");
        txtTelefonoContacto.setText("");
        txtObservaciones.setText("");
    }
    
    public void cargarProveedor(Proveedores proveedor) {
        if (proveedor != null) {
            this.proveedorActual = proveedor;
            
            txtCIF.setText(proveedor.getCif());
            txtNombre.setText(proveedor.getNombre());
            txtDireccion.setText(proveedor.getDireccion());
            txtCP.setText(proveedor.getCp());
            txtPoblacion.setText(proveedor.getPoblacion());
            txtProvincia.setText(proveedor.getProvincia());
            txtPais.setText(proveedor.getPais());
            txtTelefono.setText(proveedor.getTelefono());
            txtEmail.setText(proveedor.getEmail());
            txtWeb.setText(proveedor.getWeb());
            txtPersonaContacto.setText(proveedor.getPersonaContacto());
            txtTelefonoContacto.setText(proveedor.getTelefonoContacto());
            txtObservaciones.setText(proveedor.getObservaciones());
        }
    }
}