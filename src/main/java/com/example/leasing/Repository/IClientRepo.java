package com.example.leasing.Repository;

import com.example.leasing.Entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepo extends JpaRepository<ClientEntity, Long> {
}