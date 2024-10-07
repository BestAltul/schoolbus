package com.wny.schoolbus.pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.wny.schoolbus.annotations.DisplayName;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;
import com.vaadin.flow.data.binder.Binder;
import java.lang.reflect.Field;


import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.services.impl.BusServiceImpl;
import com.wny.schoolbus.services.impl.DashCamServiceImpl;

import java.util.List;

@Route("bus-list")
public class BusListView extends VerticalLayout {

    private final BusServiceImpl busService;
    private final DashCamServiceImpl dashCamService;
    private final Grid<SchoolBusImpl> grid = new Grid<>(SchoolBusImpl.class);
    private ListDataProvider<SchoolBusImpl> dataProvider;
    private final Button addBusButton = new Button("Add a new bus");
    private final Button backButton = new Button("Back");
    private final TextField filterText = new TextField();

    public BusListView(BusServiceImpl busService, DashCamServiceImpl dashCamService) {
        this.busService = busService;
        this.dashCamService = dashCamService;

        setSizeFull();
        setSpacing(true);

        List<SchoolBusImpl> buses = busService.getAllBuses();
        dataProvider = new ListDataProvider<>(buses);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();

        addColumnsDynamically();

        backButton.addClickListener(event -> UI.getCurrent().getPage().getHistory().back());

        addBusButton.addClickListener(event -> openAddBusDialog());

        filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(event -> applyFilter());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBusButton);
        toolbar.setWidthFull();
        toolbar.setSpacing(true);

        grid.addItemDoubleClickListener(event->openEditDialog(event.getItem()));

        add(backButton, toolbar, grid);

    }

    private void addColumnsDynamically() {
        Field[] fields = SchoolBusImpl.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if("id".equals(field.getName())){
                continue;
            }

            DisplayName displayName = field.getAnnotation(DisplayName.class);
            String header = displayName != null ? displayName.value() : field.getName();

            grid.addColumn(item -> {
                try {
                    return field.get(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return "N/A";
                }
            }).setHeader(header);  // Устанавливаем пользовательское название колонки
        }
    }


    public void openAddBusDialog(){
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");

        ComboBox<BusType> typeField = new ComboBox<>("Type");
        typeField.setItems(BusType.values());
        typeField.setItemLabelGenerator(BusType::getDescription);
        typeField.setPlaceholder("Select bus type");

        ComboBox<Terminal> terminalField = new ComboBox<>("Terminal");
        terminalField.setItems(Terminal.values());
        terminalField.setItemLabelGenerator(Terminal::getDescription);
        terminalField.setPlaceholder("Select terminal");

        ComboBox<DashCamImpl> dashCamComboBox = new ComboBox<>("Dash camera");

        List<DashCamImpl> dashCamList = dashCamService.getAllDashCameras();
        dashCamComboBox.setItems(dashCamList);
        dashCamComboBox.setItemLabelGenerator(DashCamImpl::getDescription);
        dashCamComboBox.setPlaceholder("Select dash camera");

        Button saveButton = new Button("Save", event->{
            String name = nameField.getValue();
            BusType type = typeField.getValue();
            Terminal terminal = terminalField.getValue();

                if (!name.isEmpty()) {
                    SchoolBusImpl newBus = new SchoolBusImpl(name,type,terminal);
                    busService.save(newBus);

                    dataProvider.getItems().add(newBus);
                    dataProvider.refreshAll();

                    Notification.show("The bus is added!");
                    dialog.close();
                } else {
                    Notification.show("Fill in all fields!");
                }
        });
        Button cancelButton = new Button("Cancel",event->dialog.close());

        formLayout.add(nameField,typeField,dashCamComboBox,terminalField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }

    private void openEditDialog(SchoolBusImpl bus) {

        FormLayout formLayout = new FormLayout();
        Dialog dialog = new Dialog();

        Binder<SchoolBusImpl> binder = new Binder<>(SchoolBusImpl.class);

        TextField nameField = new TextField("Name", bus.getName());

        ComboBox<BusType> typeField = new ComboBox<>("Type");
        typeField.setItems(BusType.values());
        typeField.setValue(bus.getBusType());

        ComboBox<Terminal> terminalField = new ComboBox<>("Terminal");
        terminalField.setItems(Terminal.values());
        terminalField.setValue(bus.getTerminal());

        ComboBox<DashCamImpl> dashCameralField = new ComboBox<>("Dash camera");
        dashCameralField.setItems(dashCamService.getAllDashCameras());
        dashCameralField.setValue(bus.getDashCam());

        binder.forField(nameField)
                .asRequired("Name is required")
                .bind(SchoolBusImpl::getName,SchoolBusImpl::setName);
        binder.forField(typeField)
                .asRequired("Type is required")
                .bind(SchoolBusImpl::getBusType,SchoolBusImpl::setBusType);
        binder.forField(terminalField)
                .asRequired("Terminal is required")
                .bind(SchoolBusImpl::getTerminal,SchoolBusImpl::setTerminal);
        binder.forField(dashCameralField)
                .asRequired("Dash camera is required")
                .bind(SchoolBusImpl::getDashCam,SchoolBusImpl::setDashCam);

        binder.setBean(bus);

        Button saveButton = new Button("Save", event -> {

            if(binder.validate().isOk()){
                busService.save(bus);
                dataProvider.refreshAll();
                Notification.show("Bus updated");
                dialog.close();
            }else{
                Notification.show("Please fill all required fields!");
            }

        });

        Button cancelButton = new Button("Cancel",event->dialog.close());

        formLayout.add(nameField,typeField,terminalField,dashCameralField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }

    private void applyFilter(){
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(bus->bus.getName().toLowerCase().contains(filterValue));
    }
}
