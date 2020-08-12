package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Shapes2D {
    private ShapeFactory factory;

    public Line() {
        super();
        factory = new ShapeFactory();
        this.getProperties().put("type", 1.0);
    }

    @Override
    public void draw(Object canvas) {
        double startX = this.getPosition().getX() + this.getProperties().get("x1");
        double startY = this.getPosition().getY() + this.getProperties().get("y1");
        double endX = this.getPosition().getX() + this.getProperties().get("x2");
        double endY = this.getPosition().getY() + this.getProperties().get("y2");

        Graphics2D graph = (Graphics2D) canvas;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
        graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
        graph.setColor(this.getColor());
        graph.draw(new Line2D.Double(startX, startY, endX, endY));
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        Shape newShape = factory.createShape("Line");
        newShape = this.cloning("Line", this);
        return newShape;
    }
}
