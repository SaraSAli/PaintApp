package eg.edu.alexu.csd.oop.draw.Controller;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Shapes.Ellipse;
import eg.edu.alexu.csd.oop.draw.Shapes.Circle;
import eg.edu.alexu.csd.oop.draw.Shapes.Square;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.Shapes.Triangle;
import eg.edu.alexu.csd.oop.draw.Shapes.Line;


import java.awt.Graphics;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PaintEngine implements DrawingEngine {
    public ArrayList<Shape> shapes;
    private Stack<Entry<Shape, String>> undoStack;
    private Stack<Entry<Shape, String>> redoStack;
    private List<Class<? extends Shape>> shapesList;

    private XML XmlFile;
    private JSON JsonFile;

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
        XmlFile = new XML();
        JsonFile = new JSON();
        undoTimes = 0;
        redoTimes = 0;
        UpdatedShape = null;

        installPluginShape("RoundRectangle.jar");
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
                shape.draw((Graphics) canvas);
        });
    }

    @Override
    public void save(String path) throws IOException {
        if (path.matches(".*\\.[Xx][Mm][Ll]")) {
            XmlFile.setShapes(shapes);
            XmlFile.writeXml(path);
        } else if (path.matches(".*\\.[Jj][Ss][Oo][Nn]")) {
            JsonFile.setShapes(shapes);
            JsonFile.writeJson(path);
        }
    }

    @Override
    public void load(String path) {
        System.out.println(path);
        if (path.toLowerCase().endsWith(".xml")) {
            XmlFile.readXml(path);
            shapes = XmlFile.getShapes();
        } else if (path.toLowerCase().endsWith(".json")) {
            JsonFile.readJson(path);
            shapes = JsonFile.getShapes();
        }
    }

    @Override
    public Shape[] getShapes() {
        int numOfShapes = this.getNumShapes();
        Shape[] arrShapes = new Shape[numOfShapes];
        for (int i = 0; i < numOfShapes; i++) {
            arrShapes[i] = shapes.get(i);
        }
        return arrShapes;
    }

    private int getNumShapes() {
        return this.shapes.size();
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return shapesList;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public void installPluginShape(String jarPath) {

        File file = new File("C:\\Users\\Sara Said\\Documents\\PaintApp\\" + jarPath);

        try {
            InputStream inputStream = new FileInputStream(file);
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cload = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
            List<String> classNames = new ArrayList<String>();
            ZipInputStream zip = new ZipInputStream(inputStream);
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.');
                    String classN = className.substring(0, className.length() - ".class".length());
                    Class<? extends Shape> cls = (Class<? extends Shape>) cload.loadClass(classN);
                    (cls.getConstructors()[0]).newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
