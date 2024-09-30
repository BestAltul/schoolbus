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
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;
import org.springframework.beans.factory.annotation.Autowired;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.services.impl.BusServiceImpl;

import java.util.List;

@Route("bus-list")
@CssImport("./styles/styles.css")
public class BusListView extends VerticalLayout {

    private final BusServiceImpl busService;
    private final Grid<SchoolBusImpl> grid = new Grid<>(SchoolBusImpl.class);
    private ListDataProvider<SchoolBusImpl> dataProvider;
    private final Button addBusButton = new Button("Add a new bus");
    private final Button backButton = new Button("Back");
    private final TextField filterText = new TextField();

    public BusListView(BusServiceImpl busService) {
        this.busService = busService;

        // Настройка таблицы с данными
        List<SchoolBusImpl> buses = busService.getAllBuses();
        dataProvider = new ListDataProvider<>(buses);
        grid.setDataProvider(dataProvider);
        grid.removeAllColumns();

        //grid.addColumn(SchoolBusImpl::getId).setHeader("ID");
        grid.addColumn(SchoolBusImpl::getName).setHeader("Name");
        grid.addColumn(SchoolBusImpl::getBusType).setHeader("Type");
        grid.addColumn(SchoolBusImpl::getTerminal).setHeader("Terminal");

        // Кнопка для возврата назад
        backButton.addClickListener(event -> UI.getCurrent().getPage().getHistory().back());

        // Кнопка для добавления нового автобуса
        addBusButton.addClickListener(event -> openAddBusDialog());

        // Настройка фильтра
        filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(event -> applyFilter());

        // Горизонтальный макет для фильтра и кнопки добавления автобуса
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBusButton);
        toolbar.setWidthFull();  // Занять всю ширину
        toolbar.setSpacing(true);  // Добавить отступы между элементами

        grid.addItemDoubleClickListener(event->openEditDialog(event.getItem()));

        // Добавляем компоненты на страницу
        add(backButton, toolbar, grid);

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

        Button saveButton = new Button("Save", event->{
            String name = nameField.getValue();
            BusType type = typeField.getValue();
            Terminal terminal = terminalField.getValue();

                if (!name.isEmpty()) {
                    SchoolBusImpl newBus = new SchoolBusImpl(type, name,terminal);
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

        formLayout.add(nameField,typeField,terminalField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }

    private void openEditDialog(SchoolBusImpl bus) {

        FormLayout formLayout = new FormLayout();

        Dialog dialog = new Dialog();
        TextField nameField = new TextField("Name", bus.getName());

        ComboBox<BusType> typeField = new ComboBox<>("Type");
        typeField.setItems(BusType.values());
        typeField.setValue(bus.getBusType());

        ComboBox<Terminal> terminalField = new ComboBox<>("Terminal");
        terminalField.setItems(Terminal.values());
        terminalField.setValue(bus.getTerminal());

        Button saveButton = new Button("Save", event -> {
            bus.setName(nameField.getValue());
            bus.setBusType(typeField.getValue());
            bus.setTerminal(terminalField.getValue());

            busService.save(bus);  // Сохраняем изменения
            dataProvider.refreshAll();  // Обновляем данные в Grid
            Notification.show("Bus updated!");
            dialog.close();
        });

        Button cancelButton = new Button("Cancel",event->dialog.close());

        formLayout.add(nameField,typeField,terminalField);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();
    }

    private void applyFilter(){
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(bus->bus.getName().toLowerCase().contains(filterValue));
    }
}
