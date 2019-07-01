package javafx.scaletests.layers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoundingBox {
    private double x;
    private double y;
    private double width;
    private double height;
    private int offset = 5;
    private int square = offset * 2 + 1;



    private Rectangle boundingBoxRectangle;
    private List<Rectangle> controlPoints;

    // Drag variables
    private Rectangle dragSource;
    private double origMouseX;
    private double origMouseY;
    private double origX;
    private double origY;
    private EventHandler<MouseEvent> mousePressedHandler = evt -> {
        dragSource = (Rectangle) evt.getSource();
        origMouseX = evt.getSceneX();
        origMouseY = evt.getSceneY();
        origX = ((Rectangle) evt.getSource()).getX();
        origY = ((Rectangle) evt.getSource()).getY();
    };
    private EventHandler<MouseEvent> mouseDraggedHandler = evt -> {
        if (dragSource == null || dragSource != evt.getSource()) {
            mousePressedHandler.handle(evt);
        }
        double dx = evt.getSceneX() - origMouseX;
        double dy = evt.getSceneY() - origMouseY;
        double newX = origX + dx;
        double newY = origY + dy;
        ((Rectangle) evt.getSource()).setX(newX);
        ((Rectangle) evt.getSource()).setY(newY);
    };


    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public List<Rectangle> getShapes() {
        List<Rectangle> shapes = new ArrayList<>();
        shapes.add(getBoundingBoxRectangle());
        shapes.addAll(getControlPoints());

        return shapes;
    }

    public Rectangle getBoundingBoxRectangle() {
        if (boundingBoxRectangle == null) {
            boundingBoxRectangle = new Rectangle(x, y, width, height);
            boundingBoxRectangle.setOnMousePressed(mousePressedHandler);
            boundingBoxRectangle.setOnMouseDragged(mouseDraggedHandler);
        }
        return boundingBoxRectangle;
    }

    public List<Rectangle> getControlPoints() {
        if (controlPoints == null) {
            List<Rectangle> points = new ArrayList<>();
            Rectangle b = getBoundingBoxRectangle();
            points.add(buildUpperLeftControlPoint(b));
            points.add(buildUpperRightControlPoint(b));
            points.add(buildLowerLeftControlPoint(b));
            points.add(buildLowerRightControlPoint(b));
            controlPoints = Collections.unmodifiableList(points);
        }
        return controlPoints;
    }

    private Rectangle buildUpperLeftControlPoint(Rectangle b) {
        // x, y
        Rectangle r = new Rectangle(b.getX() - offset,
                b.getY() - offset,
                square,
                square);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() - offset));
        b.setOnMouseEntered(evt -> b.toFront());
        b.setOnMousePressed(mousePressedHandler);
        b.setOnMouseDragged(mouseDraggedHandler);
//        r.xProperty()
//                .addListener((obs, oldX, newX) -> {
//                    b.setX(newX.doubleValue() + offset);
//                    double newWidth = oldX.doubleValue() - newX.doubleValue() + b.getWidth() - offset;
//                    b.setWidth(newWidth);
//                });
//        r.yProperty()
//                .addListener((obs, oldv, newv) -> {
//                    b.setY(newv.doubleValue() + offset);
//                    double height = oldv.doubleValue() + newv.doubleValue() + b.getHeight() - offset;
//                    b.setHeight(height);
//                });
        r.setOnMouseClicked(mousePressedHandler);
        r.setOnMouseDragged(mouseDraggedHandler);


        return r;
    }

    private Rectangle buildUpperRightControlPoint(Rectangle b) {
        // x, y , width
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - offset,
                b.getY() - offset,
                square,
                square);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() - offset));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - offset));
        b.setOnMouseEntered(evt -> b.toFront());

        return r;
    }

    private Rectangle buildLowerLeftControlPoint(Rectangle b) {
        // x, y, height
        Rectangle r = new Rectangle(b.getX() - offset,
                b.getY() + b.getHeight() - offset,
                square,
                square);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - offset));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - offset));
        b.setOnMouseEntered(evt -> b.toFront());
        return r;
    }

    private Rectangle buildLowerRightControlPoint(Rectangle b) {
        // x, y, width, height
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - offset,
                b.getY() + b.getHeight() - offset,
                square,
                square);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - offset));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - offset));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - offset));
        b.setOnMouseEntered(evt -> b.toFront());

        return r;


    }


}
