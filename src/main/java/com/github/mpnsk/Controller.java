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
    private Runnable stage1;
    private Line line;

    public void initialize() {

        Stack<Runnable> stack = new Stack<>();

        Runnable rectangleMouseEntered = () -> {
            highlightRectangle();
            label.setText("rectangleMouseEntered");
            rectangle.setOnMouseExited(event -> {
                rectangle.setOnMouseExited(null);
                System.out.println(event.getEventType());
                assert line != null;
                removeLine(line);
                stack.pop();
                stack.peek().run();
            });
        };
        Runnable s2DraggingLine = () -> {
            label.setText("s2DraggingLine");
            line = createLine();
            rectangle.setOnMouseEntered(event -> {
                rectangle.setOnMouseEntered(null);
                System.out.println(event.getEventType());
                stack.push(rectangleMouseEntered).run();
            });
            pane.setOnMouseClicked(event -> {
                pane.setOnMouseClicked(null);
                rectangle.setOnMouseEntered(null);
                removeLine(line);
                System.out.println(event.getEventType());
                label.setText("no stage");
                stack.empty();
                assert stage1 != null;
                stack.push(stage1).run();
            });
        };
        stage1 = () -> {
            label.setText("stage1");
            circle.setOnMouseClicked(event -> {
                circle.setOnMouseClicked(null);
                event.consume();
                System.out.println(event.getEventType());
                stack.push(s2DraggingLine).run();
            });

        };
        stack.push(stage1).run();
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
