package eg.edu.alexu.csd.oop.draw.Controller;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.Reader;

import eg.edu.alexu.csd.oop.draw.Shapes.*;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
    private ArrayList<Shape> shapes;
    private Shape shape;

    public JSON() {
        shapes = new ArrayList<>();
        shape = null;
    }

    public void writeJson(String path) throws IOException {

        File fileJson = new File(path);

        JSONObject jo = new JSONObject();

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileJson)));
        JSONArray jsonArray = new JSONArray();
        for (Shape s : shapes) {
            JSONObject obj = new JSONObject();

            if (s.getProperties() != null) {
                obj.put("type", s.getProperties().get("type").intValue());
                obj.put("width", s.getProperties().get("width").toString());
                obj.put("Height", s.getProperties().get("Height").toString());
                obj.put("X", s.getProperties().get("x1").toString());
                obj.put("x", s.getProperties().get("x2").toString());
                obj.put("Y", s.getProperties().get("y1").toString());
                obj.put("y", s.getProperties().get("y2").toString());
                obj.put("thickness", s.getProperties().get("thickness").toString());
                obj.put("transparent", s.getProperties().get("transparent").toString());
            }

            if (s.getPosition() != null) {
                obj.put("positionX", s.getPosition().getX());
                obj.put("positionY", s.getPosition().getY());
            }
            if (s.getColor() != null) {
                obj.put("strokeColor", s.getColor().getRGB());
            }
            if (s.getFillColor() != null) {
                obj.put("fillColor", s.getFillColor().getRGB());
            }
            jsonArray.add(obj);
        }

        jo.put("shapes", jsonArray);
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
        System.out.println("file successfully saved");

    }

    public void readJson(String path) {
        shapes.clear();
        shape = null;
        JSONParser jsonParser = new JSONParser();
        File fileJson = new File(path);

        try (Reader reader = new FileReader(fileJson)) {
            JSONObject jo = (JSONObject) jsonParser.parse(reader);
            JSONArray shapesList = (JSONArray) jo.get("shapes");

            for(Object o:shapesList){

                JSONObject json_shape = (JSONObject)o;

                System.out.println(json_shape.get("type"));
                int type = ((Number) json_shape.get("type")).intValue();

                if (type==1)
                    shape = new Line();
                if (type==2)
                    shape = new Rectangle();
                if (type==(3))
                    shape = new Ellipse();
                if (type==(4))
                    shape = new Circle();
                if (type==(5))
                    shape = new Square();
                if (type==(6))
                    shape = new Triangle();

                Double width = Double.parseDouble(json_shape.get("width").toString());
                shape.getProperties().put("width", width);

                Double height = Double.parseDouble(json_shape.get("Height").toString());
                shape.getProperties().put("Height", height);

                Double x1 = Double.parseDouble(json_shape.get("X").toString());
                shape.getProperties().put("x1", x1);

                Double x2 = Double.parseDouble(json_shape.get("x").toString());
                shape.getProperties().put("x2", x2);

                Double y1 = Double.parseDouble(json_shape.get("Y").toString());
                shape.getProperties().put("y1", y1);

                Double y2 = Double.parseDouble(json_shape.get("y").toString());
                shape.getProperties().put("y2", y2);

                Double thickness = Double.parseDouble(json_shape.get("thickness").toString());
                shape.getProperties().put("thickness", thickness);

                Double transparent = Double.parseDouble(json_shape.get("transparent").toString());
                shape.getProperties().put("transparent", transparent);

                shape.getPosition().x = (int) Double.parseDouble(json_shape.get("positionX").toString());

                shape.getPosition().y = (int) Double.parseDouble(json_shape.get("positionY").toString());

                Integer strokeColor = Integer.parseInt(json_shape.get("strokeColor").toString());
                shape.setColor(new Color(strokeColor));

                if (json_shape.containsKey("fillColor")) {
                    Integer fillColor = Integer.parseInt(json_shape.get("fillColor").toString());
                    shape.setFillColor(new Color(fillColor));
                }
                shapes.add(shape);
            }
            if (shape != null) {
                shapes.add(shape);
                shape = null;
            }

            System.out.println("File loaded successfully");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (ParseException | IOException e) {
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
