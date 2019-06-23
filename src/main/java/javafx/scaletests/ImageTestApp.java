package javafx.scaletests;

import eu.mihosoft.scaledfx.ScalableContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author Brian Schlining
 * @since 2019-03-04T14:38:00
 */
public class ImageTestApp extends Application {

    class MyCircle {
        final double x;
        final double y;
        final Circle circle;

        public MyCircle(double x, double y) {
            this.x = x;
            this.y = y;
            circle = new Circle(10);
            circle.setFill(Color.color(.7, .1, .3, .5));
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        var image = new Image("http://dsg.mbari.org/images/dsg/external/Mollusca/Cephalopoda/Opisthoteuthis_spA_01.png");
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        var myCircle = new MyCircle(25, 25);
        imageView.boundsInParentProperty().addListener((obs, oldv, newv) -> {
            circle.setCenterX(newv.getMinX() + );
        });

        AnchorPane anchorPane = new AnchorPane(circle);
        anchorPane.prefWidthProperty().bind(imageView.fitWidthProperty());
        anchorPane.prefHeightProperty().bind(imageView.fitHeightProperty());
        anchorPane.translateXProperty().bind(imageView.xProperty());
        anchorPane.translateYProperty().bind(imageView.yProperty());



        StackPane stackPane = new StackPane(imageView, anchorPane);
        stackPane.setStyle("-fx-background-color: #000000");


        ScalableContentPane scp =
                new ScalableContentPane(stackPane);
        scp.setAspectScale(true);

        primaryStage.setScene(new Scene(scp));
        primaryStage.setTitle("Scalable Content Pane Demo");
        primaryStage.show();
    }
}