package com.example.leasing.Entity;

import lombok.Getter;

@Getter
public enum ReservationStatusEnum {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    CANCELED("CANCELED");
    
    private final String status;
    
    // Constructor del enum (debe ser privado o package-private)
    ReservationStatusEnum(String status) {
        this.status = status;
    }
}
