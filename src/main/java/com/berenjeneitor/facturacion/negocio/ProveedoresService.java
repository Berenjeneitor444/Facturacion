package com.berenjeneitor.facturacion.negocio;

import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ProveedoresDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Proveedores;

import java.util.List;
import java.util.regex.Pattern;

public class ProveedoresService extends GenericServiceImpl<Proveedores, Integer> {
    private final ProveedoresDAO proveedorDAO;

    public ProveedoresService(ProveedoresDAO proveedorDAO) {
        super(proveedorDAO);
        this.proveedorDAO = proveedorDAO;
    }

    @Override
    public void validate(Proveedores proveedor) throws ValidationException {
        // Validate CIF
        if (proveedor.getCif() == null || !isValidCIF(proveedor.getCif())) {
            throw new ValidationException("CIF del proveedor inválido");
        }

        // Validate nombre
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre del proveedor es obligatorio");
        }

        // Validate email (if provided)
        if (proveedor.getEmail() != null && !isValidEmail(proveedor.getEmail())) {
            throw new ValidationException("Formato de email inválido");
        }

        // Validate telefono (if provided)
        if (proveedor.getTelefono() != null && !isValidTelefono(proveedor.getTelefono())) {
            throw new ValidationException("Número de teléfono inválido");
        }

        // Validate IBAN (if provided)
        if (proveedor.getIban() != null && !isValidIBAN(proveedor.getIban())) {
            throw new ValidationException("Número de IBAN inválido");
        }
    }

    @Override
    public Proveedores processEntity(Proveedores entity) {
        validate(entity);
        return entity;
    }

    // CIF validation method (simplified)
    private boolean isValidCIF(String cif) {
        return cif != null && cif.matches("^[A-HJPQSUV][0-9]{7}[A-J0-9]$");
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Telefono validation method (Spanish phone number format)
    private boolean isValidTelefono(String telefono) {
        return telefono != null && telefono.matches("^\\+?\\d{9,15}$");
    }

    // IBAN validation method (simplified)
    private boolean isValidIBAN(String iban) {
        return iban != null && iban.matches("^[A-Z]{2}\\d{22}$");
    }

    // Additional business logic methods
    public List<Proveedores> findByProvincia(String provincia) {
        return proveedorDAO.findByProvincia(provincia);
    }

    public List<Proveedores> findByPoblacion(String poblacion) {
        return proveedorDAO.findByPoblacion(poblacion);
    }

    public List<Proveedores> findByPais(String pais) {
        return proveedorDAO.findByPais(pais);
    }
}
