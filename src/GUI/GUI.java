package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.NoSuchElementException;

public class GUI extends JFrame {
    Labyrinth labyrinth;
    DrawLabyrinth drawLabyrinth;
    public GUI() {
        /*Меню */

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu new_file = new JMenu("Создать");
        JMenuItem clean_lab = new JMenuItem("Новый");
        JMenuItem open_file = new JMenuItem("Открыть");
        JMenuItem play_button = new JMenuItem(new ImageIcon("Pictures\\play.png"));
        JMenuItem scale_increase = new JMenuItem(new ImageIcon("Pictures\\zoom-in-button.png"));
        JMenuItem scale_decrease = new JMenuItem(new ImageIcon("Pictures\\zoom-out.png"));
        JMenuItem save_file = new JMenuItem("Сохранить");
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
        setSize(800,600);
        setVisible(true);
        save_file.setEnabled(false);
        play_button.setEnabled((false));
        scale_decrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.ScaleDecrease();
                pack();
                setSize(800,600);
            }
        });
        scale_increase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.ScaleIncrease();
                pack();
                setSize(800,600);
            }
        });
        clean_lab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Пустой лабиринт
                if(drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());
                labyrinth = new Labyrinth();
                labyrinth.printLabyrinth();
                drawLabyrinth = new DrawLabyrinth(labyrinth);
                labyrinth.printLabyrinth();
                pack();
                setSize(800,600);
                add(drawLabyrinth.getJPanel(),BorderLayout.NORTH);
                scale_increase.setEnabled(true);
                scale_decrease.setEnabled(true);
                play_button.setEnabled(true);
                setPlay(play_button, labyrinth);
                save_file.setEnabled(true);
                setSaveButton(save_file,labyrinth);
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
                        drawLabyrinth = new DrawLabyrinth(labyrinth);
                        pack();
                        setSize(800,600);
                        add(drawLabyrinth.getJPanel(),BorderLayout.NORTH);
                        scale_increase.setEnabled(true);
                        scale_decrease.setEnabled(true);
                        play_button.setEnabled(true);
                        setPlay(play_button, labyrinth);

                        // Если что-то открыли, можем результат записать в файл
                        save_file.setEnabled(true);
                        setSaveButton(save_file,labyrinth);
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
                    // Рисуем чистый лабиринт;
//                    labyrinth.printLabyrinth();
                    drawLabyrinth = new DrawLabyrinth(labyrinth);
                    pack();
                    setSize(800,600);
                    add(drawLabyrinth.getJPanel(),BorderLayout.NORTH);
                    scale_increase.setEnabled(true);
                    scale_decrease.setEnabled(true);
                    play_button.setEnabled(true);
                    setPlay(play_button, labyrinth);
                    //remove(drawLabyrinth.getJPanel());
                    // Здесь должно быть указание координат старта и финиша. Пока строим один путь;

                    // Рисуем лабиринт с путем;
                    //labyrinth.printLabyrinth();
                    // Если что-то открыли, можем результат записать в файл
                    save_file.setEnabled(true);
                    setSaveButton(save_file,labyrinth);

            }
        });

    }

    private void setPlay(JMenuItem play_button, Labyrinth labyrinth) {
        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.clearLab();
                labyrinth.floodFill(labyrinth.getStart()); // Вызов алгоритма;
                pack();
                setSize(800,600);
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