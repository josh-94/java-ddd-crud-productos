package com.example.productosapi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio Cliente
 * Representa un cliente en el sistema
 */
@Data
@Builder
public class Cliente {
    
    private UUID id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Valida que el cliente tenga todos los datos requeridos
     */
    public void validar() {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (dni.length() != 8) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }
        if (nombres == null || nombres.trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
    }

    /**
     * Obtiene el nombre completo del cliente
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Activa el cliente
     */
    public void activar() {
        this.activo = true;
    }

    /**
     * Desactiva el cliente
     */
    public void desactivar() {
        this.activo = false;
    }
}
