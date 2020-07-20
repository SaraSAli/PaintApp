package com.Project.shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class Circle extends Shapes2D {

    private double radius;

    public Circle() {
        super();
        this.getProperties().put("type", 4.0);
    }

    @Override
    public void draw(Object canvas) {
        SurroundingRectangle();
        double diameter = this.getProperties().get("width");
        Graphics2D graph = (Graphics2D) canvas;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
        graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));

        graph.setColor(this.getColor());
        graph.draw(new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(), diameter, diameter));
        if (this.getFillColor() != null) {
            graph.setColor(this.getFillColor());
            graph.fill(new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(), diameter, diameter));
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape newShape=this.cloning("Circle" ,this);
        return newShape;
    }

}
