package com.example.leasing.Repository;

import com.example.leasing.Entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IClientRepo extends JpaRepository<ClientEntity, Long> {
    
    // Buscar por marca exacta
    List<ClientEntity> findByUser_Email(String email);
}