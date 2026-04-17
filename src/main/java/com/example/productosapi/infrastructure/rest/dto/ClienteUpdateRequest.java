package com.example.productosapi.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un cliente existente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateRequest {

    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    private String nombres;

    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    private String apellidos;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 150, message = "El correo no debe exceder 150 caracteres")
    private String correo;

    @Size(max = 15, message = "El teléfono no debe exceder 15 caracteres")
    private String telefono;
}
