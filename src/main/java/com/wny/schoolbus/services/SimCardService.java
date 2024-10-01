package com.wny.schoolbus.services;

import com.wny.schoolbus.entities.impl.SimCardImpl;

import java.util.List;

public interface SimCardService {
    List<SimCardImpl> getAllSimCards();
}
