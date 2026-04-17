package com.example.productosapi.application.usecase;

import com.example.productosapi.domain.model.Cliente;
import com.example.productosapi.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo cliente
 */
@Component
@RequiredArgsConstructor
public class CreateClienteUseCase {
    
    private final ClienteService clienteService;

    /**
     * Ejecuta el caso de uso para crear un cliente
     * 
     * @param cliente El cliente a crear
     * @return El cliente creado con su ID
     */
    public Cliente execute(Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }
}
