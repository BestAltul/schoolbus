package com.wny.schoolbus.pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.wny.schoolbus.enums.BusType;
import org.springframework.beans.factory.annotation.Autowired;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.services.impl.BusServiceImpl;

import java.util.List;

@Route("bus-list")

public class BusListView extends VerticalLayout {

    private final Grid<SchoolBusImpl> grid = new Grid<>(SchoolBusImpl.class);
    private final TextField filterText = new TextField();
    private ListDataProvider<SchoolBusImpl> dataProvider;

    private final BusServiceImpl busService;


    public BusListView(BusServiceImpl busService) {
        this.busService = busService;

        List<SchoolBusImpl> buses = busService.getAllBuses();

        dataProvider = new ListDataProvider<>(buses);
        grid.setDataProvider(dataProvider);

        grid.addColumn(SchoolBusImpl::getId).setHeader("ID");
        grid.addColumn(SchoolBusImpl::getName).setHeader("Name");
        grid.addColumn(SchoolBusImpl::getBusType).setHeader("Type");

        filterText.setPlaceholder("Filter by name...");
        filterText.addValidationStatusChangeListener(event->applyFilter());

        Button addBusButton = new Button("Add a new bus",event->openAddBusDialog());

        add(new HorizontalLayout(filterText,addBusButton));

        add(new HorizontalLayout(filterText),grid);
    }

    public void openAddBusDialog(){
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        //TextField typeField = new TextField("Type");
        ComboBox<BusType> typeField = new ComboBox<>("Type");
        typeField.setItems(BusType.values());
        typeField.setPlaceholder("Select bus type");

        Button saveButton = new Button("Save", event->{
            String name = nameField.getValue();
            BusType type = typeField.getValue();

                if (!name.isEmpty()) {
                    SchoolBusImpl newBus = new SchoolBusImpl(type, name);
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

        formLayout.add(nameField,typeField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }



    private void applyFilter(){
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(bus->bus.getName().toLowerCase().contains(filterValue));
    }
}
