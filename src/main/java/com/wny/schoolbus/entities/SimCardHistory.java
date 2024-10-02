package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;

public interface SimCardHistory {
    DashCamImpl getDashCam();
    SimCardImpl getSimCard();
}
