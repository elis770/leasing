package com.example.leasing.Entity;

import lombok.Getter;

@Getter
public enum CarStatusEnum {
    ACTIVE("ACTIVE"),
    IN_MAINTENANCE("IN_MAINTENANCE"),
    INACTIVE("INACTIVE");
    
    private final String status;
    
    // Constructor del enum (debe ser privado o package-private)
    CarStatusEnum(String status) {
        this.status = status;
    }
}
