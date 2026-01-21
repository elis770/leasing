package com.example.leasing.Entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    OWNER("OWNER"),
    CLIENT("CLIENT"),
    ADMIN("ADMIN");
    
    private final String role;
    
    // Constructor del enum (debe ser privado o package-private)
    UserRoleEnum(String role) {
        this.role = role;
    }
}
