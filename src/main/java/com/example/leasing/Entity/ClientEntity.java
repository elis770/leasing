package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String phone;

    // Relación One-to-Many: Un cliente tiene muchas reviews
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "client-reviews") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    @Builder.Default
    private List<ReviewEntity> reviews = new ArrayList<>();

    // Relación One-to-Many: Un cliente tiene muchas reservas
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "client-reservations") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    @Builder.Default
    private List<ReservationEntity> reservations = new ArrayList<>();

    // Relación One-to-One: Un cliente tiene un usuario
    @JsonIgnore
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // Evita loops en toString de Lombok
    private UserEntity user;
}