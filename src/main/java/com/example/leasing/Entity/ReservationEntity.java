package com.example.leasing.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Fechas con hora (LocalDateTime) para inicio y fin de reserva
    // Permite al usuario elegir la hora exacta de entrega/devolución
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    private BigDecimal totalPrice;
    
    // Timestamp de cuándo se creó la reserva
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatusEnum status;

    // Relación Many-to-One: Muchas reservas pertenecen a un auto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonBackReference(value = "car-reservations") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    private CarEntity car;

    // Relación Many-to-One: Muchas reservas pertenecen a un cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference(value = "client-reservations") // Evita loop infinito en JSON
    @ToString.Exclude // Evita loops en toString de Lombok
    private ClientEntity client;
    
    /**
     * Calcula la cantidad de días completos de la reserva
     * Redondea hacia arriba (si reservas de 10:00 a 15:00 del día siguiente = 2 días)
     */
    public long getDaysReserved() {
        if (startDate != null && endDate != null) {
            long hours = ChronoUnit.HOURS.between(startDate, endDate);
            // Redondea hacia arriba: cualquier fracción de día cuenta como día completo
            return (long) Math.ceil(hours / 24.0);
        }
        return 0;
    }
    
    /**
     * Calcula las horas totales de la reserva
     */
    public long getHoursReserved() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.HOURS.between(startDate, endDate);
        }
        return 0;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = ReservationStatusEnum.PENDING;
        }
    }
}
