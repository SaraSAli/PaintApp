package com.Project.shapes;

import java.awt.*;

public interface Shape {
    void setPosition(java.awt.Point position);

    java.awt.Point getPosition();

    /* update shape specific properties (e.g., radius) */
    void setProperties(java.util.Map<String, Double> properties);

    java.util.Map<String, Double> getProperties();

    void setColor(java.awt.Color color);

    java.awt.Color getColor();

    void setFillColor(java.awt.Color color);

    java.awt.Color getFillColor();

    /* redraw the shape on the canvas,
    for swing, you will cast canvas to java.awt.Graphics */
    void draw(Object canvas);

    /* create a deep clone of the shape */
    Object clone() throws CloneNotSupportedException;

    /* Some Extra Functions */
    Point[] GetPolygonPoints();

    String getName();
}
