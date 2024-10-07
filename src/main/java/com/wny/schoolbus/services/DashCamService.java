package com.wny.schoolbus.services;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.impl.DashCamImpl;

import java.util.List;

public interface DashCamService {
    public List<DashCamImpl> getAllDashCameras();
}
