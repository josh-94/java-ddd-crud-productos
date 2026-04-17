package com.example.productosapi.application.usecase;

import com.example.productosapi.domain.model.Cliente;
import com.example.productosapi.domain.repository.ClienteRepository;
import com.example.productosapi.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: Obtener clientes
 */
@Component
@RequiredArgsConstructor
public class GetClienteUseCase {
    
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    /**
     * Obtiene un cliente por su ID
     */
    public Cliente executeById(UUID id) {
        return clienteService.obtenerClientePorId(id);
    }

    /**
     * Obtiene un cliente por su DNI
     */
    public Cliente executeByDni(String dni) {
        return clienteService.obtenerClientePorDni(dni);
    }

    /**
     * Obtiene todos los clientes
     */
    public List<Cliente> executeAll() {
        return clienteRepository.findAll();
    }

    /**
     * Obtiene clientes por estado (activo/inactivo)
     */
    public List<Cliente> executeByActivo(Boolean activo) {
        return clienteRepository.findByActivo(activo);
    }
}
