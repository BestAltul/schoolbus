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
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.enums.SimCardCarrier;
import com.wny.schoolbus.enums.SimCardType;
import com.wny.schoolbus.services.impl.SimCardServiceImpl;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

@Route("sim-card-list")
public class SimCardListView extends VerticalLayout {

    private final SimCardServiceImpl simCardService;
    private final Grid<SimCardImpl> grid = new Grid<>(SimCardImpl.class);
    private ListDataProvider<SimCardImpl> dataProvider;
    private final Button addSimCardButton = new Button("Add a new SIM card");
    private final Button backButton = new Button("Back");
    private final TextField filterText = new TextField();

    public SimCardListView(SimCardServiceImpl simCardService) {
        this.simCardService = simCardService;

        List<SimCardImpl> simCards = simCardService.getAllSimCards();
        dataProvider = new ListDataProvider<>(simCards);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();

        grid.addColumn(SimCardImpl::getId).setHeader("ID");
        grid.addColumn(SimCardImpl::getSimCardNumber).setHeader("SIM Card Number");
        grid.addColumn(SimCardImpl::getSimCardType).setHeader("SIM Card Type");
        grid.addColumn(SimCardImpl::getSimCardCarrier).setHeader("Carrier");


        backButton.addClickListener(event -> UI.getCurrent().getPage().getHistory().back());


        addSimCardButton.addClickListener(event -> openAddSimCardDialog());


        filterText.setPlaceholder("Filter by number...");
        filterText.addValueChangeListener(event -> applyFilter());


        HorizontalLayout toolbar = new HorizontalLayout(filterText, addSimCardButton);
        toolbar.setWidthFull();
        toolbar.setSpacing(true);

        grid.addItemDoubleClickListener(event->openEditSimCardDialog(event.getItem()));

        add(backButton, toolbar, grid);

    }

    private void openAddSimCardDialog() {
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField simCardNumberField = new TextField("SIM Card Number");

        ComboBox<SimCardType> simCardTypeField = new ComboBox<>("SIM Card Type");
        simCardTypeField.setItems(SimCardType.values());
        simCardTypeField.setPlaceholder("Select SIM card type");

        ComboBox<SimCardCarrier> simCardCarrierField = new ComboBox<>("Carrier");
        simCardCarrierField.setItems(SimCardCarrier.values());
        simCardCarrierField.setPlaceholder("Select carrier");

        Button saveButton = new Button("Save", event -> {
            String simCardNumber = simCardNumberField.getValue();
            SimCardType simCardType = simCardTypeField.getValue();
            SimCardCarrier simCardCarrier = simCardCarrierField.getValue();

            if (!simCardNumber.isEmpty() && simCardType != null && simCardCarrier != null) {
                SimCardImpl newSimCard = new SimCardImpl( simCardType, simCardCarrier, simCardNumber);
                simCardService.save(newSimCard);

                dataProvider.getItems().add(newSimCard);
                dataProvider.refreshAll();

                Notification.show("SIM card added!");
                dialog.close();
            } else {
                Notification.show("Please fill in all fields!");
            }
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        formLayout.add(simCardNumberField, simCardTypeField, simCardCarrierField);
        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));

        dialog.open();
    }

    private void openEditSimCardDialog(SimCardImpl simCard){
        Dialog dialog = new Dialog();

        Binder<SimCardImpl> binder = new Binder<>(SimCardImpl.class);

        FormLayout formLayout = new FormLayout();
        TextField simCardNumberField = new TextField("Sim card number",simCard.getSimCardNumber());

        ComboBox<SimCardType> simCardTypeComboBox = new ComboBox<>("Type of sim card");
        simCardTypeComboBox.setItems(SimCardType.values());
        simCardTypeComboBox.setValue(simCard.getSimCardType());

        ComboBox<SimCardCarrier> simCardCarrierComboBox = new ComboBox<>("Carrier");
        simCardCarrierComboBox.setItems(SimCardCarrier.values());
        simCardCarrierComboBox.setValue(simCard.getSimCardCarrier());

        binder.forField(simCardNumberField)
                .asRequired("Sim card number is required")
                .bind(SimCardImpl::getSimCardNumber,SimCardImpl::setSimCardNumber);
        binder.forField(simCardTypeComboBox)
                .asRequired("Sim card type is required")
                .bind(SimCardImpl::getSimCardType,SimCardImpl::setSimCardType);
        binder.forField(simCardCarrierComboBox)
                .asRequired("Sim card carrier is required")
                .bind(SimCardImpl::getSimCardCarrier,SimCardImpl::setSimCardCarrier);

        binder.setBean(simCard);

        Button saveButton = new Button("Save",event ->{
            if (binder.validate().isOk()) {
                simCardService.save(simCard);
                dataProvider.refreshAll();
                Notification.show("Sim card is updated");
                dialog.close();
            }else{
                Notification.show("Please fill all reqiured fields!");
            }
        });

        Button cancelButton = new Button("Cancel",event->dialog.close());

        formLayout.add(simCardNumberField,simCardTypeComboBox,simCardCarrierComboBox);
        dialog.add(formLayout,new HorizontalLayout(saveButton,cancelButton));

        dialog.open();

    }

    private void applyFilter() {
        String filterValue = filterText.getValue().trim().toLowerCase();
        dataProvider.setFilter(simCard -> simCard.getSimCardNumber().toLowerCase().contains(filterValue));
    }
}
