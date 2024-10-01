package com.wny.schoolbus.pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.services.impl.DashCamServiceImpl;
import com.wny.schoolbus.services.impl.SimCardServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Route("dashcam-list")
public class DashCamListView extends VerticalLayout {
    private final DashCamServiceImpl dashCamService;
    private final SimCardServiceImpl simCardService;
    private final Grid<DashCamImpl> grid = new Grid<>(DashCamImpl.class);
    private ListDataProvider<DashCamImpl> dataProvider;
    private final Button addDashCamButton = new Button("Add a new dashcam");
    private final Button backButton = new Button("Back");
    private final TextField filterText = new TextField();

    public DashCamListView(DashCamServiceImpl dashCamService, SimCardServiceImpl simCardService) {
        this.dashCamService = dashCamService;
        this.simCardService = simCardService;

        // Настройка таблицы с данными
        List<DashCamImpl> dashCams = dashCamService.getAllDashCameras();
        dataProvider = new ListDataProvider<>(dashCams);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();

        grid.addColumn(DashCamImpl::getName).setHeader("Name");
        grid.addColumn(DashCamImpl::getDRID).setHeader("DRID");
        grid.addColumn(dashCam -> dashCam.getSimCard().getSimCardNumber()).setHeader("SIM Card Number");
        grid.addColumn(dashCam -> dashCam.getSchoolBus() != null ? dashCam.getSchoolBus().getName() : "N/A").setHeader("School Bus");

        backButton.addClickListener(event -> UI.getCurrent().getPage().getHistory().back());

        addDashCamButton.addClickListener(event -> openAddDashCamDialog());

        filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(event -> applyFilter());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addDashCamButton);
        toolbar.setWidthFull();  // Занять всю ширину
        toolbar.setSpacing(true);  // Добавить отступы между элементами

        grid.addItemDoubleClickListener(event -> openEditDialog(event.getItem()));

        add(backButton, toolbar, grid);
    }

    public void openAddDashCamDialog() {
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        TextField dridField = new TextField("DRID");
        TextField imeiField = new TextField("IMEI");

        ComboBox<SimCardImpl> simCardComboBox = new ComboBox<>("SIM Card");
        // Получение списка SIM-карт для выбора
        List<SimCardImpl> simCards = simCardService.getAllSimCards(); // Предполагаем, что есть такой метод
        simCardComboBox.setItems(simCards);
        simCardComboBox.setItemLabelGenerator(SimCardImpl::getSimCardNumber);
        simCardComboBox.setPlaceholder("Select SIM card");

        Button saveButton = new Button("Save", event -> {
            String name = nameField.getValue();
            String drid = dridField.getValue();
            String imei = imeiField.getValue();
            SimCardImpl simCard = simCardComboBox.getValue();

            if (!name.isEmpty() && !drid.isEmpty() && simCard != null) {
                DashCamImpl newDashCam = new DashCamImpl(null, name, drid, imei, simCard, null);
                dashCamService.save(newDashCam);

                dataProvider.getItems().add(newDashCam);
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

    private void openEditDialog(DashCamImpl dashCam) {
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        Binder<DashCamImpl> binder = new Binder<>(DashCamImpl.class);

        TextField nameField = new TextField("Name", dashCam.getName());
        TextField dridField = new TextField("DRID", dashCam.getDRID());
        TextField imeiField = new TextField("IMEI", dashCam.getIMEI());

        ComboBox<SimCardImpl> simCardComboBox = new ComboBox<>("SIM Card");
        List<SimCardImpl> simCards = simCardService.getAllSimCards(); // Предполагаем, что есть такой метод
        simCardComboBox.setItems(simCards);
        simCardComboBox.setItemLabelGenerator(SimCardImpl::getSimCardNumber);
        simCardComboBox.setValue(dashCam.getSimCard());

        binder.forField(nameField)
                .asRequired("Name is required")
                .bind(DashCamImpl::getName, DashCamImpl::setName);
        binder.forField(dridField)
                .asRequired("DRID is required")
                .bind(DashCamImpl::getDRID, DashCamImpl::setDRID);
        binder.forField(imeiField)
                .asRequired("IMEI is required")
                .bind(DashCamImpl::getIMEI, DashCamImpl::setIMEI);
        binder.forField(simCardComboBox)
                .asRequired("SIM Card is required")
                .bind(DashCamImpl::getSimCard, DashCamImpl::setSimCard);

        binder.setBean(dashCam);

        Button saveButton = new Button("Save", event -> {
            if (binder.validate().isOk()) {
                dashCamService.save(dashCam);
                dataProvider.refreshAll();
                Notification.show("Dashcam updated");
                dialog.close();
            } else {
                Notification.show("Please fill all required fields!");
            }
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        formLayout.add(nameField, dridField, imeiField, simCardComboBox);
        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));

        dialog.open();
    }

    private void applyFilter() {
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(dashCam -> dashCam.getName().toLowerCase().contains(filterValue));
    }
}
