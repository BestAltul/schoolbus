package com.wny.schoolbus.pages;


import com.wny.schoolbus.dtos.SchoolDTO;
import com.wny.schoolbus.entities.School;
import com.wny.schoolbus.services.SchoolService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.apache.el.lang.ELArithmetic.add;

@Route("school-form")
public class SchoolFormAddEdit {
    private SchoolService schoolService;
    private School currentSchool;

    private TextField nameField = new TextField("School name");
    private Button saveButton = new Button("Save");

    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl = "/api/v3/school-management";

    public SchoolFormAddEdit(SchoolService schoolService){
        this.schoolService = schoolService;

        add(nameField,saveButton);

        saveButton.addClickListener(event->saveSchool());
    }

    private void saveSchool(){

        if(currentSchool==null){

            SchoolDTO schoolDTO = new SchoolDTO(nameField.getValue());

            restTemplate.postForObject(apiUrl,schoolDTO,SchoolDTO.class);

            Notification.show("School added successfully!");

            clearForm();

        }else{

            SchoolDTO schoolDTO = new SchoolDTO(nameField.getValue());

           // String url = apiUrl+"/"+

        }


    }

    private void clearForm(){
        nameField.clear();
    }


}
