package javafx.scaletests.model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class AnnoBoundingBox extends Rectangle {

    private double annoX;
    private double annoY;
    private double annoWidth;
    private double annoHeight;

    public AnnoBoundingBox(double annoX, double annoY, double annoWidth, double annoHeight) {
        this.annoX = annoX;
        this.annoY = annoY;
        this.annoWidth = annoWidth;
        this.annoHeight = annoHeight;
    }

    public AnnoBoundingBox(double width, double height, double annoX, double annoY, double annoWidth, double annoHeight) {
        super(width, height);
        this.annoX = annoX;
        this.annoY = annoY;
        this.annoWidth = annoWidth;
        this.annoHeight = annoHeight;
    }

    public AnnoBoundingBox(double width, double height, Paint fill, double annoX, double annoY, double annoWidth, double annoHeight) {
        super(width, height, fill);
        this.annoX = annoX;
        this.annoY = annoY;
        this.annoWidth = annoWidth;
        this.annoHeight = annoHeight;
    }

    public AnnoBoundingBox(double x, double y, double width, double height, double annoX, double annoY, double annoWidth, double annoHeight) {
        super(x, y, width, height);
        this.annoX = annoX;
        this.annoY = annoY;
        this.annoWidth = annoWidth;
        this.annoHeight = annoHeight;
    }
}
