package com.berenjeneitor.facturacion.negocio;



import com.berenjeneitor.facturacion.negocio.generic.GenericServiceImpl;
import com.berenjeneitor.facturacion.persistencia.DAOs.interfaces.ClientesDAO;
import com.berenjeneitor.facturacion.persistencia.entidades.Clientes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ClientesService extends GenericServiceImpl<Clientes, Integer> {
    private final ClientesDAO clienteDAO;

    public ClientesService(ClientesDAO clienteDAO) {
        super(clienteDAO);
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void validate(Clientes cliente) throws ValidationException {
        // Validate CIF/NIF
        if (cliente.getCif() == null || !isValidCIF(cliente.getCif())) {
            throw new ValidationException("CIF/NIF inválido");
        }

        // Validate name
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre del cliente es obligatorio");
        }

        // Validate email (if provided)
        if (cliente.getEmail() != null && !isValidEmail(cliente.getEmail())) {
            throw new ValidationException("Formato de email inválido");
        }

        // Validate telefono (if provided)
        if (cliente.getTelefono() != null && !isValidTelefono(cliente.getTelefono())) {
            throw new ValidationException("Número de teléfono inválido");
        }

        // Validate IBAN (if provided)
        if (cliente.getIban() != null && !isValidIBAN(cliente.getIban())) {
            throw new ValidationException("Número de IBAN inválido");
        }
    }

    @Override
    public Clientes processEntity(Clientes entity) {
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
    public Optional<Clientes> findByNombre(String nombre) {
        return clienteDAO.findByNombre(nombre);
    }

    public List<Clientes> findByProvincia(String provincia) {
        return clienteDAO.findByProvincia(provincia);
    }
}