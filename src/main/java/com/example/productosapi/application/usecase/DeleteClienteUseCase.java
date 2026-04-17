package com.example.productosapi.application.usecase;

import com.example.productosapi.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso: Eliminar un cliente
 */
@Component
@RequiredArgsConstructor
public class DeleteClienteUseCase {
    
    private final ClienteService clienteService;

    /**
     * Ejecuta el caso de uso para eliminar un cliente
     * 
     * @param id El ID del cliente a eliminar
     */
    public void execute(UUID id) {
        clienteService.eliminarCliente(id);
    }
}
