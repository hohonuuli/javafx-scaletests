package javafx.scaletests.layers;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditableRectangle extends Rectangle {

    private int offset = 5;
    private int square = offset * 2 + 1;

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



    public EditableRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        getStyleClass().add("mbari-bounding-box");
        setStrokeWidth(2);
    }

    public void setColor(Color color) {
        Color fillColor = Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.1);
        getShapes().forEach(r -> {
            r.setStroke(color);
            r.setFill(fillColor);
        });
    }

    public List<Rectangle> getShapes() {
        List<Rectangle> shapes = new ArrayList<>();
        shapes.add(this);
        shapes.addAll(getControlPoints());

        return shapes;
    }

    public List<Rectangle> getControlPoints() {
        if (controlPoints == null) {
            List<Rectangle> points = new ArrayList<>();
            Rectangle b = this;
            points.add(buildUpperLeftControlPoint(b));
            points.add(buildUpperRightControlPoint(b));
            points.add(buildLowerLeftControlPoint(b));
            points.add(buildLowerRightControlPoint(b));
            controlPoints = Collections.unmodifiableList(points);
        }
        return controlPoints;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        square = offset * 2 + 1;
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
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        r.addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        r.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        r.xProperty()
                .addListener((obs, oldX, newX) -> {
                    if (r == dragSource) {
                        setLeftX(newX.doubleValue());
                    }
                });
        r.yProperty()
                .addListener((obs, oldY, newY) -> {
                    if (r == dragSource) {
                        setUpperY(newY.doubleValue());
                    }
                });

        return r; // -- WORKING
    }

    private Rectangle buildUpperRightControlPoint(Rectangle b) {
        // x, y , width
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - offset,
                b.getY() - offset,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() - offset));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - offset));
        r.addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        r.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        r.xProperty()
                .addListener((obs, oldX, newX) -> {
                    if (r == dragSource) {
                        setRightX(newX.doubleValue());
                    }
                });
        r.yProperty()
                .addListener((obs, oldY, newY) -> {
                    if (r == dragSource) {
                        setUpperY(newY.doubleValue());
                    }
                });
        return r; // -- WORKING
    }

    private Rectangle buildLowerLeftControlPoint(Rectangle b) {
        // x, y, height
        Rectangle r = new Rectangle(b.getX() - offset,
                b.getY() + b.getHeight() - offset,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - offset));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - offset));
        r.addEventHandler(MouseEvent.MOUSE_ENTERED, evt -> r.toFront());
        r.addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        r.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        r.xProperty()
                .addListener((obs, oldX, newX) -> {
                    if (r == dragSource) {
                        setLeftX(newX.doubleValue());
                    }
                });
        r.yProperty()
                .addListener((obs, oldY, newY) -> {
                    if (r == dragSource) {
                        setLowerY(newY.doubleValue());
                    }
                });
        return r; // -- WORKING
    }

    private Rectangle buildLowerRightControlPoint(Rectangle b) {
        // x, y, width, height
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - offset,
                b.getY() + b.getHeight() - offset,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - offset));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - offset));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - offset));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - offset));
        r.addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        r.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        r.xProperty()
                .addListener((obs, oldX, newX) -> {
                    if (r == dragSource) {
                        setRightX(newX.doubleValue());
                    }
                });
        r.yProperty()
                .addListener((obs, oldY, newY) -> {
                    if (r == dragSource) {
                        setLowerY(newY.doubleValue());
                    }
                });
        return r;

    }

    private void setUpperY(double y) {
        double centerY = y + offset;
        double boxHeight = getHeight() + (getY() - centerY);
        setY(centerY);
        setHeight(boxHeight);
    }

    private void setLowerY(double y) {
        double centerY = y + offset;
        double boxHeight = centerY - getY();
        setHeight(boxHeight);
    }

    private void setLeftX(double x) {
        double centerX = x + offset;
        double boxWidth = getWidth() + (getX() - centerX);
        setX(centerX);
        setWidth(boxWidth);
    }

    private void setRightX(double x) {
        double centerX = x + offset;
        double boxWidth = centerX - getX();
        setWidth(boxWidth);
    }

}
