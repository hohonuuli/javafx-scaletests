package javafx.scaletests.layers;

import javafx.scene.image.ImageView;

public class BoundingBoxLayer {

    private ImageView imageView;

    public static class BoundingBox {
        private double x;
        private double y;
        private double width;
        private double height;


        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }
}
