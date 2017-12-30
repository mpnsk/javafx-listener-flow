package com.github.mpnsk;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

public class Controller {
    public Pane pane;
    public Label label;
    public Circle circle;
    public Rectangle rectangle;
    private Runnable stage1_init;
    private Line line;

    public void initialize() {

        Stack<Runnable> stack = new Stack<>();

        Runnable stage3_enter_rectangle = () -> {
            highlightRectangle();
            label.setText("stage3_enter_rectangle");
            rectangle.setOnMouseExited(event -> {
                rectangle.setOnMouseExited(null);
                System.out.println(event.getEventType());
                removeLine(line);
                stack.pop();
                stack.peek().run();
            });
        };
        Runnable stage2_create_line = () -> {
            label.setText("stage2_create_line ");
            line = createLine();
            rectangle.setOnMouseEntered(event -> {
                rectangle.setOnMouseEntered(null);
                System.out.println(event.getEventType() + " : " + event.getPickResult().getIntersectedNode());
                stack.push(stage3_enter_rectangle).run();
            });
            pane.setOnMouseClicked(event -> {
                pane.setOnMouseClicked(null);
                rectangle.setOnMouseEntered(null);
                removeLine(line);
                System.out.println(event.getEventType() + " : " + event.getPickResult().getIntersectedNode());
                label.setText("no stage");
                stack.empty();
                stack.push(stage1_init).run();
            });
        };
        stage1_init = () -> {
            label.setText("stage1_init");
            circle.setOnMouseClicked(event -> {
                circle.setOnMouseClicked(null);
                event.consume();
                System.out.println(event.getEventType() + " : " + event.getPickResult().getIntersectedNode());
                stack.push(stage2_create_line ).run();
            });

        };
        stack.push(stage1_init).run();
    }

    private void highlightRectangle() {
        rectangle.setHeight(rectangle.getHeight() + 15);
        rectangle.setWidth(rectangle.getWidth() + 15);
    }

    private void removeLine(Line line) {
        line.startXProperty().unbind();
        line.startYProperty().unbind();
        pane.getChildren().remove(line);
        pane.setOnMouseMoved(null);
    }

    private Line createLine() {
        Line line = new Line();
        line.setStrokeWidth(3);
        line.setMouseTransparent(true);
        line.startXProperty().bind(circle.centerXProperty().add(circle.getLayoutX()));
        line.startYProperty().bind(circle.centerYProperty().add(circle.getLayoutY()));
        pane.setOnMouseMoved(event -> {
            line.setEndX(event.getSceneX());
            line.setEndY(event.getSceneY());
        });
        pane.getChildren().add(line);
        return line;
    }

}
