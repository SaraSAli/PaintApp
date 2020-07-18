package com.Project.Controller;

import java.awt.*;
import java.util.Map;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import com.Project.shapes.Shape;

public class CheckPointInShape {
    private Map<String, Double> prop;

    public boolean CheckPoint(String shapeName, Shape x, Point p) {
        boolean result = false;
        CheckPointPolygon obj = new CheckPointPolygon();

        switch (shapeName) {

            case "Circle": {
                prop = x.getProperties();
                double D = prop.get("Radius");
                double Cx = prop.get("midX");
                double Cy = prop.get("midY");
                double L = sqrt(((Cx - p.x) * (Cx - p.x)) + ((Cy - p.y) * (Cy - p.y)));
                if ((D / 2) > L) result = true;
            }
            break;
            case "Ellipse": {
                prop = x.getProperties();

                double z = p.getX() - (prop.get("midX"));
                double y = p.getY() - (prop.get("midY"));
                if ((((z * z) / ((prop.get("Width") / 2) * (prop.get("Width") / 2))) + ((y * y) / ((prop.get("Length") / 2) * (prop.get("Length") / 2)))) < 1)
                    result = true;


            }
            break;
            case "Rectangle": {
                prop = x.getProperties();
                if (p.getX() >= prop.get("x") && p.getX() <= prop.get("x") + prop.get("Width")) {
                    if (p.getY() >= prop.get("y") && p.getY() <= prop.get("y") + prop.get("Length"))
                        result = true;
                }

            }
            break;
            case "Square":
                result = obj.isInside(x.GetPolygonPoints(), 4, p);
                break;
            case "Triangle":
                result = obj.isInside(x.GetPolygonPoints(), 3, p);
                break;
            case "Line": {
                prop = x.getProperties();
                double a = (prop.get("y1") - prop.get("y")) / (prop.get("x1") - prop.get("x"));
                double b = prop.get("y") - a * prop.get("x");
                if (abs(p.getY() - (a * p.getX() + b)) < 1) result = true;

            }
            break;


        }
        return result;
    }
}
