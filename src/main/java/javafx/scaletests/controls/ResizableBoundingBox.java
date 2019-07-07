package javafx.scaletests.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResizableBoundingBox extends Control {

    private DoubleProperty boxX = new SimpleDoubleProperty();
    private DoubleProperty boxY = new SimpleDoubleProperty();
    private DoubleProperty boxWidth = new SimpleDoubleProperty();
    private DoubleProperty boxHeight = new SimpleDoubleProperty();
    private final ResizeableBoundingBoxSkin skin;
    private Color focusedColor = Color.ORANGE;
    private Color notFocusedColor = Color.DEEPSKYBLUE;

    public ResizableBoundingBox(double x, double y, double width, double height) {
        super();
        boxX.set(x);
        boxY.set(y);
        boxWidth.set(width);
        boxHeight.set(height);
        skin = new ResizeableBoundingBoxSkin(this);
        setColor(notFocusedColor);
        focusedProperty().addListener((obs, oldv, newv) -> {
            if (newv) {
                setColor(focusedColor);
            }
            else {
                setColor(notFocusedColor);
            }
        });
        addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> requestFocus());
    }

    public Color getFocusedColor() {
        return focusedColor;
    }

    public void setFocusedColor(Color focusedColor) {
        this.focusedColor = focusedColor;
        if (isFocused()) {
            setColor(focusedColor);
        }
    }

    public Color getNotFocusedColor() {
        return notFocusedColor;
    }

    public void setNotFocusedColor(Color notFocusedColor) {
        this.notFocusedColor = notFocusedColor;
        if (!isFocused()) {
            setColor(notFocusedColor);
        }
    }

    private void setColor(Color color) {
        skin.getEditableBoundingBox()
                .setColor(color);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return skin;
    }

    public double getBoxX() {
        return boxX.get();
    }

    public DoubleProperty boxXProperty() {
        return boxX;
    }

    public double getBoxY() {
        return boxY.get();
    }

    public DoubleProperty boxYProperty() {
        return boxY;
    }

    public double getBoxWidth() {
        return boxWidth.get();
    }

    public DoubleProperty boxWidthProperty() {
        return boxWidth;
    }

    public double getBoxHeight() {
        return boxHeight.get();
    }

    public DoubleProperty boxHeightProperty() {
        return boxHeight;
    }
}

class ResizeableBoundingBoxSkin extends SkinBase<ResizableBoundingBox> {

    private EditableBoundingBox editableBoundingBox;

    public ResizeableBoundingBoxSkin(ResizableBoundingBox control) {
        super(control);
        editableBoundingBox = new EditableBoundingBox(control.getBoxX(),
                control.getBoxY(),
                control.getBoxWidth(),
                control.getBoxHeight());
        Rectangle r = editableBoundingBox.getBoundingBoxRectangle();
        r.xProperty().bindBidirectional(control.boxXProperty());
        r.yProperty().bindBidirectional(control.boxYProperty());
        r.widthProperty().bindBidirectional(control.boxWidthProperty());
        r.heightProperty().bindBidirectional(control.boxHeightProperty());
        getChildren().addAll(editableBoundingBox.getShapes());
    }

    EditableBoundingBox getEditableBoundingBox() {
        return editableBoundingBox;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        Rectangle boundingBoxRectangle = editableBoundingBox.getBoundingBoxRectangle();
//       boundingBoxRectangle.setX(contentX + boundingBoxRectangle.getX());
//       boundingBoxRectangle.setY(contentY + boundingBoxRectangle.getY());
        boundingBoxRectangle.relocate(contentX + boundingBoxRectangle.getX(),
                contentY + boundingBoxRectangle.getY());
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return EditableBoundingBox.OFFSET * 2 + editableBoundingBox.getBoundingBoxRectangle().getWidth();
    }


    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return EditableBoundingBox.OFFSET * 2 + editableBoundingBox.getBoundingBoxRectangle().getHeight();
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computeMinWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computeMinHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}

class EditableBoundingBox {

    static final int OFFSET = 5;
    private int square = OFFSET * 2 + 1;

    private final Rectangle boundingBoxRectangle;
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


    EditableBoundingBox(double x, double y, double width, double height) {
        boundingBoxRectangle = new Rectangle(x, y, width, height);
        boundingBoxRectangle.addEventHandler(MouseEvent.MOUSE_PRESSED,
                mousePressedHandler);
        boundingBoxRectangle.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseDraggedHandler);
        boundingBoxRectangle.getStyleClass().add("mbari-bounding-box");
        boundingBoxRectangle.setStrokeWidth(2);
    }

    void setColor(Color color) {
        Color fillColor = Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.1);
        getShapes().forEach(r -> {
            r.setStroke(color);
            r.setFill(fillColor);
        });
    }

    List<Rectangle> getShapes() {
        List<Rectangle> shapes = new ArrayList<>();
        shapes.add(getBoundingBoxRectangle());
        shapes.addAll(getControlPoints());

        return shapes;
    }

    Rectangle getBoundingBoxRectangle() {
        return boundingBoxRectangle;
    }

    List<Rectangle> getControlPoints() {
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
        // boxX, boxY
        Rectangle r = new Rectangle(b.getX() - OFFSET,
                b.getY() - OFFSET,
                square,
                square);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() - OFFSET));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() - OFFSET));
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
        // boxX, boxY , boxWidth
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - OFFSET,
                b.getY() - OFFSET,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - OFFSET));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() - OFFSET));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - OFFSET));
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
        // boxX, boxY, boxHeight
        Rectangle r = new Rectangle(b.getX() - OFFSET,
                b.getY() + b.getHeight() - OFFSET,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() - OFFSET));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - OFFSET));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - OFFSET));
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
        // boxX, boxY, boxWidth, boxHeight
        Rectangle r = new Rectangle(b.getX() + b.getWidth() - OFFSET,
                b.getY() + b.getHeight() - OFFSET,
                square,
                square);
        r.getStyleClass().add("mbari-bounding-box-control");
        r.setStrokeWidth(1);
        b.xProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getWidth() - OFFSET));
        b.yProperty()
                .addListener((obs, oldv, newv) -> r.setY(newv.doubleValue() + b.getHeight() - OFFSET));
        b.widthProperty()
                .addListener((obs, oldv, newv) -> r.setX(newv.doubleValue() + b.getX() - OFFSET));
        b.heightProperty()
                .addListener((obs, oldv, newv) -> r.setY(b.getY() + newv.doubleValue()  - OFFSET));
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
        double centerY = y + OFFSET;
        double boxHeight = boundingBoxRectangle.getHeight() + (boundingBoxRectangle.getY() - centerY);
        boundingBoxRectangle.setY(centerY);
        boundingBoxRectangle.setHeight(boxHeight);
    }

    private void setLowerY(double y) {
        double centerY = y + OFFSET;
        double boxHeight = centerY - boundingBoxRectangle.getY();
        boundingBoxRectangle.setHeight(boxHeight);
    }

    private void setLeftX(double x) {
        double centerX = x + OFFSET;
        double boxWidth = boundingBoxRectangle.getWidth() + (boundingBoxRectangle.getX() - centerX);
        boundingBoxRectangle.setX(centerX);
        boundingBoxRectangle.setWidth(boxWidth);
    }

    private void setRightX(double x) {
        double centerX = x + OFFSET;
        double boxWidth = centerX - boundingBoxRectangle.getX();
        boundingBoxRectangle.setWidth(boxWidth);
    }

}

