package com.Project.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Circle implements Shape {
    protected Point point;
    protected Map<String, Double> prop;
    protected Color color;
    protected Color fillColor;

    public Circle() {
        prop = new HashMap<>();
        prop.put("Radius", 0.0);
        prop.put("midX",0.0);
        prop.put("midY",0.0);
    }

    @Override
    public void setPosition(Point position) {
        point = position;
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        prop = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return prop;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void draw(Object canvas) {
        if (getFillColor() != null) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillOval((int) (prop.get("midX").intValue() - (prop.get("Radius").intValue() / 2)),
                    (int) (prop.get("midY").intValue() - (prop.get("Radius").intValue() / 2)),
                    (int) prop.get("Radius").intValue(),
                    (int) prop.get("Radius").intValue());
        }
        ((Graphics2D) canvas).setStroke(new BasicStroke(2));
        ((Graphics2D) canvas).setColor(getColor());
        ((Graphics2D) canvas).drawOval((int) (prop.get("midX").intValue() - (prop.get("Radius").intValue() / 2)),
                (int) (prop.get("midY").intValue() - (prop.get("Radius").intValue() / 2)),
                (int) prop.get("Radius").intValue(),
                (int) prop.get("Radius").intValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape cloned = new Circle();
        cloned.setColor(color);
        cloned.setFillColor(fillColor);
        cloned.setPosition(point);
        Map newProp = new HashMap<>();
        for (Map.Entry s : prop.entrySet())
            newProp.put(s.getKey(), s.getValue());
        cloned.setProperties(newProp);
        return cloned;
    }

    @Override
    public Point[] GetPolygonPoints() {
        return new Point[0];
    }

    @Override
    public String getName() {
        return "Circle";
    }
}
