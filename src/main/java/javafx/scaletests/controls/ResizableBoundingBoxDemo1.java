package javafx.scaletests.controls;

import javafx.application.Application;
import javafx.scaletests.layers.EditableBoundingBox;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResizableBoundingBoxDemo1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        BorderPane root = new BorderPane(pane);
        root.setCenter(pane);

//        javafx.scaletests.layers.EditableBoundingBox box1 = new javafx.scaletests.layers.EditableBoundingBox(10, 10, 40, 60);
//        javafx.scaletests.layers.EditableBoundingBox box2 = new EditableBoundingBox(200, 100, 50, 73);
//        box1.setColor(Color.DEEPSKYBLUE);
//        pane.getChildren().addAll(box1.getShapes());

        ResizableBoundingBox box = new ResizableBoundingBox(200, 100, 50, 73);
        pane.getChildren().add(box);

        ResizableBoundingBox box2 = new ResizableBoundingBox(0, 0, 100, 100);
        pane.getChildren().add(box2);

        ResizableBoundingBox box3 = new ResizableBoundingBox(300, 20, 34, 66);
        pane.getChildren().add(box3);




        Scene scene = new Scene(root);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
