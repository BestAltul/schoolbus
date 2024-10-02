package com.wny.schoolbus.pages;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class MainView extends AppLayout {

    private boolean isDrawerOpened = true;

    public MainView() {

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

        RouterLink busesLink = new RouterLink("Buses", BusListView.class);
        RouterLink dashCamLink = new RouterLink("Dash cameras",DashCamListView.class);
        RouterLink simCardLink = new RouterLink("Sim cards", SimCardListView.class);

        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.add(busesLink);
        menuLayout.add(dashCamLink);
        menuLayout.add(simCardLink);
 //       menuLayout.add(new Button("Buses", event -> setContent(createContent("Information about buses"))));
 //       menuLayout.add(new Button("Terminals", event -> setContent(createContent("Information about terminals"))));

        addToDrawer(menuLayout);

        setContent(createContent("Select an option from the menu to see details."));
    }

    private void toggleDrawerState() {
        isDrawerOpened = !isDrawerOpened;
        setDrawerOpened(isDrawerOpened);
    }

    private VerticalLayout createContent(String text) {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(text);
        return contentLayout;
    }
}
