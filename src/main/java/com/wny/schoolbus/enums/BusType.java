package com.wny.schoolbus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusType {

    BIGBUS ("Big buses"),
    SMALLBUS("Small buses"),
    VAN("Van"),
    MINIVAN("Others");

    private final String description;

}
