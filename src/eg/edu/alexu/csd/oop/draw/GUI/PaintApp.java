package eg.edu.alexu.csd.oop.draw.GUI;

import eg.edu.alexu.csd.oop.draw.Controller.PaintEngine;
import eg.edu.alexu.csd.oop.draw.Controller.SaveLoad;
import eg.edu.alexu.csd.oop.draw.Shape;

import javax.swing.filechooser.FileFilter;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PaintApp extends JFrame {
    private JButton RecButton, LineButton, EllipseButton, CircleButton, TriangleButton, SquareButton;
    private Color fillColor, strokeColor;
    private PaintEngine PaintDemo;
    private Board DrawingBoard;
    private JButton RedoButton;
    private JRadioButton Fill;
    private JRadioButton NoFill;
    private JButton StrokeButton;
    private JButton fillButton;
    JFileChooser fc;
    private Shape copiedShape;
    private JButton PasteButton;
    private JButton CopyButton;
    private JButton removeButton;
    private JButton FillWithColorButton;
    private boolean fillShape;
    private JButton SaveButton;
    private boolean isFound;
    private SaveLoad saveLoad;

    public PaintApp() {
        PaintDemo = new PaintEngine();
        DrawingBoard = new Board(PaintDemo, this);
        DrawingBoard.setBackground(new Color(255, 255, 224));
        copiedShape = null;
        fillShape = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Paint with Sara!");
        this.setSize(1100, 700);
        JPanel contentPane = new JPanel();
        contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel MainPanel = new JPanel();
        MainPanel.setBackground(new Color(0, 0, 255));
        contentPane.add(MainPanel, BorderLayout.NORTH);

        JPanel operationPanel = new JPanel();
        operationPanel.setBackground(new Color(67, 79, 91));

        JPanel NewPanel = new JPanel();
        operationPanel.add(NewPanel);
        NewPanel.setLayout(new BorderLayout(0, 0));

        JButton NewButton = new JButton("");
        NewButton.addActionListener(arg0 -> {
            PaintDemo.shapes.clear();
            PaintDemo.getRedoStack().clear();
            PaintDemo.getUndoStack().clear();
            PaintDemo.setRedoTimes(0);
            PaintDemo.setUndoTimes(0);
            repaint();
        });
        NewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(NewButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(NewButton);
            }
        });
        NewButton.setBackground(new Color(67, 79, 91));
        NewButton.setContentAreaFilled(false);
        NewButton.setOpaque(true);
        NewButton.setIcon(new ImageIcon("icons8_File_30px.png"));
        NewPanel.add(NewButton, BorderLayout.CENTER);

        Label newLabel = new Label("New ");
        newLabel.setForeground(new Color(255, 255, 224));
        newLabel.setBackground(new Color(67, 79, 91));
        newLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
        newLabel.setAlignment(Label.CENTER);
        NewPanel.add(newLabel, BorderLayout.SOUTH);

        JPanel savePanel = new JPanel();
        operationPanel.add(savePanel);
        savePanel.setLayout(new BorderLayout(0, 0));

        SaveButton = new JButton("");
        SaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(SaveButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(SaveButton);
            }
        });
        SaveButton.addActionListener(arg0 -> {
            fc = new JFileChooser();
            fc.setDialogTitle("save file ");
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int res = fc.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fc.getSelectedFile().getCanonicalPath();
                    if (!(filePath.matches(".*\\.[Jj][Ss][Oo][Nn]") || filePath.matches(".*\\.[Xx][Mm][Ll]"))) {
                        JOptionPane.showMessageDialog(this, "please enter xml or json file only", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else
                        PaintDemo.save(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
              /*  try {
                    String filePath = fc.getSelectedFile().getCanonicalPath();
                    if (filePath.matches(".*\\.[Jj][Ss][Oo][Nn]")){
                        StringBuilder name = new StringBuilder();
                        name.append("/");
                        name.append(JOptionPane.showInputDialog("Name of the .json file without extension"));
                        name.append(".json");
                        //saveLoad.saveJson(new PaintEngine() ,fc.getSelectedFile()+name.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });
        SaveButton.setIcon(new ImageIcon("save.png"));
        SaveButton.setBackground(new Color(67, 79, 91));
        SaveButton.setContentAreaFilled(false);
        SaveButton.setOpaque(true);
        savePanel.add(SaveButton, BorderLayout.CENTER);

        Label SaveLabel = new Label("Save As..");
        SaveLabel.setForeground(new Color(255, 255, 224));
        SaveLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
        SaveLabel.setAlignment(Label.CENTER);
        SaveLabel.setBackground(new Color(67, 79, 91));
        savePanel.add(SaveLabel, BorderLayout.SOUTH);

        JPanel snapshotPanel = new JPanel();
        operationPanel.add(snapshotPanel);
        snapshotPanel.setLayout(new BorderLayout(0, 0));

        JButton saveImageButton = new JButton("");
        saveImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(saveImageButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(saveImageButton);
            }
        });
        saveImageButton.addActionListener(arg0 -> {
            BufferedImage img = new BufferedImage(DrawingBoard.getWidth(), DrawingBoard.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            DrawingBoard.paint(g);
            JFileChooser screenPath = new JFileChooser();
            screenPath.setDialogTitle("Save File");
            FileFilter filter = new FileFilter() {

                @Override
                public String getDescription() {
                    return "jpg and png images (*.jpg)(*.png)";
                }

                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        String fileName = f.getName().toLowerCase();
                        return fileName.matches(".*\\.[Xx][Mm][Ll]") || fileName.matches(".*\\.[Jj][Ss][Oo][Nn]");
                    }
                }
            };
            screenPath.setFileFilter(filter);
            screenPath.addChoosableFileFilter(filter);
            screenPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int res = screenPath.showSaveDialog(PaintApp.this);
            if (res == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = screenPath.getSelectedFile().getCanonicalPath().toString();
                    if (!(filePath.matches(".*\\.[Jj][Pp][Gg]") || filePath.matches(".*\\.[Pp][Nn][Gg]"))) {
                        JOptionPane.showMessageDialog(PaintApp.this, "please enter jpg or png image only", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        ImageIO.write(img, "jpg", screenPath.getSelectedFile());
                        DrawingBoard.setBorder(null);
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });
        saveImageButton.setBackground(new Color(67, 79, 91));
        saveImageButton.setContentAreaFilled(false);
        saveImageButton.setOpaque(true);
        saveImageButton.setIcon(new ImageIcon("save.png"));
        snapshotPanel.add(saveImageButton, BorderLayout.CENTER);

        Label label_12 = new Label("Save");
        label_12.setBackground(new Color(67, 79, 91));
        label_12.setForeground(new Color(255, 255, 224));
        label_12.setFont(new Font("Arial Black", Font.BOLD, 12));
        label_12.setAlignment(Label.CENTER);
        snapshotPanel.add(label_12, BorderLayout.SOUTH);
        Panel loadPanel = new Panel();
        operationPanel.add(loadPanel);
        loadPanel.setLayout(new BorderLayout(0, 0));

        JButton loadButton = new JButton("");
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(loadButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(loadButton);
            }
        });
        loadButton.addActionListener(arg0 -> {
            fc = new JFileChooser();
            fc.setDialogTitle("Open xml or json file");
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml") || f.getName().toLowerCase().endsWith(".json");
                }

                @Override
                public String getDescription() {
                    return "*.json, *.xml";
                }
            });
            int res = fc.showOpenDialog(PaintApp.this);
            if (res == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fc.getSelectedFile().getAbsolutePath();
                    PaintDemo.load(filePath);
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadButton.setIcon(new ImageIcon("load.png"));
        loadButton.setBackground(new Color(67, 79, 91));
        loadButton.setForeground(new Color(255, 255, 224));
        loadButton.setContentAreaFilled(false);
        loadButton.setOpaque(true);
        loadPanel.add(loadButton, BorderLayout.CENTER);

        Label loadLabel = new Label("Load file");
        loadLabel.setFont(new Font("Arial", Font.BOLD, 12));
        loadLabel.setBackground(new Color(67, 79, 91));
        loadLabel.setForeground(new Color(255, 255, 224));
        loadLabel.setAlignment(Label.CENTER);
        loadPanel.add(loadLabel, BorderLayout.SOUTH);

        JPanel copyPanel = new JPanel();
        copyPanel.setBackground(new Color(67, 79, 91));
        operationPanel.add(copyPanel);

        CopyButton = new JButton("");
        CopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(CopyButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(CopyButton);
            }
        });

        CopyButton.setIcon(new ImageIcon("icons8_Copy_30px_1.png"));
        CopyButton.setBackground(new Color(67, 79, 91));
        CopyButton.setContentAreaFilled(false);
        CopyButton.setOpaque(true);
        CopyButton.addActionListener(arg0 -> {
            try {
                copiedShape = (Shape) DrawingBoard.getUpdatedShape().clone();

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            copiedShape.setPosition(new Point(DrawingBoard.getCurrentShape().getPosition().x + 20,
                    DrawingBoard.getCurrentShape().getPosition().y + 20));

            PasteButton.setEnabled(true);
            repaint();
        });

        copyPanel.setLayout(new BorderLayout(0, 0));
        copyPanel.add(CopyButton, BorderLayout.CENTER);
        CopyButton.setEnabled(false);

        Label CopyLabel = new Label("Copy");
        CopyLabel.setFont(new Font("Arial", Font.BOLD, 12));
        CopyLabel.setAlignment(Label.CENTER);
        CopyLabel.setForeground(new Color(255, 255, 224));
        CopyLabel.setBackground(new Color(67, 79, 91));
        copyPanel.add(CopyLabel, BorderLayout.SOUTH);
        JPanel pastePanel = new JPanel();
        operationPanel.add(pastePanel);

        PasteButton = new JButton("");
        PasteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(PasteButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(PasteButton);
            }
        });
        PasteButton.setIcon(new ImageIcon("icons8_Paste_30px.png"));
        PasteButton.setBackground(new Color(67, 79, 91));
        PasteButton.setForeground(new Color(255, 255, 224));
        PasteButton.setContentAreaFilled(false);
        PasteButton.setOpaque(true);
        PasteButton.addActionListener(arg0 -> {
            PaintDemo.addShape(copiedShape);
            DrawingBoard.setCurrentShape(null);
            PaintDemo.setUpdatedShape(null);
            removeButton.setEnabled(false);
            CopyButton.setEnabled(false);
            PasteButton.setEnabled(false);
            repaint();
        });
        pastePanel.setLayout(new BorderLayout(0, 0));
        pastePanel.add(PasteButton);
        PasteButton.setEnabled(false);

        Label PasteLabel = new Label("Paste");
        PasteLabel.setFont(new Font("Arial", Font.BOLD, 12));
        PasteLabel.setAlignment(Label.CENTER);
        PasteLabel.setBackground(new Color(67, 79, 91));
        PasteLabel.setForeground(new Color(255, 255, 224));
        pastePanel.add(PasteLabel, BorderLayout.SOUTH);

        JPanel removePanel = new JPanel();
        operationPanel.add(removePanel);

        JPanel fillPanel = new JPanel();
        operationPanel.add(fillPanel);

        removeButton = new JButton("");
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(removeButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(removeButton);
            }
        });
        removeButton.setIcon(new ImageIcon("delete.png"));
        removeButton.setBackground(new Color(67, 79, 91));
        removeButton.setContentAreaFilled(false);
        removeButton.setOpaque(true);
        removeButton.addActionListener(arg0 -> {
            PaintDemo.removeShape(DrawingBoard.getUpdatedShape());
            DrawingBoard.setCurrentShape(null);
            DrawingBoard.setUpdatedShape(null);
            PaintDemo.setUpdatedShape(null);
            CopyButton.setEnabled(false);
            PasteButton.setEnabled(false);
            removeButton.setEnabled(false);
            if (PaintDemo.getShapes().length == 0)
                FillWithColorButton.setEnabled(false);
            repaint();

        });
        fillPanel.setLayout(new BorderLayout(0, 0));

        FillWithColorButton = new JButton("");
        FillWithColorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(FillWithColorButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(FillWithColorButton);
            }
        });
        FillWithColorButton.setIcon(new ImageIcon("FillWithColor.png"));
        FillWithColorButton.setBackground(new Color(67, 79, 91));
        FillWithColorButton.setContentAreaFilled(false);
        FillWithColorButton.setOpaque(true);
        FillWithColorButton.setEnabled(false);
        fillPanel.add(FillWithColorButton);

        Label fill_with_color_Label = new Label("Fill With color");
        fill_with_color_Label.setFont(new Font("Arial", Font.BOLD, 12));
        fill_with_color_Label.setAlignment(Label.CENTER);
        fill_with_color_Label.setBackground(new Color(67, 79, 91));
        fill_with_color_Label.setForeground(new Color(255, 255, 224));
        fillPanel.add(fill_with_color_Label, BorderLayout.SOUTH);
        FillWithColorButton.addActionListener(e -> fillShape = true);
        removePanel.setLayout(new BorderLayout(0, 0));
        removePanel.add(removeButton);
        removeButton.setEnabled(false);

        Label RemovePanel = new Label("Remove");
        RemovePanel.setFont(new Font("Arial", Font.BOLD, 12));
        RemovePanel.setAlignment(Label.CENTER);
        RemovePanel.setBackground(new Color(67, 79, 91));
        RemovePanel.setForeground(new Color(255, 255, 224));
        removePanel.add(RemovePanel, BorderLayout.SOUTH);
        JPanel UndoRedoPanel = new JPanel();
        UndoRedoPanel.setBackground(new Color(67, 79, 91));
        UndoRedoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        UndoRedoPanel.setLayout(new BoxLayout(UndoRedoPanel, BoxLayout.X_AXIS));


        JPanel panel_4 = new JPanel();
        UndoRedoPanel.add(panel_4);
        panel_4.setBackground(new Color(67, 79, 91));
        panel_4.setLayout(new GridLayout(3, 1, 0, 0));

        Label label_4 = new Label("Fill Shape");
        label_4.setBackground(new Color(67, 79, 91));
        label_4.setAlignment(Label.CENTER);
        panel_4.add(label_4);

        NoFill = new JRadioButton("No Fill");
        NoFill.setBackground(new Color(67, 79, 91));
        NoFill.setSelected(true);

        Fill = new JRadioButton("Fill solid Color");
        Fill.setBackground(new Color(67, 79, 91));

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(NoFill);
        buttonGroup1.add(Fill);
        panel_4.add(NoFill);
        panel_4.add(Fill);

        fillColor = Color.BLUE;
        strokeColor = Color.BLACK;

        JPanel strokePanel = new JPanel();
        operationPanel.add(strokePanel);
        strokePanel.setLayout(new BorderLayout(0, 0));
        JPanel fillColorPanel = new JPanel();
        operationPanel.add(fillColorPanel);

        StrokeButton = new JButton("");
        StrokeButton.setBorder(new MatteBorder(0, 7, 0, 7, new Color(67, 79, 91)));
        StrokeButton.setBackground(strokeColor);
        StrokeButton.setToolTipText("Choose Stroke Color");
        StrokeButton.setContentAreaFilled(false);
        StrokeButton.setOpaque(true);

        fillButton = new JButton("");
        fillButton.setBorder(new MatteBorder(0, 7, 0, 7, new Color(67, 79, 91)));
        fillButton.setBackground(fillColor);
        fillButton.setContentAreaFilled(false);
        fillButton.setOpaque(true);
        fillButton.setToolTipText("Choose FillColor");
        fillButton.addActionListener(arg0 -> {
            fillColor = JColorChooser.showDialog(null, "Fill Color", fillColor);
            fillButton.setBackground(fillColor);
            DrawingBoard.setFillColor(fillColor);
        });
        fillColorPanel.setLayout(new BorderLayout(0, 0));
        fillColorPanel.add(fillButton);

        Label label_15 = new Label("Fill color");
        label_15.setForeground(new Color(255, 255, 224));
        label_15.setBackground(new Color(67, 79, 91));
        label_15.setFont(new Font("Arial Black", Font.BOLD, 12));
        label_15.setAlignment(Label.CENTER);
        fillColorPanel.add(label_15, BorderLayout.SOUTH);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(67, 79, 91));
        fillColorPanel.add(panel_1, BorderLayout.NORTH);
        StrokeButton.addActionListener(arg0 -> {
            strokeColor = JColorChooser.showDialog(null, "stroke Color", strokeColor);
            StrokeButton.setBackground(strokeColor);
            DrawingBoard.setStrokeColor(strokeColor);
        });
        strokePanel.add(StrokeButton, BorderLayout.CENTER);

        Label label_14 = new Label("Stroke ");
        label_14.setForeground(new Color(255, 255, 224));
        label_14.setFont(new Font("Arial Black", Font.BOLD, 12));
        label_14.setBackground(new Color(67, 79, 91));
        label_14.setAlignment(Label.CENTER);
        strokePanel.add(label_14, BorderLayout.SOUTH);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(67, 79, 91));
        strokePanel.add(panel, BorderLayout.NORTH);

        RedoButton = new JButton("");
        RedoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(RedoButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(RedoButton);
            }
        });
        RedoButton.setBackground(new Color(67, 79, 91));
        RedoButton.setIcon(new ImageIcon("icons8_Redo_30px.png"));
        RedoButton.setContentAreaFilled(false);
        RedoButton.setOpaque(true);
        RedoButton.addActionListener(arg0 -> {
            PaintDemo.redo();
            repaint();
        });
        RedoButton.setToolTipText("Redo");
        UndoRedoPanel.add(RedoButton);

        JButton UndoButton = new JButton("");
        UndoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(UndoButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(UndoButton);
            }
        });
        UndoButton.setIcon(new ImageIcon("icons8_Undo_30px.png"));
        UndoButton.setOpaque(true);
        UndoButton.setContentAreaFilled(false);
        UndoButton.addActionListener(arg0 -> {
            PaintDemo.undo();
            repaint();
        });
        UndoRedoPanel.add(UndoButton);
        UndoButton.setToolTipText("Undo");

        Label label_2 = new Label("Stroke Color");
        label_2.setForeground(new Color(255, 255, 224));

        Label label_3 = new Label("Fill Color");
        label_3.setForeground(new Color(255, 255, 224));

        ButtonGroup buttonGroup = new ButtonGroup();
        MainPanel.setLayout(new BorderLayout(0, 0));
        MainPanel.add(operationPanel, BorderLayout.CENTER);
        operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.X_AXIS));
        operationPanel.add(UndoRedoPanel);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.GRAY);
        contentPane.add(sidePanel, BorderLayout.EAST);
        sidePanel.setLayout(new CardLayout(0, 0));

        JPanel drawingPanel = new JPanel();
        sidePanel.add(drawingPanel);

        drawingPanel.setLayout(new BorderLayout(0, 0));
        JPanel shapesPanel = new JPanel();
        shapesPanel.setBorder(new MatteBorder(2, 0, 1, 0, (Color) new Color(0, 0, 0)));
        shapesPanel.setBackground(new Color(67, 79, 91));
        drawingPanel.add(shapesPanel, BorderLayout.CENTER);
        shapesPanel.setLayout(new GridLayout(7, 2, 0, 0));


        RecButton = new JButton();
        RecButton.setIcon(new ImageIcon("rectangle.png"));
        RecButton.setBackground(new Color(67, 79, 91));
        RecButton.setContentAreaFilled(false);
        RecButton.setOpaque(true);
        RecButton.setToolTipText("Rectangle");
        RecButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);

            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(1);
        });
        RecButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(RecButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(RecButton);
            }
        });
        shapesPanel.add(RecButton);

        LineButton = new JButton();
        LineButton.setIcon(new ImageIcon("line.png"));
        LineButton.setBackground(new Color(67, 79, 91));
        LineButton.setContentAreaFilled(false);
        LineButton.setOpaque(true);
        LineButton.setToolTipText("Line ");
        LineButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);
            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(2);
        });
        LineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(LineButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(LineButton);
            }
        });

        shapesPanel.add(LineButton);

        EllipseButton = new JButton();
        EllipseButton.setIcon(new ImageIcon("ellipse.png"));
        EllipseButton.setBackground(new Color(67, 79, 91));
        EllipseButton.setContentAreaFilled(false);
        EllipseButton.setOpaque(true);
        EllipseButton.setToolTipText("Ellipse");
        EllipseButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);
            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(3);
        });
        EllipseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(EllipseButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(EllipseButton);
            }
        });
        shapesPanel.add(EllipseButton);

        CircleButton = new JButton();
        CircleButton.setIcon(new ImageIcon("circle.png"));
        CircleButton.setBackground(new Color(67, 79, 91));
        CircleButton.setContentAreaFilled(false);
        CircleButton.setOpaque(true);
        CircleButton.setToolTipText("Circle");
        CircleButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);
            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(4);
        });
        CircleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(CircleButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(CircleButton);
            }
        });

        shapesPanel.add(CircleButton);

        TriangleButton = new JButton();
        TriangleButton.setIcon(new ImageIcon("triangle.png"));
        TriangleButton.setBackground(new Color(67, 79, 91));
        TriangleButton.setContentAreaFilled(false);
        TriangleButton.setOpaque(true);
        TriangleButton.setToolTipText("Triangle");
        TriangleButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);
            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(5);
        });
        TriangleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(TriangleButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(TriangleButton);
            }
        });

        shapesPanel.add(TriangleButton);

        SquareButton = new JButton();
        SquareButton.setIcon(new ImageIcon("square.png"));
        SquareButton.setBackground(new Color(67, 79, 91));
        SquareButton.setContentAreaFilled(false);
        SquareButton.setOpaque(true);
        SquareButton.setToolTipText("Square");
        SquareButton.addActionListener(arg0 -> {
            if (Fill.isSelected()) {
                DrawingBoard.setFill(true);
            } else if (NoFill.isSelected()) {
                DrawingBoard.setFill(false);
            }
            FillWithColorButton.setEnabled(true);
            DrawingBoard.setCurrentAction(6);
        });
        SquareButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButtonBackground(SquareButton);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                returnButtonBackground(SquareButton);
            }
        });

        shapesPanel.add(SquareButton);

        JPanel panel_6 = new JPanel();
        drawingPanel.add(panel_6, BorderLayout.SOUTH);
        panel_6.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel panel_7 = new JPanel();
        panel_7.setBorder(new MatteBorder(2, 0, 1, 0, (Color) new Color(0, 0, 0)));
        panel_6.add(panel_7);
        panel_7.setLayout(new GridLayout(2, 1, 0, 0));

        Label label_5 = new Label("Thickness");
        label_5.setForeground(new Color(250, 235, 215));
        label_5.setFont(new Font("Arial", Font.BOLD, 12));
        label_5.setBackground(new Color(67, 79, 91));
        label_5.setAlignment(Label.CENTER);
        panel_7.add(label_5);

        JSlider thicknessSlider = new JSlider(1, 20, 2);
        thicknessSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DrawingBoard.setThick(thicknessSlider.getValue());
            }
        });
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setMajorTickSpacing(2);
        thicknessSlider.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
        thicknessSlider.setBackground(new Color(67, 79, 91));
        panel_7.add(thicknessSlider);

        JPanel panel_8 = new JPanel();
        panel_8.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
        panel_6.add(panel_8);
        panel_8.setLayout(new GridLayout(2, 1, 0, 0));

        Label label_8 = new Label("Transparent");
        label_8.setForeground(new Color(250, 235, 215));
        label_8.setFont(new Font("Arial", Font.BOLD, 12));
        label_8.setBackground(new Color(67, 79, 91));
        label_8.setAlignment(Label.CENTER);
        panel_8.add(label_8);

        JSlider tranSlider = new JSlider(1, 99, 99);
        tranSlider.addChangeListener(arg0 -> DrawingBoard.setTransparent(tranSlider.getValue() * 0.01));
        tranSlider.setPaintTicks(true);
        tranSlider.setPaintLabels(true);
        tranSlider.setMinorTickSpacing(1);
        tranSlider.setMajorTickSpacing(10);
        tranSlider.setBackground(new Color(67, 79, 91));
        panel_8.add(tranSlider);

        JPanel ShapeInfoPanel = new JPanel();
        sidePanel.add(ShapeInfoPanel);

        contentPane.add(DrawingBoard, BorderLayout.CENTER);
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getCopyButton() {
        return CopyButton;
    }

    public boolean IsFillShape() {
        return fillShape;
    }

    public void setFillShape(boolean fill) {
        this.fillShape = fill;
    }

    private void changeButtonBackground(JButton b) {
        b.setBackground(Color.darkGray);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
    }

    private void returnButtonBackground(JButton b) {
        b.setBackground(new Color(67, 79, 91));
        b.setContentAreaFilled(false);
        b.setOpaque(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                PaintApp frame = new PaintApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
