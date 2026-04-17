package com.example.productosapi.application.usecase;

import com.example.productosapi.domain.model.Cliente;
import com.example.productosapi.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso: Actualizar un cliente existente
 */
@Component
@RequiredArgsConstructor
public class UpdateClienteUseCase {
    
    private final ClienteService clienteService;

    /**
     * Actualiza la información completa de un cliente
     * 
     * @param id El ID del cliente a actualizar
     * @param nombres Nuevos nombres
     * @param apellidos Nuevos apellidos
     * @param correo Nuevo correo
     * @param telefono Nuevo teléfono
     * @return El cliente actualizado
     */
    public Cliente execute(UUID id, String nombres, String apellidos, String correo, String telefono) {
        return clienteService.actualizarCliente(id, nombres, apellidos, correo, telefono);
    }

    /**
     * Actualiza solo el estado del cliente
     * 
     * @param id El ID del cliente
     * @param activo Nuevo estado
     * @return El cliente actualizado
     */
    public Cliente executeStateUpdate(UUID id, Boolean activo) {
        return clienteService.cambiarEstado(id, activo);
    }
}
