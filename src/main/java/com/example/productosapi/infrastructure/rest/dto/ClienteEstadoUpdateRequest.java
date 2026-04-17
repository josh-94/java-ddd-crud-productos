package com.example.productosapi.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar el estado de un cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEstadoUpdateRequest {

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;
}
