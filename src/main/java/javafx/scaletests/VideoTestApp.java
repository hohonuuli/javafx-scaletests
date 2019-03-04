package javafx.scaletests;

import eu.mihosoft.scaledfx.ScalableContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author Brian Schlining
 * @since 2019-03-04T14:48:00
 */
public class VideoTestApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
//
//        var mediaPlayer = new MediaPlayer
//        var media = new Media



        Image image = new Image("http://dsg.mbari.org/images/dsg/external/Mollusca/Cephalopoda/Opisthoteuthis_spA_01.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        Circle circle = new Circle(20, 100, 150);

        StackPane stackPane = new StackPane(imageView, circle);
        stackPane.setStyle("-fx-background-color: #000000");


        ScalableContentPane scp =
                new ScalableContentPane(stackPane);
        scp.setAspectScale(true);

        borderPane.setCenter(scp);

        borderPane.setBottom(new Button("Press me"));

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("Scalable Content Pane Demo");
        primaryStage.show();
    }
}
