package com.Project.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Shapes2D implements Shape {

    protected Point position;
    private Color StrokeColor;
    private Color fillColor;
    private Map<String, Double> properities;
    private String name;
    private ShapeFactory factory;

    protected double width;
    protected double height;

    public Shapes2D() {
        position = new Point(0, 0);
        StrokeColor = null;
        fillColor = null;
        properities = new HashMap<>();
        factory=new ShapeFactory();
        properities.put("width", 0.0);
        properities.put("Height", 0.0);
        properities.put("x1", 0.0);
        properities.put("y1", 0.0);
        properities.put("x2", 0.0);
        properities.put("y2", 0.0);
        properities.put("thickness", 2.0);
        properities.put("transparent", 1.0);
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
        this.properities = properities;
    }

    @Override
    public Map<String, Double> getProperties() {
        return this.properities;
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
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    public Shape cloning(String shapeType,Shape oldShape) {

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
        return null;
    }

    protected void SurroundingRectangle() {
        this.width = this.getProperties().get("width");
        this.height = this.getProperties().get("Height");
    }
}
