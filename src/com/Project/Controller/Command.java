package com.Project.Controller;

import com.Project.shapes.Line;
import com.Project.shapes.Shape;

import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Command {
    protected Map<String, Double> prop = new HashMap<>();
    Line str = new Line();
    Canvas Cs = Canvas.getInstance();

    public void move(Shape SelectedShape, Point Re) {
        double x = 0;
        double y = 0;
        switch (SelectedShape.getName()) {
            case "Rectangle": {
                prop = SelectedShape.getProperties();
                x = Re.getX() - (prop.get("Width") / 2);
                y = Re.getY() - (prop.get("Length") / 2);
                prop.put("x", x);
                prop.put("y", y);
                SelectedShape.setProperties(prop);

            }
            break;
            case "Circle":
                prop = SelectedShape.getProperties();
                x = Re.getX() - (prop.get("Radius") / 2);
                y = Re.getY() - (prop.get("Radius") / 2);
                double midX = x + prop.get("Radius") / 2;
                double midY = y + prop.get("Radius") / 2;
                prop.put("midX", midX);
                prop.put("midY", midY);
                SelectedShape.setProperties(prop);
                break;
            case "Square":
                prop = SelectedShape.getProperties();
                x = Re.getX() - (prop.get("Length") / 2);
                y = Re.getY() - (prop.get("Length") / 2);
                prop.put("x", x);
                prop.put("y", y);
                SelectedShape.setProperties(prop);
                break;
            case "Triangle": {
                prop = SelectedShape.getProperties();
                prop.put("base", abs(prop.get("X3") - prop.get("X1")));
                prop.put("Height", abs(prop.get("Y1") - prop.get("Y2")));
                x = Re.getX() - (prop.get("base") / 2);
                y = Re.getY() - (prop.get("Height") / 2);
                prop.put("X1", x);
                prop.put("X2", (x + (prop.get("base")) / 2));
                prop.put("X3", (x + prop.get("base")));
                prop.put("Y1", (y + prop.get("Height")));
                prop.put("Y2", y);
                prop.put("Y3", (y + prop.get("Height")));
                SelectedShape.setProperties(prop);
            }
            break;
            case "Ellipse": {
                prop = SelectedShape.getProperties();
                prop.put("midX", Re.getX());
                prop.put("midY", Re.getY());
                prop.put("x", Re.getX() - (prop.get("Width") / 2));
                prop.put("y", Re.getY() - (prop.get("Length") / 2));
                Point xyz = new Point(prop.get("x").intValue(), prop.get("y").intValue());
                SelectedShape.setPosition(xyz);
                SelectedShape.setProperties(prop);
            }
            break;
            case "StrLine": {
                prop = SelectedShape.getProperties();
                double l = prop.get("y1") - prop.get("y");
                double w = prop.get("x1") - prop.get("x");
                prop.put("x", Re.getX());
                prop.put("y", Re.getY());
                prop.put("x1", w + prop.get("x"));
                prop.put("y1", l + prop.get("y"));
                SelectedShape.setProperties(prop);
            }
            break;
        }
    }

    public void Resize(Shape SelectedShape, Point point) {
        double x = 0;
        double y = 0;
        switch (SelectedShape.getName()) {
            case "Rectangle":
                prop = SelectedShape.getProperties();
                prop.put("Width", point.getX() - prop.get("x"));
                prop.put("Length", point.getY() - prop.get("y"));
                SelectedShape.setProperties(prop);
                break;

            case "Circle":
                prop = SelectedShape.getProperties();
                double l = sqrt(abs((point.x - prop.get("midX")) * (point.x - prop.get("midX")) + (point.y - prop.get("midY")) * (point.y - prop.get("midY"))));
                prop.put("Radius", 2 * l);
                SelectedShape.setProperties(prop);
                break;

            case "Square":
                prop = SelectedShape.getProperties();
                double d = sqrt(abs((prop.get("x") - point.x) * (prop.get("x") - point.x) + (prop.get("y") - point.y) * (prop.get("y") - point.y)));
                prop.put("Length", d);
                SelectedShape.setProperties(prop);
                break;

            case "Triangle":
                prop = SelectedShape.getProperties();
                prop.put("Y1", point.getY());
                prop.put("X2", (prop.get("X1") + point.getX()) / 2);
                prop.put("X3", point.getX());
                prop.put("Y3", point.getY());
                prop.put("base", abs(prop.get("X3") - prop.get("X1")));
                prop.put("Height", abs(prop.get("Y1") - prop.get("Y2")));
                SelectedShape.setProperties(prop);
            break;

            case "Ellipse":
                prop = SelectedShape.getProperties();
                prop.put("Width", point.getX() - prop.get("x"));
                prop.put("Length", point.getY() - prop.get("y"));
                prop.put("midX", (double) (SelectedShape.getPosition().getX() + (prop.get("Width") / 2)));
                prop.put("midY", (double) (SelectedShape.getPosition().getY() + (prop.get("Length") / 2)));
                SelectedShape.setProperties(prop);
            break;

            case "Line":
                prop = SelectedShape.getProperties();
                prop.put("x1", point.getX());
                prop.put("y1", point.getY());
                SelectedShape.setProperties(prop);
                break;
        }
    }
}
