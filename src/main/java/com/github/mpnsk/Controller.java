package com.github.mpnsk;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Stack;

public class Controller {
    public Button button1, forward, backward;
    public Pane pane;
    public CheckBox checkbox;
    public Label label;
    private Runnable stage1;

    public void initialize() {


        Stack<Runnable> stack = new Stack<>();

        Runnable stage4 = () -> {
            label.setText("stage 4");
            button1.setOnMouseClicked(event -> {
                button1.setOnMouseClicked(Event::consume);
                stack.empty();
                stack.push(stage1).run();
            });
        };

        Runnable stage3 = () -> {
            label.setText("stage3");
            forward.setOnMouseClicked(event -> {
                forward.setOnMouseClicked(Event::consume);
                stack.push(stage4).run();
            });
            backward.setOnMouseClicked(event -> {
                backward.setOnMouseClicked(Event::consume);
                stack.pop();
                stack.peek().run();

            });
        };
        Runnable stage2 = () -> {
            label.setText("stage2");
            forward.setOnMouseClicked(event -> {
                forward.setOnMouseClicked(Event::consume);
                stack.push(stage3).run();
            });
        };
        stage1 = () -> {
            label.setText("stage1");
            button1.setOnMouseClicked(event -> {
                button1.setOnMouseClicked(Event::consume);
                stack.push(stage2).run();
            });

        };
        stack.add(stage1);
        stack.peek().run();
    }

    public void mousePressed(MouseEvent mouseEvent) {

    }
}
