package com.example.productosapi.domain.service;

import com.example.productosapi.domain.exception.BusinessException;
import com.example.productosapi.domain.model.Cliente;
import com.example.productosapi.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio de dominio para Cliente
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        // Validar datos del cliente
        cliente.validar();

        // Verificar que no exista un cliente con el mismo DNI
        if (clienteRepository.existsByDni(cliente.getDni())) {
            throw new BusinessException("Ya existe un cliente con el DNI: " + cliente.getDni());
        }

        // Verificar que no exista un cliente con el mismo correo
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new BusinessException("Ya existe un cliente con el correo: " + cliente.getCorreo());
        }

        // Generar ID si no existe
        if (cliente.getId() == null) {
            cliente.setId(UUID.randomUUID());
        }

        // Establecer activo por defecto
        if (cliente.getActivo() == null) {
            cliente.setActivo(true);
        }

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerClientePorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerClientePorDni(String dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con DNI: " + dni));
    }

    @Override
    @Transactional
    public Cliente actualizarCliente(UUID id, String nombres, String apellidos, String correo, String telefono) {
        Cliente cliente = obtenerClientePorId(id);

        // Actualizar campos
        if (nombres != null && !nombres.trim().isEmpty()) {
            cliente.setNombres(nombres);
        }
        if (apellidos != null && !apellidos.trim().isEmpty()) {
            cliente.setApellidos(apellidos);
        }
        if (correo != null && !correo.trim().isEmpty()) {
            // Verificar que el nuevo correo no esté en uso por otro cliente
            clienteRepository.findByCorreo(correo)
                    .ifPresent(c -> {
                        if (!c.getId().equals(id)) {
                            throw new BusinessException("El correo ya está en uso por otro cliente");
                        }
                    });
            cliente.setCorreo(correo);
        }
        if (telefono != null) {
            cliente.setTelefono(telefono);
        }

        // Validar cliente actualizado
        cliente.validar();

        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente cambiarEstado(UUID id, Boolean activo) {
        Cliente cliente = obtenerClientePorId(id);
        cliente.setActivo(activo);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminarCliente(UUID id) {
        if (!clienteRepository.findById(id).isPresent()) {
            throw new BusinessException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
