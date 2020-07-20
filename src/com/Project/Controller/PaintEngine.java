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

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PaintEngine implements DrawingEngine {
    public ArrayList<Shape> shapes;
    private Stack<Entry<Shape, String>> undoStack;
    private Stack<Entry<Shape, String>> redoStack;
    private List<Class<? extends Shape>> shapesList;

    private int undoTimes, redoTimes;
    private Shape UpdatedShape;

    public Stack<Entry<Shape, String>> getUndoStack() {
        return undoStack;
    }

    public void setUndoStack(Stack<Entry<Shape, String>> undoStack) {
        this.undoStack = undoStack;
    }

    public Stack<Entry<Shape, String>> getRedoStack() {
        return redoStack;
    }

    public void setRedoStack(Stack<Entry<Shape, String>> redoStack) {
        this.redoStack = redoStack;
    }

    public int getUndoTimes() {
        return undoTimes;
    }

    public void setUndoTimes(int undoTimes) {
        this.undoTimes = undoTimes;
    }

    public int getRedoTimes() {
        return redoTimes;
    }

    public void setRedoTimes(int redoTimes) {
        this.redoTimes = redoTimes;
    }

    private Entry<Shape, String> createEntry(Shape shape, String string) {
        Entry<Shape, String> pair = new SimpleEntry<>(shape, string);
        return pair;
    }


    public PaintEngine() {
        shapes = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        shapesList = new ArrayList<>();
        shapesList.add(Circle.class);
        shapesList.add(Ellipse.class);
        shapesList.add(Line.class);
        shapesList.add(Rectangle.class);
        shapesList.add(Square.class);
        shapesList.add(Triangle.class);

        undoTimes = 0;
        redoTimes = 0;
        UpdatedShape = null;
    }

    public Shape getUpdatedShape() {
        return UpdatedShape;
    }

    public void setUpdatedShape(Shape updatedShape) {
        UpdatedShape = updatedShape;
    }


    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).equals(oldShape)) {
                shapes.remove(oldShape);
                shapes.add(i, newShape);
                break;
            }
        }
        undoStack.add(createEntry(oldShape, "oldVersion"));
        undoStack.add(createEntry(newShape, "newVersion"));
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
        undoTimes = 0;
        redoTimes = 0;
        redoStack.clear();
        undoStack.add(createEntry(shape, "remove"));
    }

    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
        if (undoTimes == 20)
            undoTimes--;
        redoTimes = 0;
        redoStack.clear();
        undoStack.add(createEntry(shape, "add"));
    }

    @Override
    public void undo() {
        if (!undoStack.isEmpty() && undoTimes < 20) {
            undoTimes++;
            String action = undoStack.peek().getValue();
            Shape shape = undoStack.peek().getKey();
            undoStack.pop();
            switch (action) {
                case "add":
                    this.shapes.remove(shape);
                    redoStack.add(createEntry(shape, "remove"));
                    break;
                case "remove":
                    this.shapes.add(shape);
                    redoStack.add(createEntry(shape, "add"));
                    break;
                case "newVersion":
                    Shape oldShape = undoStack.peek().getKey();
                    undoStack.pop();
                    for (int i = 0; i < shapes.size(); i++) {
                        if (shapes.get(i).equals(shape)) {
                            shapes.remove(shape);
                            shapes.add(i, oldShape);
                            break;
                        }
                    }
                    redoStack.add(createEntry(shape, "newVersion"));
                    redoStack.add(createEntry(oldShape, "oldVersion"));
                    break;
            }
        }
    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty() && redoTimes < 20) {
            redoTimes++;
            undoTimes--;
            String action = redoStack.peek().getValue();
            Shape shape = redoStack.peek().getKey();
            redoStack.pop();
            switch (action) {
                case "add":
                    this.shapes.remove(shape);
                    undoStack.add(createEntry(shape, "remove"));
                    break;
                case "remove":
                    this.shapes.add(shape);
                    undoStack.add(createEntry(shape, "add"));
                    break;
                case "oldVersion":
                    Shape newShape = redoStack.peek().getKey();
                    redoStack.pop();
                    for (int i = 0; i < shapes.size(); i++) {
                        if (shapes.get(i).equals(shape)) {
                            shapes.remove(shape);
                            shapes.add(i, newShape);
                            break;
                        }
                    }
                    undoStack.add(createEntry(shape, "oldVersion"));
                    undoStack.add(createEntry(newShape, "newVersion"));

                    break;
            }
        }
    }

    @Override
    public void refresh(Object canvas) {
        shapes.forEach(shape -> {
            if (!shape.equals(UpdatedShape))
                shape.draw((Graphics)canvas);
        });
    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }

    @Override
    public Shape[] getShapes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
