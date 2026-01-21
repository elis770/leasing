package com.example.leasing.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoleEnum {
    OWNER("OWNER"),
    CLIENT("CLIENT"),
    ADMIN("ADMIN");
    private final String UserRoleEnum;
}