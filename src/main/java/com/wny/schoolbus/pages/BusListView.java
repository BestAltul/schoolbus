package com.wny.schoolbus.pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.wny.schoolbus.annotations.DisplayName;
import com.wny.schoolbus.entities.impl.DashCam;
import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;
import com.vaadin.flow.data.binder.Binder;
import java.lang.reflect.Field;


import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.factory.VehicleFactory;
import com.wny.schoolbus.factory.impl.SchoolBusFactoryImpl;
import com.wny.schoolbus.services.impl.BusServiceImpl;
import com.wny.schoolbus.services.impl.DashCamServiceImpl;
import com.wny.schoolbus.services.impl.RadioServiceImpl;
import com.wny.schoolbus.services.impl.SchoolBusHistoryServiceImpl;
import com.wny.schoolbus.utils.SchoolBusRevision;

import java.util.List;

@Route("bus-list")
public class BusListView extends VerticalLayout {

    private final BusServiceImpl busService;
    private final DashCamServiceImpl dashCamService;

    private final RadioServiceImpl radioService;
    private final Grid<SchoolBusImpl> grid = new Grid<>(SchoolBusImpl.class);
    private ListDataProvider<SchoolBusImpl> dataProvider;
    private final Button addBusButton = new Button("Add a new bus");
    private final Button backButton = new Button("Back");
    private final TextField filterText = new TextField();
    private final SchoolBusHistoryServiceImpl schoolBusHistoryService;

    public BusListView(BusServiceImpl busService, DashCamServiceImpl dashCamService, RadioServiceImpl radioService,SchoolBusHistoryServiceImpl schoolBusHistoryService) {
        this.busService = busService;
        this.dashCamService = dashCamService;
        this.radioService = radioService;
        this.schoolBusHistoryService = schoolBusHistoryService;

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
            }).setHeader(header);
        }

        grid.addColumn(new ComponentRenderer<>(device -> {
             List<Number> listNumber = schoolBusHistoryService.getRevisionsForEntity(device.getId());

            if (!listNumber.isEmpty()) {
                Button schoolBusHistoryButton = createSchoolBusHistoryButton(device);
                return schoolBusHistoryButton;
            } else {
                return new Span("N/A");
            }


        })).setHeader("Last SIM Change");
    }

    private Button createSchoolBusHistoryButton(SchoolBusImpl device){

        Button schoolBusHistoryButton = new Button("Show school bus history", event -> {
            List<Number> listNumber = schoolBusHistoryService.getRevisionsForEntity(device.getId());
            openSchoolBusHistoryModal(device.getId(), listNumber);
        });

        schoolBusHistoryButton.getStyle().set("background", "none");
        schoolBusHistoryButton.getStyle().set("border", "none");
        schoolBusHistoryButton.getStyle().set("color", "blue");
        schoolBusHistoryButton.getStyle().set("text-decoration", "underline");
        schoolBusHistoryButton.getStyle().set("cursor", "pointer");

        return schoolBusHistoryButton;
    }

    private void openSchoolBusHistoryModal(String busId, List<Number> revisions) {

        com.vaadin.flow.component.dialog.Dialog dialog = new Dialog();
        dialog.setHeaderTitle("School bus history");

        Grid<SchoolBusRevision> historyGrid = new Grid<>(SchoolBusRevision.class);
        historyGrid.setWidthFull();
        historyGrid.removeAllColumns();

        historyGrid.addColumn(revision -> revision.getSchoolBus().getName()).setHeader("Bus number").setAutoWidth(true);
        historyGrid.addColumn(revision -> revision.getSchoolBus().getBusType()).setHeader("Bus type").setAutoWidth(true);
        historyGrid.addColumn(revision -> revision.getSchoolBus().getTerminal()).setHeader("Terminal").setAutoWidth(true);

        historyGrid.addColumn(revision -> {
            DashCam dashCam = revision.getSchoolBus().getDashCam();
            return dashCam != null ? dashCam.getName() : "No DashCam";
        }).setHeader("Dash Camera").setAutoWidth(true);

        historyGrid.addColumn(revision -> {
            Radio radio = revision.getSchoolBus().getRadio();
            return radio != null ? radio.getName() : "No Radio";
        }).setHeader("Radio").setAutoWidth(true);

        historyGrid.addColumn(revision -> revision.getRevisionDate().toString()).setHeader("Date of Change").setAutoWidth(true);

        List<SchoolBusRevision> busRevisions = schoolBusHistoryService.getEntitiesAtRevisionsWithDate(busId, revisions);

        historyGrid.setItems(busRevisions);

        VerticalLayout content = new VerticalLayout(historyGrid);
        content.setWidthFull();
        dialog.add(content);

        Button closeButton = new Button("Close", event -> dialog.close());
        dialog.getFooter().add(closeButton);

        dialog.setWidth("80vw");
        dialog.open();
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

        ComboBox<DashCam> dashCamComboBox = new ComboBox<>("Dash camera");
        List<DashCam> dashCamList = dashCamService.getAllDashCameras();
        dashCamComboBox.setItems(dashCamList);
        dashCamComboBox.setItemLabelGenerator(DashCam::getName);
        dashCamComboBox.setPlaceholder("Select dash camera");

        ComboBox<Radio> radioComboBox = new ComboBox<>("Radio");
        List<Radio> radioList = radioService.getAllRadios();
        radioComboBox.setItems(radioList);
        radioComboBox.setItemLabelGenerator(Radio::getDescription);
        radioComboBox.setPlaceholder("Select radio");

        Button saveButton = new Button("Save", event->{
            String name = nameField.getValue();
            BusType type = typeField.getValue();
            Terminal terminal = terminalField.getValue();
            Radio radio = radioComboBox.getValue();
            DashCam dashCam = dashCamComboBox.getValue();

                if (!name.isEmpty()) {
                    //SchoolBusImpl newBus = new SchoolBusImpl(name,type,terminal,dashCam,radio);
                    VehicleFactory vehicleFactory = new SchoolBusFactoryImpl();
                    SchoolBusImpl newBus = vehicleFactory.createVehicle(name,type,terminal,dashCam,radio);
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

        formLayout.add(nameField,typeField,dashCamComboBox,terminalField,radioComboBox);
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

        ComboBox<DashCam> dashCameralField = new ComboBox<>("Dash camera");
        dashCameralField.setItems(dashCamService.getAllDashCameras());
        dashCameralField.setValue(bus.getDashCam());

        ComboBox<Radio> radioField = new ComboBox<>("Camera");
        radioField.setItems(radioService.getAllRadios());
        radioField.setValue(bus.getRadio());


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
        binder.forField(radioField)
                .asRequired("Dash camera is required")
                .bind(SchoolBusImpl::getRadio,SchoolBusImpl::setRadio);

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

        formLayout.add(nameField,typeField,terminalField,dashCameralField,radioField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }

    private void applyFilter(){
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(bus->bus.getName().toLowerCase().contains(filterValue));
    }
}
