package com.example.leasing.service;

import com.example.leasing.Entity.ClientEntity;
import com.example.leasing.Entity.OwnerEntity;
import com.example.leasing.Entity.UserEntity;
import com.example.leasing.Entity.UserRoleEnum;
import com.example.leasing.Repository.IUserRepo;
import com.example.leasing.exception.BusinessValidationException;
import com.example.leasing.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepo userRepo;

    @Transactional(readOnly = true)
    public ClientEntity getClientByEmail(String email) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User con email " + email + " no encontrado"));

        if (user.getRole() != UserRoleEnum.CLIENT) {
            throw new BusinessValidationException("User is not a client");
        }

        ClientEntity client = user.getClient();
        if (client == null) throw new ResourceNotFoundException("Client para user con email " + email + " no encontrado");
        return client;
    }

    @Transactional(readOnly = true)
    public OwnerEntity getOwnerByEmail(String email) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User con email " + email + " no encontrado"));

        if (user.getRole() != UserRoleEnum.OWNER) {
            throw new BusinessValidationException("User is not an owner");
        }

        OwnerEntity owner = user.getOwner();
        if (owner == null) throw new ResourceNotFoundException("Owner para user con email " + email + " no encontrado");
        return owner;
    }
}