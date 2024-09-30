package com.wny.schoolbus.pages;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends AppLayout {

    public MainView() {
        // Добавляем верхний логотип
        Image logo = new Image("https://example.com/logo.png", "Logo");
        logo.setWidth("150px");  // Задаем размер картинки

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();  // Занимаем всю ширину
        header.add(logo);  // Добавляем логотип в верхнюю панель

        // Меню в левой боковой панели
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.add(new Button("Buses", event -> setContent(createContent("Information about buses"))));
        menuLayout.add(new Button("Terminals", event -> setContent(createContent("Information about terminals"))));

        // Добавляем меню в боковую панель
        addToDrawer(menuLayout);

        // Добавляем логотип в верхнюю панель
        addToNavbar(header);

        // Стандартное приветствие по умолчанию
        setContent(createContent("Select an option from the menu to see details."));
    }

    // Метод для создания контента в правой части
    private VerticalLayout createContent(String text) {
        VerticalLayout contentLayout = new VerticalLayout();
        Span description = new Span(text);  // Используем Span вместо Label
        contentLayout.add(description);
        return contentLayout;
    }
}

