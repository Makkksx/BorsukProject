package GUI;

import Matrix.Labyrinth;

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu new_file = new JMenu("Создать");
        JMenuItem open_file = new JMenuItem("Открыть");
        JMenuItem save_file = new JMenuItem("Сохранить");
        JMenuItem Pattern1 = new JMenuItem("Шаблон1");
        JMenuItem Pattern2 = new JMenuItem("Шаблон2");
        new_file.add(Pattern1);
        new_file.add(Pattern2);
        fileMenu.add(new_file);
        fileMenu.add(open_file);
        fileMenu.add(save_file);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        setSize(800,600);

        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showDialog(null, "Открыть файл");
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