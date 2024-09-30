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

    private boolean isDrawerOpened = true; // Текущее состояние панели

    public MainView() {
        // Логотип в верхней панели
        Image logo = new Image("https://example.com/logo.png", "Logo");
        logo.setWidth("150px");

        // Кнопка для управления боковой панелью
        Icon menuIcon = new Icon(VaadinIcon.MENU);
        Button toggleDrawerButton = new Button(menuIcon, event -> toggleDrawerState());

        // Настраиваем размер кнопки
        toggleDrawerButton.setWidth("50px");
        toggleDrawerButton.setHeight("50px");

        // Горизонтальный макет для кнопки и логотипа
        HorizontalLayout header = new HorizontalLayout(toggleDrawerButton, logo);
        header.setWidthFull();  // Занимаем всю ширину панели
        header.setAlignItems(Alignment.CENTER);

        // Добавляем логотип и кнопку в верхнюю панель
        addToNavbar(header);

        RouterLink busesLink = new RouterLink("Buses", BusListView.class);
        //RouterLink terminalsLink = new RouterLink("Terminals",Terminals)

        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.add(busesLink);
 //       menuLayout.add(new Button("Buses", event -> setContent(createContent("Information about buses"))));
 //       menuLayout.add(new Button("Terminals", event -> setContent(createContent("Information about terminals"))));

        // Добавляем меню в боковую панель
        addToDrawer(menuLayout);

        // Добавляем контент по умолчанию
        setContent(createContent("Select an option from the menu to see details."));
    }

    // Метод для переключения состояния панели
    private void toggleDrawerState() {
        isDrawerOpened = !isDrawerOpened;
        setDrawerOpened(isDrawerOpened);  // Открыть/закрыть боковую панель
    }

    // Метод для создания контента в правой части
    private VerticalLayout createContent(String text) {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(text);
        return contentLayout;
    }
}
