package com.Project.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Triangle implements Shape {
    protected Point point;
    protected Map<String, Double> prop;
    protected Color color;
    protected Color fillColor;

    public Triangle() {
        prop = new HashMap<>();
        prop.put("X1", 0.0);
        prop.put("Y1", 0.0);
        prop.put("X2", 0.0);
        prop.put("Y2", 0.0);
        prop.put("X3", 0.0);
        prop.put("Y3", 0.0);
        prop.put("base", 0.0);
        prop.put("Height", 0.0);
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
        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = prop.get("X1").intValue();
        x[1] = prop.get("X2").intValue();
        x[2] = prop.get("X3").intValue();
        y[0] = prop.get("Y1").intValue();
        y[1] = prop.get("Y2").intValue();
        y[2] = prop.get("Y3").intValue();
        if (y[2] < y[1]) {
            x[0] = prop.get("X1").intValue();
            x[1] = prop.get("X2").intValue();
            x[2] = prop.get("X3").intValue();
            y[0] = prop.get("Y2").intValue();
            y[1] = prop.get("Y3").intValue();
            y[2] = prop.get("Y2").intValue();
        }
        if (getFillColor() != null) {
            ((Graphics2D) canvas).setColor(getFillColor());
            ((Graphics2D) canvas).fillPolygon(x, y, 3);
        }
        ((Graphics2D) canvas).setStroke(new BasicStroke(2));
        ((Graphics2D) canvas).setColor(getColor());
        ((Graphics2D) canvas).drawPolygon(x, y, 3);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape cloned = new Triangle();
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
        return "Triangle";
    }
}
