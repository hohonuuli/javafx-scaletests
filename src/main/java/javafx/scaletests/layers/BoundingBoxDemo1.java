package javafx.scaletests.layers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BoundingBoxDemo1 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        BorderPane root = new BorderPane(pane);

        root.setCenter(pane);

        BoundingBox box1 = new BoundingBox(10, 10, 40, 60);
        pane.getChildren().addAll(box1.getShapes());

        BoundingBox box2 = new BoundingBox(200, 100, 50, 73);
        pane.getChildren().addAll(box2.getShapes());

        Scene scene = new Scene(root);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
