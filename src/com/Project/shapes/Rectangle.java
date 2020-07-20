package com.Project.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Rectangle extends Shapes2D {

    private ShapeFactory factory;

    public Rectangle() {
        super();
        factory = new ShapeFactory();
        this.getProperties().put("type", 2.0);
    }


    @Override
    public void draw(Object canvas) {

        SurroundingRectangle();

        Graphics2D graph = (Graphics2D) canvas;

        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setStroke(new BasicStroke((float)(double)this.getProperties().get("thickness")));
        graph.setColor(this.getColor());
        graph.setComposite(AlphaComposite.SrcOver.derive((float)(double)this.getProperties().get("transparent")));
        graph.draw(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.height));

        if (this.getFillColor() != null) {
            graph.setColor(this.getFillColor());
            graph.fill(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.height));

        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Shape newShape = factory.createShape("Rectangle");
        newShape = this.cloning("Rectangle", this);
        return newShape;
    }


}
