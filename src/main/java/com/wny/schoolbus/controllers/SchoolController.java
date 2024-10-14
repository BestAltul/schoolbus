package com.wny.schoolbus.controllers;

import com.wny.schoolbus.dtos.SchoolDTO;
import com.wny.schoolbus.entities.School;
import com.wny.schoolbus.services.SchoolService;
import com.wny.schoolbus.services.impl.SchoolServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v3/school-management")
public class SchoolController {

    private SchoolServiceImpl schoolService;
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<SchoolDTO> addSchool(@RequestBody SchoolDTO schoolDTO){

        School school = modelMapper.map(schoolDTO,School.class);

        School savedSchool = schoolService.addSchool(school);

        SchoolDTO savedSchoolDTO = modelMapper.map(savedSchool,SchoolDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchoolDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable String id, @RequestBody SchoolDTO schoolDTO){

        Optional<School> schoolOptional = schoolService.getSchool(id);

        if(schoolOptional.isPresent()){
            School updatedSchool = schoolService.updateSchool(schoolOptional.get(),schoolDTO);
            SchoolDTO updatedSchoolDTO = modelMapper.map(updatedSchool,SchoolDTO.class);
            return ResponseEntity.ok(updatedSchoolDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
