package com.wny.schoolbus.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Terminal {

    DEPEW("Depew"),
    HAMBURG("Hamburg"),
    SANBORN("Sanborn");

    private final String description;
}
