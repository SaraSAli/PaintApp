package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Shapes2D {

    private ShapeFactory factory;

    public Ellipse() {
        super();
        factory = new ShapeFactory();
        this.getProperties().put("type", 3.0);
    }

    @Override
    public void draw(Object canvas) {
  SurroundingRectangle();
        Graphics2D graph = (Graphics2D) canvas;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
        graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
        if (this.getFillColor() == null) {
            graph.setColor(this.getColor());
            graph.draw(new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width,
                    this.height));
        }
        if (this.getFillColor() != null) {
            graph.setColor(this.getFillColor());
            graph.fill(new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width,
                    this.height));
        }
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        Shape newShape = factory.createShape("Ellipse");
        newShape = this.cloning("Ellipse", this);
        return newShape;
    }
}
