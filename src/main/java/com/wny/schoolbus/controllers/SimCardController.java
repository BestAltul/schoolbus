
package com.wny.schoolbus.controllers;

import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.services.impl.SimCardServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("sim-card")
public class SimCardController {

    private final SimCardServiceImpl simCardService;

    public List<SimCardHistory> getSimCardHistory(@PathVariable String simCardId){

        return simCardService.getSimCardHistoryBySimCardId(simCardId);

    }
}

