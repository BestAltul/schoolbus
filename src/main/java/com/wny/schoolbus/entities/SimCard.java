package com.wny.schoolbus.entities;

import com.wny.schoolbus.enums.SimCardCarrier;
import com.wny.schoolbus.enums.SimCardType;

public interface SimCard {
    SimCardType getSimCardType();
    String getSimCardNumber();
    SimCardCarrier getSimCardCarrier();
}
