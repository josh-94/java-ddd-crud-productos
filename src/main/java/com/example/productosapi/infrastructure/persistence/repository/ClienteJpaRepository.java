package com.example.productosapi.infrastructure.persistence.repository;

import com.example.productosapi.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA para ClienteEntity
 */
@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, UUID> {
    
    /**
     * Busca un cliente por su DNI
     */
    Optional<ClienteEntity> findByDni(String dni);

    /**
     * Busca un cliente por su correo
     */
    Optional<ClienteEntity> findByCorreo(String correo);

    /**
     * Busca clientes por estado
     */
    List<ClienteEntity> findByActivo(Boolean activo);

    /**
     * Verifica si existe un cliente con el DNI dado
     */
    boolean existsByDni(String dni);

    /**
     * Verifica si existe un cliente con el correo dado
     */
    boolean existsByCorreo(String correo);
}
