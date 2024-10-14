package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.SchoolDTO;
import com.wny.schoolbus.repositories.SchoolRepository;
import com.wny.schoolbus.services.SchoolService;
import com.wny.schoolbus.entities.School;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    private ModelMapper modelMapper;

    public School addSchool(School school){
        return schoolRepository.save(school);
    }

    public Optional<School> getSchool(String id){
        return schoolRepository.findById(id);
    }

    public List<School> getAllSchool(){
        return schoolRepository.findAll();
    }

    public School updateSchool(School school, SchoolDTO schoolDTO){
        modelMapper.map(schoolDTO,school);

        School updatedSchool = schoolRepository.save(school);

        return updatedSchool;
    }

    public ResponseEntity<Void> deleteSchool(String id){
        Optional<School> schoolOptional = schoolRepository.findById(id);

        if(schoolOptional.isPresent()){
            schoolRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
