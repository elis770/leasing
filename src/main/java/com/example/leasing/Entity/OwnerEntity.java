package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String descripcion;
    private String photoUrl;
    private String bankAccount;

    // Relación One-to-Many: Un owner tiene muchos autos
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "owner-cars") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    @Builder.Default
    private List<CarEntity> cars = new ArrayList<>();

    // Relación One-to-One: Un owner tiene un usuario
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // Evita loops en toString de Lombok
    private UserEntity user;
}