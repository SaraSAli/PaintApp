package com.Project.GUI;

import com.Project.Controller.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUI extends JFrame {
    Canvas C;
    private Color backcolordef = Color.WHITE;
    private Color Drawingcoldef = Color.BLACK;
    private Color Defdcol = Color.BLACK;
    private Color Fillcoldef;
    private Color Fillcolvar;

    public static GUI frame;

    public GUI() {
        C = Canvas.getInstance();
        //C.setBackground(Color.WHITE);
        setSize(2000,770);
        setTitle("Paint with Sara!!");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(null);



        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JMenuItem mnNew = new JMenuItem("New");
      /*  mnNew.addActionListener(arg0 -> {
            selectShape = "";
            paint.clear();
            repaint();
        });*/
        mnMenu.add(mnNew);

        JMenuItem mnOpen = new JMenuItem("Open");
        mnOpen.addActionListener(arg0 -> {

        });
        mnMenu.add(mnOpen);

        JMenuItem mnSave = new JMenuItem("Save");
        mnSave.addActionListener(arg0 -> {

        });
        mnMenu.add(mnSave);

        JMenuItem mnExit = new JMenuItem("Exit");
        mnExit.addActionListener(arg0 -> System.exit(0));
        mnMenu.add(mnExit);

        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JPanel panelAbout = new JPanel();
        panelAbout.setVisible(false);
        JMenu mnAbout = new JMenu("About");
        mnAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                new About();
            }
        });
        menuBar.add(mnAbout);

        JButton ellipse = new JButton(new ImageIcon("Capture2.PNG"));
        ellipse.setBackground(Color.WHITE);
        ellipse.setBounds(100, 30, 40, 40);
        JButton circle = new JButton(new ImageIcon("Capture1.png"));
        circle.setBackground(Color.WHITE);
        circle.setBounds(100, 75, 40, 40);
        JButton rectangle = new JButton(new ImageIcon("rectangle.png"));
        rectangle.setBackground(Color.WHITE);
        rectangle.setBounds(55, 30, 40, 40);
        JButton square = new JButton(new ImageIcon("Capture.png"));
        square.setBackground(Color.WHITE);
        square.setBounds(10, 30, 40, 40);
        JButton triangle = new JButton(new ImageIcon("Capture3.png"));
        triangle.setBackground(Color.WHITE);
        triangle.setBounds(10, 75, 40, 40);
        JButton line = new JButton(new ImageIcon("Capture4.png"));
        line.setBackground(Color.WHITE);
        line.setBounds(55, 75, 40, 40);

        JButton backGroundCol = new JButton(new ImageIcon("colorChooser.png"));
        backGroundCol.setBackground(Color.WHITE);
        backGroundCol.setBounds(150, 30, 80, 85);
        JButton drawingCol = new JButton(new ImageIcon("changeColor.jpg"));
        drawingCol.setBackground(Color.WHITE);
        drawingCol.setBounds(240, 30, 40, 40);
        JButton fillCol = new JButton(new ImageIcon("fill.png"));
        fillCol.setBackground(Color.WHITE);
        fillCol.setBounds(240, 75, 40, 40);
        JButton x = new JButton(new ImageIcon("move.png"));
        x.setBackground(Color.WHITE);
        x.setBounds(290, 30, 40, 40);
        JButton y = new JButton(new ImageIcon("refresh.png"));
        y.setBackground(Color.WHITE);
        y.setBounds(340, 75, 40, 40);
        JButton z = new JButton(new ImageIcon("resize.png"));
        z.setBackground(Color.WHITE);
        z.setBounds(290, 75, 40, 40);
        JButton delete = new JButton(new ImageIcon("delete.png"));
        delete.setBackground(Color.WHITE);
        delete.setBounds(340, 30, 40, 40);
        JButton copy = new JButton(new ImageIcon("copy.png"));
        copy.setBackground(Color.WHITE);
        copy.setBounds(1080, 30, 90, 80);
        JButton undo = new JButton(new ImageIcon("undo.png"));
        undo.setBackground(Color.WHITE);
        undo.setBounds(1180, 30, 70, 80);
        JButton redo = new JButton(new ImageIcon("redo.png"));
        redo.setBackground(Color.WHITE);
        redo.setBounds(1260, 30, 70, 80);


        add(ellipse);
        add(circle);
        add(rectangle);
        add(square);
        add(triangle);
        add(line);
        add(backGroundCol);
        add(drawingCol);
        add(fillCol);
        add(x);
        add(y);
        add(z);
        add(delete);
        add(copy);
        add(undo);
        add(redo);

        C.setBounds(0, 120, 1400, 700);
        add(C);


        ellipse.addActionListener(e -> C.flag = 1);
        circle.addActionListener(e -> C.flag = 2);

        rectangle.addActionListener(e -> C.flag = 3);

        square.addActionListener(e -> C.flag = 4);

        triangle.addActionListener(e -> C.flag = 5);

        line.addActionListener(e -> C.flag = 6);

        x.addActionListener(e -> C.flag = 7);
        y.addActionListener(e -> C.refresh(C));
        delete.addActionListener(e -> C.flag = 9);
        z.addActionListener(e -> C.flag = 10);
        copy.addActionListener(e -> C.flag = 11);


        backGroundCol.addActionListener(e -> {
            C.backcolflag = JColorChooser.showDialog(null, "Pick Your BackGround Color", backcolordef);
            if (C.backcolflag == null)
                C.backcolflag = backcolordef;
            C.setBackground(C.backcolflag);
        });
        drawingCol.addActionListener(e -> {
            Drawingcoldef = JColorChooser.showDialog(null, "Pick Your Drawing Color", Defdcol);
            if (Drawingcoldef == null)
                Drawingcoldef = Defdcol;
            C.setDrawingCol(Drawingcoldef);

        });
        fillCol.addActionListener(e -> {
            Fillcolvar = JColorChooser.showDialog(null, "Pick Fill Color", Fillcoldef);
            if (Fillcolvar == null)
                Fillcolvar = Fillcoldef;
            C.setFillCol(Fillcolvar);
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                C.undo();
                C.flag = 20;
                C.refresh(this);
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                C.redo();
                C.flag = 20;
                C.refresh(this);
            }
        });
    }

    public static void main(String[] args) {
        try {
            frame = new GUI();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
