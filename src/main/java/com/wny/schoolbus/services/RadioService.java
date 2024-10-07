package com.wny.schoolbus.services;

import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.RadioImpl;

import java.util.List;

public interface RadioService {
    public List<RadioImpl> getAllRadios();

}
