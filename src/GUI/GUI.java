package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.NoSuchElementException;

public class GUI extends JFrame {
    /**
     * I want this constructor to be a public!
     */
    public GUI() {
        /*Меню */
        Container frameContainer = new Container();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon image_play =new ImageIcon("Pictures\\play.png");
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu new_file = new JMenu("Создать");
        JMenuItem open_file = new JMenuItem("Открыть");
        JMenuItem play_button = new JMenuItem(image_play);
        JMenuItem save_file = new JMenuItem("Сохранить");
        JMenuItem Pattern1 = new JMenuItem("Шаблон1");
        JMenuItem Pattern2 = new JMenuItem("Шаблон2");
        new_file.add(Pattern1);
        new_file.add(Pattern2);
        fileMenu.add(new_file);
        fileMenu.add(open_file);
        fileMenu.add(save_file);
        menuBar.add(fileMenu);
        menuBar.add(play_button);
        frameContainer.add(menuBar);
        setJMenuBar(menuBar);
        setSize(800,600);
        save_file.setEnabled(false);
        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setCurrentDirectory(new File("."));
                int ret = fileOpen.showDialog(null, "Открыть");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Чтение лабиринта из файла;
                        Labyrinth labyrinth = new Labyrinth(fileOpen.getSelectedFile());
                        // рисуем чистый лабиринт;
                        //labyrinth.printLabyrinth();
                        // Здесь должно быть указание координат старта и финиша. Пока строим один путь;
                        labyrinth.floodFill(1, 1); // Вызов алгоритма;
                        // рисуем лабиринт с путем;
                        labyrinth.printLabyrinth();
                        // Если что-то открыли, можем результат записать в файл
                        save_file.setEnabled(true);
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
                    } catch(IOException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (NoSuchElementException e) {
                        System.exit(0);
                    }
                }

            }

        });

    }
}