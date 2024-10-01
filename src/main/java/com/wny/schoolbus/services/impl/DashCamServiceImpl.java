package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.repositories.DashCamRepository;
import com.wny.schoolbus.services.DashCamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashCamServiceImpl implements DashCamService {
    private final DashCamRepository dashCamRepository;

    public List<DashCamImpl> getAllDashCameras(){
       return dashCamRepository.findAll();
    }
}
