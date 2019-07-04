package Project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import Project.Labyrinth.*;

public class GUI extends JFrame {
    JLabel mainText = new JLabel("START MENU");
    JRadioButton file_choice = new JRadioButton("InputFromFile");
    JRadioButton random_choice = new JRadioButton("RandomInput");
    JTextField input = new JTextField("", 1);
    JLabel fileText = new JLabel("Enter a path to file if you had choose inputting from file!");
    JLabel randomText = new JLabel("Enter a style of the labyrinth if you had choose a random generating!");
    JRadioButton pattern1 = new JRadioButton("Pattern1");
    JRadioButton pattern2 = new JRadioButton("Pattern2");
    Button button = new Button("START");
    /**
     * I want this constructor to be a public!
     */
    public GUI() {
        super("LabyrinthJavaProject - BorSukiTrushnikovTeam");
        this.setBounds(100, 100, 1240, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel container = new JPanel();
        container.setLayout(null);
        getContentPane().add(container);
        //container.setLayout(new GridLayout(3, 2, 2, 2));

        // main text;
        container.add(mainText);
        mainText.setBounds(150, 10, 100, 20);
        // type of the labyrinth building;
        file_choice.setBounds(20, 50, 200, 20);
        random_choice.setBounds(250, 50, 200, 20);
        ButtonGroup choiceGroup = new ButtonGroup();
        choiceGroup.add(file_choice);
        choiceGroup.add(random_choice);
        container.add(file_choice);
        file_choice.setSelected(true); // Selected in a default mode;
        container.add(random_choice);
        // file choice text;
        container.add(fileText);
        fileText.setBounds(20, 100, 400, 20);
        // input a file path (file choice only);
        input.setBounds(20, 130, 400, 20);
        container.add(input);
        // random choice text;
        container.add(randomText);
        randomText.setBounds(20, 160, 400, 20);
        // style of the labyrinth random generating;
        pattern1.setBounds(20, 190, 200, 20);
        pattern2.setBounds(250, 190, 200, 20);
        ButtonGroup patternGroup = new ButtonGroup();
        patternGroup.add(pattern1);
        patternGroup.add(pattern2);
        container.add(pattern1);
        pattern1.setSelected(true); // Selected in a default mode;
        container.add(pattern2);
        // a start button;
        button.setBounds(140, 250, 100, 20);
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    private class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (file_choice.isSelected()) {
                Labyrinth labyrinth = new Labyrinth(input.getText());
            }
            else {
                // it will be more than 2 patterns;
                if (pattern1.isSelected()) {
                    Labyrinth labyrinth = new Labyrinth(1);
                }
                if (pattern2.isSelected()) {
                    Labyrinth labyrinth = new Labyrinth(2);
                }
            }
        }
    }
}
