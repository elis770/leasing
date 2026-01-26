package com.example.leasing.Repository;

import com.example.leasing.Entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOwnerRepo extends JpaRepository<OwnerEntity, Long> {
}