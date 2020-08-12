package eg.edu.alexu.csd.oop.draw.Controller;

import com.sun.jdi.DoubleValue;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import eg.edu.alexu.csd.oop.draw.Shapes.*;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import java.io.*;
import java.util.ArrayList;

public class XML {
    private ArrayList<Shape> shapes;
    private Shape shape;

    public XML() {
        shapes = new ArrayList<>();
    }


    public void writeXml(String path) {
        File newXml = new File(path);
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element root = document.createElement("shapes");
            document.appendChild(root);

            int i = 1;
            for (Shape s : shapes) {
                Element shape = document.createElement("shape");
                root.appendChild(shape);

                Element properties = document.createElement("properties");
                shape.appendChild(properties);

                if (s.getProperties() != null) {

                    Element type = document.createElement("type");
                    type.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("type"))));
                    properties.appendChild(type);

                    Element width = document.createElement("width");
                    width.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("width"))));
                    properties.appendChild(width);

                    Element Height = document.createElement("Height");
                    Height.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("Height"))));
                    properties.appendChild(Height);

                    Element x1 = document.createElement("x1");
                    x1.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("x1"))));
                    properties.appendChild(x1);

                    Element y1 = document.createElement("y1");
                    y1.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("y1"))));
                    properties.appendChild(y1);

                    Element x2 = document.createElement("x2");
                    x2.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("x2"))));
                    properties.appendChild(x2);

                    Element y2 = document.createElement("y2");
                    y2.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("y2"))));
                    properties.appendChild(y2);

                    Element thickness = document.createElement("thickness");
                    thickness.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("thickness"))));
                    properties.appendChild(thickness);

                    Element transparent = document.createElement("transparent");
                    transparent.appendChild(document.createTextNode(String.valueOf(s.getProperties().get("transparent"))));
                    properties.appendChild(transparent);
                } else if (s.getProperties() == null) {
                    Element type = document.createElement("type");
                    type.appendChild(document.createTextNode("20.0"));
                    properties.appendChild(type);
                }
                if (s.getPosition() != null) {
                    Element positionX = document.createElement("positionX");
                    positionX.appendChild(document.createTextNode(String.valueOf(s.getPosition().getX())));
                    properties.appendChild(positionX);

                    Element positionY = document.createElement("positionY");
                    positionY.appendChild(document.createTextNode(String.valueOf(s.getPosition().getY())));
                    properties.appendChild(positionY);
                }
                if (s.getFillColor() != null) {
                    Element fillColor = document.createElement("fillColor");
                    fillColor.appendChild(document.createTextNode(String.valueOf(s.getFillColor().getRGB())));
                    properties.appendChild(fillColor);
                }
                if (s.getFillColor() == null) {
                    Element fillColor = document.createElement("fillColor");
                    fillColor.appendChild(document.createTextNode(""));
                    properties.appendChild(fillColor);
                }
                if (s.getColor() != null) {
                    Element strokeColor = document.createElement("strokeColor");
                    strokeColor.appendChild(document.createTextNode(String.valueOf(s.getColor().getRGB())));
                    properties.appendChild(strokeColor);
                }
                i++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(newXml);

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void readXml(String path) {
        shapes.clear();
        File xmlFile = new File(path);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("shape");

            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String value = element.getElementsByTagName("type").item(0).getTextContent();
                    double dValue = Double.parseDouble(String.valueOf(value));
                    if (dValue == 1.0) {
                        shape = new Line();
                    } else if (dValue == 2.0) {
                        shape = new Rectangle();
                    } else if (dValue == 3.0) {
                        shape = new Ellipse();
                    } else if (dValue == 4.0) {
                        shape = new Circle();
                    } else if (dValue == 5.0) {
                        shape = new Square();
                    } else if (dValue == 6.0) {
                        shape = new Triangle();
                    }
                    shape.getProperties().put("type", dValue);

                    String width = element.getElementsByTagName("width").item(0).getTextContent();
                    double dWidth = Double.parseDouble(String.valueOf(width));
                    shape.getProperties().put("width", dWidth);

                    String Height = element.getElementsByTagName("Height").item(0).getTextContent();
                    double dHeight = Double.parseDouble(String.valueOf(Height));
                    shape.getProperties().put("Height", dHeight);

                    String x1 = element.getElementsByTagName("x1").item(0).getTextContent();
                    if (x1 != null) {
                        double dx1 = Double.parseDouble(String.valueOf(x1));
                        shape.getProperties().put("x1", dx1);
                    }

                    String y1 = element.getElementsByTagName("y1").item(0).getTextContent();
                    if (y1 != null) {
                        double dy1 = Double.parseDouble(String.valueOf(y1));
                        shape.getProperties().put("y1", dy1);
                    }

                    String x2 = element.getElementsByTagName("x2").item(0).getTextContent();
                    if (x2 != null) {
                        double dx2 = Double.parseDouble(String.valueOf(x2));
                        shape.getProperties().put("x2", dx2);
                    }

                    String y2 = element.getElementsByTagName("y2").item(0).getTextContent();
                    if (y1 != null) {
                        double dy2 = Double.parseDouble(String.valueOf(y2));
                        shape.getProperties().put("y2", dy2);
                    }

                    String thickness = element.getElementsByTagName("thickness").item(0).getTextContent();
                    if (thickness != null) {
                        double dThickness = Double.parseDouble(String.valueOf(thickness));
                        shape.getProperties().put("thickness", dThickness);
                    }

                    String transparent = element.getElementsByTagName("transparent").item(0).getTextContent();
                    if (transparent != null) {
                        double dTransparent = Double.parseDouble(String.valueOf(transparent));
                        shape.getProperties().put("transparent", dTransparent);
                    }

                    String positionX = element.getElementsByTagName("positionX").item(0).getTextContent();
                    String positionY = element.getElementsByTagName("positionY").item(0).getTextContent();
                    if (positionX != null && positionY != null) {
                        double dPositionX = Double.parseDouble(String.valueOf(positionX));
                        double dPositionY = Double.parseDouble(String.valueOf(positionY));
                        shape.setPosition(new Point((int) dPositionX, (int) dPositionY));
                    }

                    if (element.getElementsByTagName("fillColor").item(0).getTextContent() != "") {
                        String fillColor = element.getElementsByTagName("fillColor").item(0).getTextContent();
                        if (fillColor != "") {
                            double dFillColor = Double.parseDouble(String.valueOf(fillColor));
                            shape.setFillColor(new Color((int) dFillColor));
                        }
                    }

                    String strokeColor = element.getElementsByTagName("strokeColor").item(0).getTextContent();
                    double dStrokeColor = Double.parseDouble(String.valueOf(strokeColor));
                    shape.setColor(new Color((int) dStrokeColor));
                }
                shapes.add(shape);
            }
            System.out.println("File loaded successfully");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
}
