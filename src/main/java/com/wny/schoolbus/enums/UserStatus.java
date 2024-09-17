package com.wny.schoolbus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("hired or on vacation"),
    FIRED("fired"),
    LAID_OFF("laid off"),
    DELETED("deleted"),
    BLOCKED("blocked");

    private final String description;

}
