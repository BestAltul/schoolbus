package com.wny.schoolbus.pages;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.enums.EmployeeStatus;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.wny.schoolbus.services.EmployeeService;
import com.wny.schoolbus.services.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.combobox.ComboBox;
import com.wny.schoolbus.enums.EmployeeStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Route("employee-list")
public class EmployeeListView extends VerticalLayout{

    private static final String API_URL = "http://localhost:8080/api/v3/employee-management";

    private final EmployeeServiceImpl employeeService;
    private Grid<EmployeeDTO> grid = new Grid<>(EmployeeDTO.class);
    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private TextField phoneNumberField = new TextField("Phone Number");
    private Button saveButton = new Button("Save");

    private ComboBox<EmployeeStatus> statusField = new ComboBox<>("Employee status");

    private RestTemplate restTemplate;

    @Autowired
    public EmployeeListView(EmployeeServiceImpl employeeService, RestTemplate restTemplate) {
        this.employeeService = employeeService;
        this.restTemplate = restTemplate;

        configureGrid();
        configureForm();

        HorizontalLayout formLayout = new HorizontalLayout(firstNameField, lastNameField, phoneNumberField, saveButton);

        formLayout.setVerticalComponentAlignment(Alignment.END,firstNameField,lastNameField,phoneNumberField,saveButton);

        formLayout.setWidthFull();

        add(formLayout, grid);

        updateGrid();
    }

    private void configureGrid() {
        grid.setColumns("firstName", "lastName", "phoneNumber", "employeeStatus");
        grid.asSingleSelect().addValueChangeListener(event -> {
            EmployeeDTO employee = event.getValue();
            if (employee != null) {
                firstNameField.setValue(employee.getFirstName());
                lastNameField.setValue(employee.getLastName());
                phoneNumberField.setValue(employee.getPhoneNumber());
            }
        });
    }

    private void configureForm() {
        statusField.setItems(EmployeeStatus.values());
        statusField.setItemLabelGenerator(EmployeeStatus::name);

        saveButton.addClickListener(e -> saveEmployee());
    }

    private void saveEmployee() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(firstNameField.getValue());
        employeeDTO.setLastName(lastNameField.getValue());
        employeeDTO.setPhoneNumber(phoneNumberField.getValue());
        employeeDTO.setEmployeeStatus(statusField.getValue());

        ResponseEntity<EmployeeDTO> response = restTemplate.postForEntity(API_URL,employeeDTO,EmployeeDTO.class);

        if(response.getStatusCode() == HttpStatus.CREATED){
            updateGrid();
            clearForm();
            Notification.show("Employee saved!");
        }else{
            Notification.show("Error saving employee: "+response.getStatusCode());
        }

    }

    private void updateGrid() {
        ResponseEntity<List<EmployeeDTO>> responce = restTemplate.exchange(
                API_URL + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeDTO>>() {}
        );

        if(responce.getStatusCode()==HttpStatus.OK){
            List<EmployeeDTO> employees = responce.getBody();
            grid.setItems(employees);
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        phoneNumberField.clear();
        grid.deselectAll();
    }
}