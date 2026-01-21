package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String brand;
    private String model;
    private String color;
    private String descripcion;
    private BigDecimal pricePerDay;
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private CarStatusEnum status;

    // Relación Many-to-One: Muchos autos pertenecen a un Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude // Evita loops en toString de Lombok
    private OwnerEntity owner;

    // Relación One-to-Many: Un auto tiene muchas reviews
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "car-reviews") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    @Builder.Default
    private List<ReviewEntity> reviews = new ArrayList<>();

    // Relación One-to-Many: Un auto tiene muchas reservas
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "car-reservations") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    @Builder.Default
    private List<ReservationEntity> reservations = new ArrayList<>();
}
