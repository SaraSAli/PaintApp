package eg.edu.alexu.csd.oop.draw.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class About extends JFrame {
    private JPanel contentPane;

    public About() {
        setTitle("About");
        setBounds(100, 100, 450, 300);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JEditorPane About = new JEditorPane();
        About.setEditable(false);
        About.setText("About Application:\r\nIt is an incredible Paint App\r\nFor any inquiry Please contact me");
        contentPane.add(About, BorderLayout.CENTER);
    }
}
