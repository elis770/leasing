package com.example.leasing.Repository;

import com.example.leasing.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.client WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithClient(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.client LEFT JOIN FETCH u.owner WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithRelations(@Param("email") String email);
}