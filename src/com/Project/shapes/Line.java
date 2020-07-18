package com.Project.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Line implements Shape {
    protected Point point;
    protected Map<String, Double> prop;
    protected Color color;
    protected Color fillColor;

    public Line() {
        prop = new HashMap<>();
        prop.put("x",0.0);
        prop.put("y",0.0);
        prop.put("x1", 0.0);
        prop.put("y2", 0.0);
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
        ((Graphics2D) canvas).setStroke(new BasicStroke(2));
        ((Graphics2D) canvas).setColor(getColor());
        ((Graphics2D) canvas).drawLine((int) prop.get("x").intValue(),
                (int) prop.get("y").intValue(),
                (int) prop.get("x1").intValue(),
                (int) prop.get("y1").intValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape cloned=new Line();
        cloned.setColor(color);
        cloned.setFillColor(fillColor);
        cloned.setPosition(point);
        Map newProp = new HashMap<>();
        for (Map.Entry s: prop.entrySet())
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
        return "Line";
    }
}
