package com.example.leasing.service;

import com.example.leasing.Entity.ClientEntity;
import com.example.leasing.Entity.UserEntity;
import com.example.leasing.Repository.IClientRepo;
import com.example.leasing.Repository.IUserRepo;
import com.example.leasing.exception.BusinessValidationException;
import com.example.leasing.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service para lógica de negocio de Client
 * 
 * VERSION SIMPLIFICADA PARA TESTING:
 * - Solo CRUD básico por ID
 * - Sin autenticación (por ahora)
 * - Para probar que se guardan datos en BD
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final IClientRepo clientRepo;
    private final IUserRepo userRepo;

    // ==========================================
    // BÚSQUEDA POR EMAIL
    // ==========================================

    /**
     * Busca un Client por email de su usuario
     * @throws ResourceNotFoundException si no existe
     */
    @Transactional(readOnly = true)
    public ClientEntity getClientByEmail(String email) {
        return userRepo.findByEmailWithClient(email)
                .map(UserEntity::getClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client with email " + email));
    }

    // ==========================================
    // CRUD BÁSICO (por ID)
    // ==========================================

    /**
     * Busca un Client por ID
     * @throws ResourceNotFoundException si no existe
     */
    @Transactional(readOnly = true)
    public ClientEntity findById(Long id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    /**
     * Obtiene todos los Clients
     */
    @Transactional(readOnly = true)
    public List<ClientEntity> findAll() {
        return clientRepo.findAll();
    }

    /**
     * Guarda un Client (crear o actualizar)
     * @throws BusinessValidationException si los datos son inválidos
     */
    @Transactional
    public ClientEntity save(ClientEntity client) {
        validateClient(client);
        return clientRepo.save(client);
    }

    /**
     * Actualiza un Client existente
     * Solo actualiza campos no nulos
     */
    @Transactional
    public ClientEntity update(Long id, ClientEntity updatedData) {
        ClientEntity existing = findById(id);
        
        // Actualiza solo campos no nulos
        if (updatedData.getFirstName() != null && !updatedData.getFirstName().isEmpty()) {
            existing.setFirstName(updatedData.getFirstName());
        }
        if (updatedData.getLastName() != null && !updatedData.getLastName().isEmpty()) {
            existing.setLastName(updatedData.getLastName());
        }
        if (updatedData.getPhone() != null && !updatedData.getPhone().isEmpty()) {
            existing.setPhone(updatedData.getPhone());
        }
        
        return clientRepo.save(existing);
    }

    /**
     * Elimina un Client por ID
     * @throws ResourceNotFoundException si no existe
     */
    @Transactional
    public void deleteById(Long id) {
        // Verifica que existe antes de eliminar
        findById(id);
        clientRepo.deleteById(id);
    }

    // ==========================================
    // VALIDACIONES PRIVADAS
    // ==========================================

    /**
     * Valida que un Client tenga datos completos
     */
    private void validateClient(ClientEntity client) {
        if (client.getFirstName() == null || client.getFirstName().isEmpty()) {
            throw new BusinessValidationException("El nombre es obligatorio");
        }
        if (client.getLastName() == null || client.getLastName().isEmpty()) {
            throw new BusinessValidationException("El apellido es obligatorio");
        }
        if (client.getPhone() == null || client.getPhone().isEmpty()) {
            throw new BusinessValidationException("El teléfono es obligatorio");
        }
    }
}