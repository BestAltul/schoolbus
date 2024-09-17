package com.wny.schoolbus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusType {

    BigBus ("Big buses"),
    SmallBus("Small buses"),
    Van("Van"),
    MiniVan("Others");

    private final String description;

}
