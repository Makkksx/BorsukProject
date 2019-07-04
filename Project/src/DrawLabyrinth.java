package Project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Drawing the window with labyrinth by matrix;

public class DrawLabyrinth {
    JRadioButton min_path = new JRadioButton("Min Path");
    JRadioButton max_path = new JRadioButton("Max Path");

    public DrawLabyrinth() {
        JFrame frame = new JFrame("Generated Labyrinth");
        frame.setBounds(630, 150, 650, 650);
        JPanel container = new JPanel();
        frame.setLayout(null);
        frame.getContentPane().add(container);
        frame.setVisible(true);
        // pathes;
        min_path.setBounds(50, 10, 200, 20);
        max_path.setBounds(400, 10, 200, 20);
        ButtonGroup patternGroup = new ButtonGroup();
        patternGroup.add(min_path);
        patternGroup.add(max_path);
        frame.add(min_path);
        min_path.setSelected(true); // Selected in a default mode;
        frame.add(max_path);
    }
}
