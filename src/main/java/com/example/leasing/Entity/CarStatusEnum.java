package com.example.leasing.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CarStatusEnum {
    ACTIVE("ACTIVE"),
    IN_MAINTENANCE("IN_MAINTENANCE"),
    INACTIVE("INACTIVE");
    private final String CarStatus;
}