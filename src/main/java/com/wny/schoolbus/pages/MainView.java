package com.wny.schoolbus.pages;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

@Route("")
public class MainView extends AppLayout {

    private boolean isDrawerOpened = true;
    private Div contentArea;

    public MainView() {

        try{

            Image logo = new Image("images/logo.png", "Logo");
            logo.setWidth("150px");

            Icon menuIcon = new Icon(VaadinIcon.MENU);
            Button toggleDrawerButton = new Button(menuIcon, event -> toggleDrawerState());

            toggleDrawerButton.setWidth("50px");
            toggleDrawerButton.setHeight("50px");

            HorizontalLayout header = new HorizontalLayout(toggleDrawerButton, logo);
            header.setWidthFull();  // Занимаем всю ширину панели
            header.setAlignItems(Alignment.CENTER);

            addToNavbar(header);

            Tab busesTab = new Tab("Buses");
            Tab dashCamTab = new Tab("Dash cameras");
            Tab simCardTab = new Tab("Sim cards");

            Tabs tabs = new Tabs(busesTab,dashCamTab,simCardTab);
            tabs.setOrientation(Tabs.Orientation.VERTICAL);

            tabs.addSelectedChangeListener(event->{
                Tab selectedTab = event.getSelectedTab();
                if(selectedTab.equals(busesTab)){
                    setContent(createContent("Information about buses"));
                }else if(selectedTab.equals(dashCamTab)){
                    setContent(createContent("Information about dash cameras"));
                }else if(selectedTab.equals(simCardTab)){
                    setContent(createContent("Information about sim cards"));
                }
            });

            VerticalLayout menuLayout = new VerticalLayout(tabs);
            addToDrawer(menuLayout);

            contentArea = new Div();
            contentArea.setSizeFull();
            setContent(createContent("Select an option from the menu to see details"));

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }

    }

    private void toggleDrawerState() {
        isDrawerOpened = !isDrawerOpened;
        setDrawerOpened(isDrawerOpened);
    }

    private Component createContent(String text) {
        Div contentLayout = new Div();
        contentLayout.add(text);
        return contentLayout;
    }
    public void setContent(Component content) {
        contentArea.removeAll();
        contentArea.add(content);
    }
}
