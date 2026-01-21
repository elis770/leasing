package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    @JsonIgnore // Nunca serializar la contraseña en JSON
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    // Relación One-to-One: Un usuario puede ser un Owner
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", unique = true)
    @ToString.Exclude // Evita loops en toString de Lombok
    private OwnerEntity owner;

    // Relación One-to-One: Un usuario puede ser un Client
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", unique = true)
    @ToString.Exclude // Evita loops en toString de Lombok
    private ClientEntity client;
}
