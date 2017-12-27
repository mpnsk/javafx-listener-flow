package com.github.mpnsk;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class Controller {
    public Button button;
    public Pane pane;
    public CheckBox checkbox;

    public void initialize() {
        System.out.println(pane);
        System.out.println(button);


        button.setOnMouseClicked(event -> System.out.println(button));

        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            System.out.println(pane);
        };
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) button.addEventHandler(MOUSE_CLICKED, mouseEventEventHandler);
            else button.removeEventHandler(MOUSE_CLICKED, mouseEventEventHandler);
        });
    }
}
