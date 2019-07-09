package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.NoSuchElementException;

public class GUI extends JFrame {
    private Labyrinth labyrinth;
    private DrawLabyrinth drawLabyrinth;
    private JMenuItem scale_increase;
    private JMenuItem scale_decrease;
    private JMenuItem play_button;
    private JMenuItem save_file;
    public GUI() {
        /*Меню */

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu new_file = new JMenu("Создать");
        JMenuItem clean_lab = new JMenuItem("Новый");
        JMenuItem open_file = new JMenuItem("Открыть");
        play_button = new JMenuItem(new ImageIcon("Pictures\\play.png"));
        scale_increase = new JMenuItem(new ImageIcon("Pictures\\zoom-in-button.png"));
        scale_decrease = new JMenuItem(new ImageIcon("Pictures\\zoom-out.png"));
        save_file = new JMenuItem("Сохранить");
        JMenuItem pattern1 = new JMenuItem("Шаблон1");
        JMenuItem pattern2 = new JMenuItem("Шаблон2");
        new_file.add(pattern1);
        new_file.add(pattern2);
        fileMenu.add(clean_lab);
        fileMenu.add(new_file);
        fileMenu.add(open_file);
        fileMenu.add(save_file);
        menuBar.add(fileMenu);
        scale_increase.setEnabled(false);
        scale_decrease.setEnabled(false);
        menuBar.add(scale_increase);
        menuBar.add(scale_decrease);
        menuBar.add(play_button);
        setJMenuBar(menuBar);
        setSize(400,200);
        setVisible(true);
        save_file.setEnabled(false);
        play_button.setEnabled((false));
        scale_decrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.ScaleDecrease();
                pack();

            }
        });
        scale_increase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.ScaleIncrease();
                pack();

            }
        });
        clean_lab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Пустой лабиринт
                if(drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());
                labyrinth = new Labyrinth();
                setLabyrinth();
            }
        });
        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setCurrentDirectory(new File("."));
                int ret = fileOpen.showDialog(null, "Открыть");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        //  Чтение лабиринта из файла;
                        if(drawLabyrinth != null)
                            remove(drawLabyrinth.getJPanel());
                        labyrinth = new Labyrinth(fileOpen.getSelectedFile());
                        setLabyrinth();
                    } catch(IOException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (NoSuchElementException e) {
                        System.exit(0);
                    }
                }

            }

        });
        pattern1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    // Случайная генерация лабиринта;
                    if(drawLabyrinth != null)
                        remove(drawLabyrinth.getJPanel());
                    labyrinth = new Labyrinth(13);
                    setLabyrinth();

            }
        });

    }
    private void setLabyrinth(){
        labyrinth.printLabyrinth();
        drawLabyrinth = new DrawLabyrinth(labyrinth);
        labyrinth.printLabyrinth();
        add(drawLabyrinth.getJPanel(),BorderLayout.NORTH);
        pack();
        scale_increase.setEnabled(true);
        scale_decrease.setEnabled(true);
        play_button.setEnabled(true);
        setPlay(play_button);
        save_file.setEnabled(true);
        setSaveButton(save_file,labyrinth);
    }
    private void setPlay(JMenuItem play_button) {
        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.clearLab();
//                labyrinth.floodFill(labyrinth.getStart()); // Вызов алгоритма;
                labyrinth.FindA(labyrinth.getStart(),labyrinth.getFinish());
                repaint();
                pack();
                //setSize(800,600);
                labyrinth.printLabyrinth();
            }
        });
    }

    private void setSaveButton(JMenuItem save_file,Labyrinth labyrinth){
        save_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSave = new JFileChooser();
                fileSave.setCurrentDirectory(new File("."));
                fileSave.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int ret = fileSave.showDialog(null, "Сохранить");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileSave.getSelectedFile();
                    try {
                        labyrinth.save(file);
                        System.out.println("Save successfully");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}