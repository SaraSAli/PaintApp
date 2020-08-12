package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Shapes2D implements eg.edu.alexu.csd.oop.draw.Shape {

    protected Point position;
    private Color StrokeColor;
    private Color fillColor;
    private Map<String, Double> properties;
    private String name;
    private ShapeFactory factory;

    protected double width;
    protected double height;

    public Shapes2D() {
        position = new Point(0, 0);
        StrokeColor = null;
        fillColor = null;
        properties = new HashMap<>();
        factory=new ShapeFactory();
        properties.put("width", 0.0);
        properties.put("Height", 0.0);
        properties.put("x1", 0.0);
        properties.put("y1", 0.0);
        properties.put("x2", 0.0);
        properties.put("y2", 0.0);
        properties.put("thickness", 2.0);
        properties.put("transparent", 1.0);
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }


    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = this.properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return this.properties;
    }

    @Override
    public void setColor(Color color) {
        this.StrokeColor = color;
    }

    @Override
    public Color getColor() {
        return StrokeColor;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return this.fillColor;
    }

    @Override
    public void draw(Object canvas) {

    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        return null;
    }

    public eg.edu.alexu.csd.oop.draw.Shape cloning(String shapeType, eg.edu.alexu.csd.oop.draw.Shape oldShape) {

        Shape newShape = factory.createShape(shapeType);
        newShape.setColor(oldShape.getColor());
        newShape.setFillColor(oldShape.getFillColor());
        newShape.setPosition(oldShape.getPosition());
        for (Map.Entry<String, Double> p : oldShape.getProperties().entrySet()) {
            newShape.getProperties().put(p.getKey(), p.getValue());
        }
        return newShape;
    }

    @Override
    public Point[] GetPolygonPoints() {
        return new Point[0];
    }

    @Override
    public String getName() {
        return name;
    }

    protected void SurroundingRectangle() {
        this.width = this.getProperties().get("width");
        this.height = this.getProperties().get("Height");
    }
}
