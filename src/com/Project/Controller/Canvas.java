package com.Project.Controller;

import com.Project.shapes.Shape;
import com.Project.shapes.Ellipse;
import com.Project.shapes.Circle;
import com.Project.shapes.Square;
import com.Project.shapes.Rectangle;
import com.Project.shapes.Triangle;
import com.Project.shapes.Line;

import java.awt.*;
import java.awt.event.*;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, DrawingEngine {
    int counter = 0;
    JPanel canvas;
    ArrayList<Shape> shapes = new ArrayList<>();
    //Save s=new Save();
    Shape SelectedShape;
    private Map<String, Double> prop;
    private Ellipse E;
    private Circle Cir;
    public Rectangle R;
    private Square Sq;
    private Triangle T;
    private Line L;
    private Point start = new Point();
    private Point end = new Point();
    Stack<Shape> UN = new Stack();
    Stack<Shape> RE = new Stack();
    public static Canvas UniqueCanvas = null;
    UR ur = new UR();

    Color color;
    public Color color1 = null;
    public int flag;
    public Color backcolflag;

    private Canvas() {
        this.setBackground(Color.WHITE);
        setVisible(true);
        this.setVisible(true);
        flag = 0;
        color = Color.BLACK;
        shapes = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public static Canvas getinstance() {
        if (UniqueCanvas == null) {
            UniqueCanvas = new Canvas();
        }
        return UniqueCanvas;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        refresh(this);
    }

    public void setDrawingCol(Color c) {
        color = c;
    }

    public void setFillCol(Color c) {
        color1 = c;
    }

    @Override
    public Shape[] getShapes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undo() {
        ur.undo(this);
        repaint();
    }

    @Override
    public void redo() {
        ur.redo(this);
        repaint();
    }

    @Override
    public void refresh(Object canvas) {
        for (int i = shapes.size() - 1; i >= 0; i--) {

            shapes.get(i).draw(this.getGraphics());
        }
    }


    @Override
    public void updateShape(Shape oldShape, Shape newShape) {

    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        start.x = e.getX();
        start.y = e.getY();
        CheckPointInShape obj = new CheckPointInShape();
        if (flag == 1) {  //Ellipse
            E = new Ellipse();
            E.setColor(color);
            E.setFillColor(color1);

        } else if (flag == 2) {  //Circle
            Cir = new Circle();
            Cir.setColor(color);
            Cir.setFillColor(color1);

        } else if (flag == 3) {     //Rectangle
            R = new Rectangle();
            R.setColor(color);
            R.setFillColor(color1);

        } else if (flag == 4) {     //Square
            Sq = new Square();
            Sq.setColor(color);
            Sq.setFillColor(color1);

        } else if (flag == 5) {     //Triangle
            T = new Triangle();
            T.setColor(color);
            T.setFillColor(color1);

        } else if (flag == 6) {     //Line
            L = new Line();
            L.setColor(color);
        } else if (flag == 7) {     //Move
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (obj.CheckPoint(shapes.get(i).getName(), shapes.get(i), start)) {
                    try {
                        SelectedShape = (Shape) shapes.get(i).clone();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    removeShape(shapes.get(i));
                    break;
                }
            }


        } else if (flag == 9) {     //Delete
            for (int i = shapes.size() - 1; i >= 0; i--) {

                if (obj.CheckPoint(shapes.get(i).getName(), shapes.get(i), start)) {
                    removeShape(shapes.get(i));
                    break;
                }
            }
        } else if (flag == 10) {        //Resize
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (obj.CheckPoint(shapes.get(i).getName(), shapes.get(i), start)) {

                    SelectedShape = shapes.get(i);
                    removeShape(shapes.get(i));
                    break;
                }
            }
        } else if (flag == 11) {        //Copy
            for (int i = shapes.size() - 1; i >= 0; i--) {

                if (obj.CheckPoint(shapes.get(i).getName(), shapes.get(i), start)) {
                    try {
                        SelectedShape = (Shape) shapes.get(i).clone();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);

                    }
                    break;
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end.x = e.getX();
        end.y = e.getY();
        Point temp = new Point(0, 0);
        Map<String, Double> prop;
        prop = new HashMap<>();
        Command obj = new Command();
        if (flag == 1) {    //Ellipse
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;
            E.setPosition(temp);


            prop.put("Width", (double) abs(start.x - end.x));
            prop.put("Length", (double) abs(end.y - start.y));
            prop.put("midX", (double) (temp.x + (prop.get("Width") / 2)));
            prop.put("midY", (double) (temp.y + (prop.get("Length") / 2)));
            prop.put("x", temp.getX());
            prop.put("y", temp.getY());

            E.setProperties(prop);
            E.draw(this.getGraphics());


            shapes.add(E);
            UN.push(E);


        } else if (flag == 2) {     //Circle
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;
            Cir.setPosition(temp);

            double midX = (start.x + end.x) / 2;
            double midY = (start.y + end.y) / 2;
            double d = sqrt(abs((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y)));

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Radius", d);
            prop.put("midX", midX);
            prop.put("midY", midY);


            Cir.setProperties(prop);
            Cir.draw(this.getGraphics());

            UN.push(Cir);
            shapes.add(Cir);

        } else if (flag == 3) {     //Rectangle
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Width", (double) abs(start.x - end.x));
            prop.put("Length", (double) abs(end.y - start.y));

            R.setProperties(prop);
            R.draw(this.getGraphics());

            UN.push(R);
            shapes.add(R);

        } else if (flag == 4) {     //Square
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;

            double d = sqrt(abs((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y)));

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Length", d);

            Sq.setProperties(prop);
            Sq.draw(this.getGraphics());

            UN.push(Sq);
            shapes.add(Sq);

        } else if (flag == 5) {     //Triangle
            prop.put("X1", start.getX());
            prop.put("Y1", end.getY());
            prop.put("X2", ((start.getX() + end.getX()) / 2));
            prop.put("Y2", start.getY());
            prop.put("X3", end.getX());
            prop.put("Y3", end.getY());

            T.setPosition(start);
            T.setProperties(prop);
            T.draw(this.getGraphics());

            UN.push(T);
            shapes.add(T);

        } else if (flag == 6) {     //Line

            prop.put("x", (double) (start.x));
            prop.put("y", (double) (start.y));
            prop.put("x1", (double) (end.x));
            prop.put("y1", (double) (end.y));
            L.setPosition(start);
            L.setProperties(prop);
            L.draw(this.getGraphics());

            UN.push(L);
            shapes.add(L);

        } else if (flag == 7) {     //Move
            if (SelectedShape != null) {
                obj.move(SelectedShape, end);
                SelectedShape.draw(this.getGraphics());
                addShape(SelectedShape);
                SelectedShape = null;
            }
        } else if (flag == 10) {       //Resize
            if (SelectedShape != null) {
                obj.Resize(SelectedShape, end);
                SelectedShape.draw(this.getGraphics());
                addShape(SelectedShape);
                SelectedShape = null;
            }
        } else if (flag == 11) {    //Copy

            if (SelectedShape != null) {
                obj.move(SelectedShape, end);
                SelectedShape.draw(this.getGraphics());
                addShape(SelectedShape);
                SelectedShape = null;
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Shape CurrentShape = null;
        if (SelectedShape != null) {
            try {
                CurrentShape = (Shape) SelectedShape.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        end.x = e.getX();
        end.y = e.getY();
        Point temp = new Point(0, 0);
        Map<String, Double> prop;
        prop = new HashMap<>();
        Command obj = new Command();
        if (flag == 1) {
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;
            E.setPosition(temp);


            prop.put("Width", (double) abs(start.x - end.x));
            prop.put("Length", (double) abs(end.y - start.y));
            prop.put("midX", (double) (temp.x + (prop.get("Width") / 2)));
            prop.put("midY", (double) (temp.y + (prop.get("Length") / 2)));
            prop.put("x", temp.getX());
            prop.put("y", temp.getY());

            E.setProperties(prop);
            E.draw(this.getGraphics());

            try {
                CurrentShape = (Shape) E.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else if (flag == 2) {
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;
            Cir.setPosition(temp);

            double midx = (start.x + end.x) / 2;
            double midy = (start.y + end.y) / 2;
            double d = sqrt(abs((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y)));

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Radius", d);
            prop.put("midX", midx);
            prop.put("midY", midy);


            Cir.setProperties(prop);
            Cir.draw(this.getGraphics());

            try {
                CurrentShape = (Shape) Cir.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (flag == 3) {
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Width", (double) abs(start.x - end.x));
            prop.put("Length", (double) abs(end.y - start.y));

            R.setProperties(prop);
            R.draw(this.getGraphics());

            try {
                CurrentShape = (Shape) R.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (flag == 4) {
            if (end.x < start.x) temp.x = end.x;
            else temp.x = start.x;
            if (end.y < start.y) temp.y = end.y;
            else temp.y = start.y;

            double d = sqrt(abs((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y)));

            prop.put("x", temp.getX());
            prop.put("y", temp.getY());
            prop.put("Length", d);

            Sq.setProperties(prop);
            Sq.draw(this.getGraphics());

            try {
                CurrentShape = (Shape) Sq.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (flag == 5) {
            prop.put("X1", start.getX());
            prop.put("Y1", end.getY());
            prop.put("X2", ((start.getX() + end.getX()) / 2));
            prop.put("Y2", start.getY());
            prop.put("X3", end.getX());
            prop.put("Y3", end.getY());

            T.setPosition(start);
            T.setProperties(prop);
            T.draw(this.getGraphics());
            try {
                CurrentShape = (Shape) T.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (flag == 6) {

            prop.put("x", (double) (start.x));
            prop.put("y", (double) (start.y));
            prop.put("x1", (double) (end.x));
            prop.put("y1", (double) (end.y));
            L.setPosition(start);
            L.setProperties(prop);
            L.draw(this.getGraphics());

            try {
                CurrentShape = (Shape) L.clone();
                shapes.add(CurrentShape);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (flag == 7) {
            if (SelectedShape != null) {
                obj.move(CurrentShape, end);
                CurrentShape.draw(this.getGraphics());
                addShape(CurrentShape);
            }
        } else if (flag == 10) {
            if (SelectedShape != null) {
                obj.Resize(CurrentShape, end);
                CurrentShape.draw(this.getGraphics());
                addShape(CurrentShape);
            }
        } else if (flag == 11) {

            if (SelectedShape != null) {
                obj.move(CurrentShape, end);
                CurrentShape.draw(this.getGraphics());
                addShape(CurrentShape);
            }
        }
        counter++;
        if (counter % 3 == 0) {
            repaint();

        }
        shapes.remove(CurrentShape);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }

    @Override
    public java.util.List<Class<? extends Shape>> getSupportedShapes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void installPluginShape(String jarPath) {

    }
}
