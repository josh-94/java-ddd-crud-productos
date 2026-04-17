package com.example.productosapi.infrastructure.persistence.mapper;

import com.example.productosapi.domain.model.Cliente;
import com.example.productosapi.infrastructure.persistence.entity.ClienteEntity;
import com.example.productosapi.infrastructure.rest.dto.ClienteRequest;
import com.example.productosapi.infrastructure.rest.dto.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper entre Cliente (dominio) y ClienteEntity (persistencia)
 * También mapea entre DTOs y entidades de dominio
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    /**
     * Convierte ClienteEntity a Cliente (dominio)
     */
    Cliente toDomain(ClienteEntity entity);

    /**
     * Convierte Cliente (dominio) a ClienteEntity
     */
    ClienteEntity toEntity(Cliente cliente);

    /**
     * Convierte lista de ClienteEntity a lista de Cliente
     */
    List<Cliente> toDomainList(List<ClienteEntity> entities);

    /**
     * Convierte ClienteRequest (DTO) a Cliente (dominio)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Cliente toDomain(ClienteRequest request);

    /**
     * Convierte Cliente (dominio) a ClienteResponse (DTO)
     */
    ClienteResponse toResponse(Cliente cliente);

    /**
     * Convierte lista de Cliente a lista de ClienteResponse
     */
    List<ClienteResponse> toResponseList(List<Cliente> clientes);
}
