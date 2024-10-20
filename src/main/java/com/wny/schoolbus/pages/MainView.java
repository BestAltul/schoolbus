//package com.wny.schoolbus.pages;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.applayout.AppLayout;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.tabs.Tab;
//import com.vaadin.flow.component.tabs.Tabs;
//import com.wny.schoolbus.services.EmployeeService;
//import com.wny.schoolbus.services.impl.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
//
//@org.springframework.stereotype.Component
//@Route("")
//public class MainView extends AppLayout {
//
//    private Div contentArea;
//    private BusServiceImpl busService;
//    private DashCamServiceImpl dashCamService;
//    private RadioServiceImpl radioService;
//    private SimCardServiceImpl simCardService;
//    private SchoolBusHistoryServiceImpl schoolBusHistoryService;
//    private EmployeeServiceImpl employeeService;
//
//    private RestTemplate restTemplate;
//
//    @Autowired
//    public MainView(BusServiceImpl busService,DashCamServiceImpl dashCamService, RadioServiceImpl radioService, SimCardServiceImpl simCardService,SchoolBusHistoryServiceImpl schoolBusHistoryService, EmployeeServiceImpl employeeService,RestTemplate restTemplate) {
//        this.busService = busService;
//        this.dashCamService = dashCamService;
//        this.simCardService = simCardService;
//        this.radioService   = radioService;
//        this.schoolBusHistoryService = schoolBusHistoryService;
//        this.employeeService = employeeService;
//        this.restTemplate = restTemplate;
//
//        initHeader();
//        initDrawer();
//        initContentArea();
//    }
//
//    private void initHeader() {
//        Image logo = new Image("images/logo.png", "Logo");
//        logo.setWidth("150px");
//
//        Icon menuIcon = new Icon(VaadinIcon.MENU);
//        Button toggleDrawerButton = new Button(menuIcon, event -> toggleDrawerState());
//
//        toggleDrawerButton.setWidth("50px");
//        toggleDrawerButton.setHeight("50px");
//
//        HorizontalLayout header = new HorizontalLayout(toggleDrawerButton, logo);
//        header.setWidthFull();
//        header.setAlignItems(Alignment.CENTER);
//
//        addToNavbar(header);
//    }
//
//    private void initDrawer() {
//        Tab busesTab = new Tab("Buses");
//        Tab dashCamTab = new Tab("Dash cameras");
//        Tab radioTab = new Tab("Radios");
//        Tab simCardTab = new Tab("Sim cards");
//        Tab employeeTab = new Tab("Employees");
//
//        Tabs tabs = new Tabs(busesTab, dashCamTab, radioTab,simCardTab,employeeTab);
//        tabs.setOrientation(Tabs.Orientation.VERTICAL);
//
//        tabs.addSelectedChangeListener(event -> {
//            Tab selectedTab = event.getSelectedTab();
//
//            contentArea.removeAll();
//
//            if (selectedTab.equals(busesTab)) {
//                replaceContent(new BusListView(busService,dashCamService,radioService,schoolBusHistoryService));
//            } else if (selectedTab.equals(dashCamTab)) {
//                replaceContent(new DeviceListView("dashcam",dashCamService,radioService,simCardService));
//            } else if (selectedTab.equals(radioTab)) {
//                replaceContent(new DeviceListView("radio",dashCamService,radioService,simCardService));
//            } else if (selectedTab.equals(simCardTab)) {
//                replaceContent(new SimCardListView(simCardService));
//            } else if (selectedTab.equals(employeeTab)) {
//                replaceContent(new EmployeeListView(employeeService,restTemplate));
//            }
//        });
//
//        VerticalLayout menuLayout = new VerticalLayout(tabs);
//        addToDrawer(menuLayout);
//    }
//
//    private void initContentArea() {
//        contentArea = new Div();
//        contentArea.setSizeFull();
//        setContent(contentArea);
//
//        replaceContent(createContent("Select an option from the menu to see details"));
//    }
//
//    private void toggleDrawerState() {
//        setDrawerOpened(!isDrawerOpened());
//    }
//
//    private Component createContent(String text) {
//        Div contentLayout = new Div();
//        contentLayout.add(text);
//        return contentLayout;
//    }
//
//    private void replaceContent(Component content) {
//        contentArea.removeAll();
//        contentArea.add(content);
//    }
//}
