package com.example.productosapi.domain.service;

import com.example.productosapi.domain.model.Cliente;

import java.util.UUID;

/**
 * Servicio de dominio para Cliente
 * Contiene la lógica de negocio relacionada con clientes
 */
public interface ClienteService {
    
    /**
     * Crea un nuevo cliente
     */
    Cliente crearCliente(Cliente cliente);

    /**
     * Obtiene un cliente por su ID
     */
    Cliente obtenerClientePorId(UUID id);

    /**
     * Obtiene un cliente por su DNI
     */
    Cliente obtenerClientePorDni(String dni);

    /**
     * Actualiza la información de un cliente
     */
    Cliente actualizarCliente(UUID id, String nombres, String apellidos, String correo, String telefono);

    /**
     * Cambia el estado de un cliente
     */
    Cliente cambiarEstado(UUID id, Boolean activo);

    /**
     * Elimina un cliente
     */
    void eliminarCliente(UUID id);
}
