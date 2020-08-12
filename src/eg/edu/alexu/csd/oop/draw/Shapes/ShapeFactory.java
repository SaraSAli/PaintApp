package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;

public class ShapeFactory {
    public Shape createShape(String shapeType) {

        if (shapeType.equals("Rectangle")) return new Rectangle();
        if (shapeType.equals("Circle")) return new Circle();
        if (shapeType.equals("Line")) return new Line();
        if (shapeType.equals("Square")) return new Square();
        if (shapeType.equals("Ellipse")) return new Ellipse();
        if (shapeType.equals("Triangle")) return new Triangle();
        else {
            System.out.println(shapeType);
            return null;
        }
    }
}
