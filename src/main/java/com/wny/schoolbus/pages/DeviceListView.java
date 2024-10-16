package com.wny.schoolbus.pages;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.impl.DashCam;
import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.factory.impl.DashCamFactoryImpl;
import com.wny.schoolbus.factory.impl.RadioFactoryImpl;
import com.wny.schoolbus.services.impl.DashCamServiceImpl;
import com.wny.schoolbus.services.impl.RadioServiceImpl;
import com.wny.schoolbus.services.impl.SimCardServiceImpl;
import com.vaadin.flow.component.button.Button;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/styles.css")
@Route("device-view")
//@Data
public class DeviceListView extends VerticalLayout {

    private final RadioServiceImpl radioService;
    private final SimCardServiceImpl simCardService;
    private final Grid<Device> grid = new Grid<>(Device.class);
    private final DashCamServiceImpl dashCamService;
    private final ListDataProvider<Device> dataProvider;
    private final Button deviceButton = new Button("Add a new device");
    private final TextField filterText = new TextField();
    private final TextField filterDRID = new TextField();
    private final String deviceType;
    ComboBox<String> logicSelector = new ComboBox<>();

    public DeviceListView(String deviceType,DashCamServiceImpl dashCamService, RadioServiceImpl radioService,SimCardServiceImpl simCardService){
        this.deviceType          = deviceType;
        this.simCardService  = simCardService;
        this.dashCamService  = dashCamService;
        this.radioService    = radioService;

        List<? extends Device> deviceList;

        if(deviceType.equals("dashcam")){
            deviceList = dashCamService.getAllDashCameras();
        }else if(deviceType.equals("radio")){
            deviceList = radioService.getAllRadios();
        }else{
            deviceList = new ArrayList<>();
        }

        dataProvider = new ListDataProvider<>(deviceList.stream().collect(Collectors.toList()));

        configureGrid();

        deviceButton.addClickListener(event->openAddDeviceDialog());

        grid.setItems(deviceList.toArray(new Device[0]));
        add(createToolbar(),grid);
        add(deviceButton,grid);

    }

    private HorizontalLayout createToolbar(){
        filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(event -> applyFilter());

        logicSelector.setPlaceholder("Select the filter logic");
        logicSelector.setItems("AND","OR");
        logicSelector.addClassName("centered-green-text");
        logicSelector.setValue("AND");
        logicSelector.addValueChangeListener(event->applyFilter());

        filterDRID.setPlaceholder("Filter by DRID...");
        filterDRID.addValueChangeListener(event -> applyFilter());


        HorizontalLayout toolbar = new HorizontalLayout(filterText,logicSelector, filterDRID,deviceButton);
        toolbar.setWidthFull();
        toolbar.setSpacing(true);
        return toolbar;
    }

    private void applyFilter() {
        String filterValueName = filterText.getValue().trim().toLowerCase();
        String filterValueDRID = filterDRID.getValue().trim().toLowerCase();
        String selectedLogic = logicSelector.getValue();

//        if("AND".equals(selectedLogic)){
//            dataProvider.setFilter(device -> device.getName().toLowerCase().contains(filterValueName) &&
//                    device.getDRID().toLowerCase().contains(filterValueName));
//        }else if("OR".equals(selectedLogic)){
//            dataProvider.setFilter(device -> device.getName().toLowerCase().contains(filterValueDRID) ||
//                    device.getDRID().toLowerCase().contains(filterValueDRID));
//        }
    }

    private void configureGrid(){

        grid.removeAllColumns();

        grid.addColumn(Device::getName).setHeader("Name");

        grid.addColumn(device->{
            if(device instanceof DashCam) {
                return ((DashCam) device).getDRID();
            } else if (device instanceof Radio) {
                return "N/A";
            }else{
                return "";
            }
        }).setHeader("DRID");

        grid.addColumn(device -> {
            SimCard simCard = device.getSimCard();
            return (simCard != null) ? simCard.getSimCardNumber() : "No SIM card";
        }).setHeader("SIM Card Number");


        grid.addColumn(new ComponentRenderer<>(device -> {
            SimCardHistory lastHistory = simCardService.getLastSimCardHistory(device.getSimCard());

            if (lastHistory != null) {
                Button simCardHistoryButton = createSimCardHistoryButton(device);
                return simCardHistoryButton;
            } else {
                return new Span("N/A");
            }


        })).setHeader("Last SIM Change");

        grid.addColumn(device -> device.getSchoolBus() != null ? device.getSchoolBus().getName() : "N/A").setHeader("School Bus");

        grid.addItemDoubleClickListener(event -> openEditDialog(event.getItem()));
    }

    private Button createSimCardHistoryButton(Device device){

        Button simCardHistoryButton = new Button("Show sim card history", event -> {
            String simCardId = device.getSimCard().getId();
            List<SimCardHistory> history = simCardService.getSimCardHistoryByDashCamId(device.getId());
            openSimCardHistoryModal(history);
        });

        simCardHistoryButton.getStyle().set("background", "none");
        simCardHistoryButton.getStyle().set("border", "none");
        simCardHistoryButton.getStyle().set("color", "blue");
        simCardHistoryButton.getStyle().set("text-decoration", "underline");
        simCardHistoryButton.getStyle().set("cursor", "pointer");

        return simCardHistoryButton;
    }

    public void openSimCardHistoryModal(List<SimCardHistory> history){

        com.vaadin.flow.component.dialog.Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Sim card history");

        Grid<SimCardHistory> historyGrid = new Grid<>(SimCardHistory.class);
        historyGrid.setWidthFull();
        historyGrid.removeAllColumns();

        historyGrid.addColumn(SimCardHistory::getStartDate).setHeader("Start date").setAutoWidth(true);
        historyGrid.addColumn(SimCardHistory::getEndDate).setHeader("End date").setAutoWidth(true);
        historyGrid.addColumn(historySim->historySim.getSimCard().getSimCardNumber()).setHeader("Sim card number").setAutoWidth(true);
        historyGrid.addColumn(historySim->historySim.getSimCard().getSimCardType()).setHeader("Type").setAutoWidth(true);

        historyGrid.setItems(history);

        VerticalLayout content = new VerticalLayout(historyGrid);

        content.setWidthFull();

        dialog.add(content);

        Button closeButton = new Button("Close",event->dialog.close());
        dialog.getFooter().add(closeButton);

        dialog.setWidth("80vw");

        dialog.open();

    }

    private void openEditDialog(Device device) {

        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        Binder<Device> binder = new Binder<>(Device.class);

        com.vaadin.flow.component.textfield.TextField nameField = new com.vaadin.flow.component.textfield.TextField("Name", device.getName());
        com.vaadin.flow.component.textfield.TextField dridField = null;
        if(device instanceof DashCam){
            dridField = new com.vaadin.flow.component.textfield.TextField("DRID", ((DashCam)device).getDRID());
            binder.forField(dridField)
                    .asRequired("DRID is required")
                    .bind(dev->((DashCam)dev).getDRID(),
                            (dev,drid)->((DashCam)dev).setDRID(drid));
        }
        com.vaadin.flow.component.textfield.TextField imeiField = new TextField("IMEI", device.getIMEI());
        ComboBox<SimCard> simCardComboBox = new ComboBox<>("SIM Card");
        List<SimCard> simCards = simCardService.getAllSimCards();
        simCardComboBox.setItems(simCards);
        simCardComboBox.setItemLabelGenerator(SimCard::getSimCardNumber);
        simCardComboBox.setValue(device.getSimCard());

        binder.forField(nameField)
                .asRequired("Name is required")
                .bind(Device::getName, Device::setName);

        binder.forField(imeiField)
                .asRequired("IMEI is required")
                .bind(Device::getIMEI, Device::setIMEI);

        binder.setBean(device);

        Button saveButton = new Button("Save", event -> {
            if (binder.validate().isOk()) {

                SimCard selectedSimCard = simCardComboBox.getValue();
                System.out.println("Selected SIM card: " + selectedSimCard);
                System.out.println("Current DashCam SIM card: " + device.getSimCard());
                if (!selectedSimCard.equals(device.getSimCard())) {
                    simCardService.closePreviousSimCardHistory(device);

                    SimCardHistory newHistory = new SimCardHistory();
                    newHistory.setDevice(device);
                    newHistory.setSimCard(selectedSimCard);
                    newHistory.setStartDate(LocalDate.now());
                    simCardService.saveSimCardHistory(newHistory);

                    Notification.show("Sim card date changed!");
                    device.setSimCard(selectedSimCard);
                }
                if(device instanceof DashCam){
                    dashCamService.save((DashCam) device);
                }else if(device instanceof Radio){
                    radioService.save((Radio) device);
                }

                dataProvider.refreshAll();
                Notification.show("Dashcam updated");

                dialog.close();
            } else {
                Notification.show("Please fill all required fields!");
            }
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        formLayout.add(nameField, imeiField, simCardComboBox);
        if (dridField != null) {
            formLayout.add(dridField);
        }

        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));

        dialog.open();

    }

    public void openAddDeviceDialog() {
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        TextField dridField = new TextField("DRID");
        TextField imeiField = new TextField("IMEI");

        ComboBox<SimCard> simCardComboBox = new ComboBox<>("SIM Card");
        List<SimCard> simCards = simCardService.getAllSimCards();
        simCardComboBox.setItems(simCards);
        simCardComboBox.setItemLabelGenerator(SimCard::getSimCardNumber);
        simCardComboBox.setPlaceholder("Select SIM card");

        Button saveButton = new Button("Save", event -> {
            String name = nameField.getValue();
            String drid = dridField.getValue();
            String imei = imeiField.getValue();
            SimCard simCard = simCardComboBox.getValue();

            if (!name.isEmpty() && !drid.isEmpty()) {
                if(deviceType.equals("dashcam")){
                    DashCamFactoryImpl dashCamFactory = new DashCamFactoryImpl();
                    DashCam newDashCam = dashCamFactory.createDevice(name,drid,imei,simCard);
                    dashCamService.save(newDashCam);
                    dataProvider.getItems().add(newDashCam);
                }else if(deviceType.equals("radio")){
                    RadioFactoryImpl radioFactory = new RadioFactoryImpl();
                    Radio newRadio = radioFactory.createDevice(name,drid,imei,simCard);
                    radioService.save(newRadio);
                    dataProvider.getItems().add(newRadio);
                }

                dataProvider.refreshAll();

                Notification.show("The dashcam is added!");
                dialog.close();
            } else {
                Notification.show("Fill in all fields!");
            }
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        formLayout.add(nameField, dridField, imeiField, simCardComboBox);
        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));

        dialog.open();
    }


}
