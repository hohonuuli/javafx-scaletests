package javafx.scaletests.controls;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ResizableBoundingBoxGroup {

    private final ObservableList<ResizableBoundingBox> boundingBoxes = FXCollections.observableArrayList();
    private Color selectedColor = Color.ORANGE;
    private Color notSelectedColor = Color.DEEPSKYBLUE;


    EventHandler<MouseEvent> selectHandler = evt -> {
        Object source = evt.getSource();
        System.out.println(source);
        if (source instanceof ResizableBoundingBox) {
            ResizableBoundingBox box = (ResizableBoundingBox) source;
            Parent parent = box.getParent();
            if (parent instanceof Pane) {
                Pane pane = (Pane) parent;
                ObservableList<Node> children = pane.getChildren();
                if (children.get(0) != box) {
                    children.remove(box);
                    children.add(0, box);
                }
                box.requestFocus();
            }
        }
    };



    public ResizableBoundingBoxGroup() {
        init();
    }

    public ObservableList<ResizableBoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    private void init() {
        boundingBoxes.addListener((ListChangeListener<ResizableBoundingBox>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(box -> {
                        box.removeEventHandler(MouseEvent.MOUSE_PRESSED,
                                selectHandler);
                    });
                }
                else if (c.wasAdded()) {
                    c.getAddedSubList()
                            .forEach(b -> {
                                b.addEventHandler(MouseEvent.MOUSE_PRESSED,
                                        selectHandler);
                            });
                }
            }
        });
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
