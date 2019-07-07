package javafx.scaletests.layers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditableRectangleGroup {
    private final ObservableList<EditableRectangle> boundingBoxes = FXCollections.observableArrayList();
    private Color selectedColor = Color.ORANGE;
    private Color notSelectedColor = Color.DEEPSKYBLUE;

    public EditableRectangleGroup() {
//        boundingBoxes.addListener((ListChangeListener<EditableRectangle>) c -> {
//            while (c.next()) {
//                if (c.wasRemoved()) {
//                    for (EditableBoundingBox box : c.getRemoved()) {
//                        for (Rectangle r : box.getShapes()) {
//                            r.removeEventHandler();
//
//                        }
//                    }
//                }
//
//            }
//        });
    }

    private void addEventHandler(EditableRectangle box) {
        EventHandler<MouseEvent> selectedHandler = evt -> {
            Parent parent = box.getParent();
            if (parent instanceof Pane) {
                Pane p = (Pane) parent;
                // Reorder to put our shapes on top
                p.getChildren().removeAll(box.getShapes());
                p.getChildren().addAll(0, box.getShapes());
                box.setColor(selectedColor);

                // Set color of other editabl rectangles
                ArrayList<EditableRectangle> otherRectangles = new ArrayList<>(boundingBoxes);
                otherRectangles.remove(box);
                otherRectangles.forEach(r -> r.setColor(notSelectedColor));
            }
        };
        box.getShapes()
                .forEach(r -> r.addEventHandler(MouseEvent.MOUSE_PRESSED, selectedHandler));
        box.setUserData(selectedHandler);
    }

    public ObservableList<EditableBoundingBox> getBoundingBoxes() {
        return null;
    }

    private List<Rectangle> getAllShapes() {
        return boundingBoxes.stream()
                .flatMap(box -> box.getShapes().stream())
                .collect(Collectors.toList());
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getNotSelectedColor() {
        return notSelectedColor;
    }

    public void setNotSelectedColor(Color notSelectedColor) {
        this.notSelectedColor = notSelectedColor;
    }
}
