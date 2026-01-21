package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer rating; // 1-5 estrellas
    private String comment;
    private LocalDateTime createdAt;

    // Relación Many-to-One: Muchas reviews pertenecen a un auto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonBackReference(value = "car-reviews") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    private CarEntity car;

    // Relación Many-to-One: Muchas reviews pertenecen a un cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference(value = "client-reviews") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    private ClientEntity client;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
