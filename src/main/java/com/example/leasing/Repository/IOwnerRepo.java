package com.example.leasing.Repository;

import com.example.leasing.Entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOwnerRepo extends JpaRepository<OwnerEntity, Long> {
    List<OwnerEntity> findByUser_Email(String email);
}