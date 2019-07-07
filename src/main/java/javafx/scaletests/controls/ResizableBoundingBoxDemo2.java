package javafx.scaletests.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResizableBoundingBoxDemo2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        BorderPane root = new BorderPane(pane);
        root.setCenter(pane);

        ResizableBoundingBox box1 = new ResizableBoundingBox(200, 100, 50, 73);
        pane.getChildren().add(box1);

        ResizableBoundingBox box2 = new ResizableBoundingBox(0, 0, 100, 100);
        pane.getChildren().add(box2);

        ResizableBoundingBox box3 = new ResizableBoundingBox(50, 0, 56, 100);
        pane.getChildren().add(box3);

        ResizableBoundingBoxGroup group = new ResizableBoundingBoxGroup();
        group.getBoundingBoxes().add(box1);
        group.getBoundingBoxes().add(box2);
        group.getBoundingBoxes().add(box3);


        Scene scene = new Scene(root);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
