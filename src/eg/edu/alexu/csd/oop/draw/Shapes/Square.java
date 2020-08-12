package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Square extends Shapes2D {

    public Square() {
        super();
        this.getProperties().put("type", 5.0);
    }

    @Override
    public void draw(Object canvas) {
        SurroundingRectangle();
        Graphics2D graph = (Graphics2D) canvas;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
        graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
        graph.setColor(this.getColor());
        graph.draw(
                new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.width));

        if (this.getFillColor() != null) {
            graph.setColor(this.getFillColor());
            graph.fill(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width,
                    this.width));

        }
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        Shape newShape = this.cloning("Square", this);
        return newShape;
    }
}
