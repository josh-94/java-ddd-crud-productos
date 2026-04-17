package com.example.productosapi.domain.repository;

import com.example.productosapi.domain.model.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de dominio para Cliente
 * Define las operaciones de persistencia sin exponer detalles de implementación
 */
public interface ClienteRepository {
    
    /**
     * Guarda un cliente
     */
    Cliente save(Cliente cliente);

    /**
     * Busca un cliente por su ID
     */
    Optional<Cliente> findById(UUID id);

    /**
     * Busca un cliente por su DNI
     */
    Optional<Cliente> findByDni(String dni);

    /**
     * Busca un cliente por su correo
     */
    Optional<Cliente> findByCorreo(String correo);

    /**
     * Obtiene todos los clientes
     */
    List<Cliente> findAll();

    /**
     * Obtiene todos los clientes activos
     */
    List<Cliente> findByActivo(Boolean activo);

    /**
     * Verifica si existe un cliente con el DNI dado
     */
    boolean existsByDni(String dni);

    /**
     * Verifica si existe un cliente con el correo dado
     */
    boolean existsByCorreo(String correo);

    /**
     * Elimina un cliente por su ID
     */
    void deleteById(UUID id);
}
