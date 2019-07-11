package GUI;

import Matrix.Algorithm;
import Matrix.Labyrinth;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.NoSuchElementException;

public class GUI extends JFrame {
    private Labyrinth labyrinth;
    private DrawLabyrinth drawLabyrinth;
    private Algorithm algorithm;
    private JMenuItem scale_increase;
    private JMenuItem scale_decrease;
    private JMenuItem play_button;
    private JMenuItem save_file;
    private JMenuItem next_step;
    private JMenuItem refresh;
    private JButton setFinish;
    private JButton setStart;

    public GUI() {
        /*Меню */
        JButton reference = new JButton("Справка", new ImageIcon("Pictures\\button.png"));
        JButton creators = new JButton("Об авторах", new ImageIcon("Pictures\\button.png"));
        creators.setHorizontalTextPosition(SwingConstants.CENTER);
        creators.setBorder(null);
        creators.setPreferredSize(new Dimension(40, 30));

        reference.setHorizontalTextPosition(SwingConstants.CENTER);
        reference.setBorder(null);
        reference.setPreferredSize(new Dimension(40, 30));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel gif = new JLabel();
        gif.setIcon(new ImageIcon("Pictures\\gifka.gif"));
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenu new_file = new JMenu("Создать");
        JMenuItem new_lab = new JMenuItem("Новый");
        JMenuItem open_file = new JMenuItem("Открыть");
        JMenuItem exit = new JMenuItem("Выход");
        refresh = new JMenuItem(new ImageIcon("Pictures\\refresh.png"));
        play_button = new JMenuItem(new ImageIcon("Pictures\\play.png"));
        scale_increase = new JMenuItem(new ImageIcon("Pictures\\zoom-in-button.png"));
        scale_decrease = new JMenuItem(new ImageIcon("Pictures\\zoom-out.png"));
        save_file = new JMenuItem("Сохранить");
        next_step = new JMenuItem(new ImageIcon("Pictures\\next.png"));
        JMenuItem pattern1 = new JMenuItem("Землеройка");
        JMenuItem pattern2 = new JMenuItem("Параллельки");
        fileMenu.setPreferredSize(new Dimension(70, 30));
        fileMenu.setHorizontalTextPosition(SwingConstants.CENTER);
        JButton cancel = new JButton("Отмена");
        setFinish = new JButton("Финиш");
        setStart = new JButton("Старт");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(setFinish, BorderLayout.LINE_START);
        panel1.add(setStart, BorderLayout.AFTER_LAST_LINE);
        panel1.add(cancel, BorderLayout.AFTER_LAST_LINE);
        panel2.add(scale_decrease);
        panel2.add(scale_increase);
        panel2.add(next_step);
        panel2.add(refresh);
        panel2.add(play_button);
        add(panel1, BorderLayout.LINE_END);
        add(panel2, BorderLayout.LINE_START);
        new_file.add(pattern1);
        new_file.add(pattern2);
        fileMenu.add(new_lab);
        fileMenu.add(new_file);
        fileMenu.add(open_file);
        fileMenu.add(save_file);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        menuBar.add(reference);
        menuBar.add(creators);
        scale_increase.setEnabled(false);
        scale_decrease.setEnabled(false);
        setJMenuBar(menuBar);
        setSize(400, 200);
        setVisible(true);
        save_file.setEnabled(false);
        play_button.setEnabled(false);
        next_step.setEnabled(false);
        refresh.setEnabled(false);
        setFinish.setEnabled(false);
        setStart.setEnabled(false);
        cancel.setEnabled(false);
        cancel.addActionListener(e -> {
            if (drawLabyrinth.getButtonColumn().getFinishBool()) {
                drawLabyrinth.getButtonColumn().setFINISH(setFinish, cancel, false);
                setFinish.setIcon(null);
                setFinish.setPreferredSize(new Dimension(75, 26));
                setFinish.setBorder(new LineBorder(Color.BLACK));
            }
            if (drawLabyrinth.getButtonColumn().getStartBool()) {
                drawLabyrinth.getButtonColumn().setSTART(setStart, cancel, false);
                setStart.setIcon(null);
                setStart.setPreferredSize(new Dimension(75, 26));
                setStart.setBorder(new LineBorder(Color.BLACK));
            }
            cancel.setEnabled(false);
        });
        exit.addActionListener(e -> System.exit(0));
        creators.addActionListener(e -> {
            URI uri;
            try {
                uri = new URI("https://github.com/Makkksx/BorsukProject");
                JButton button = new JButton();
                button.setText("<HTML><FONT color=\"#000099\"><U>Репозиторий</U></FONT></HTML>");
                button.setHorizontalAlignment(SwingConstants.LEFT);
                button.setBorderPainted(false);
                button.setOpaque(false);
                button.setBackground(Color.WHITE);
                button.setToolTipText(uri.toString());

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().browse(uri);
                            } catch (IOException ex) { /* TODO: error handling */ }
                        }  /* TODO: error handling */

                    }
                });
                JOptionPane.showMessageDialog(null, button, "Об авторах", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ignored) {

            }
            creators.setIcon(new ImageIcon("Pictures\\button.png"));
        });
        reference.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                reference.setIcon(new ImageIcon("Pictures\\buttonpressed.png"));
            }
        });
        setFinish.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (setFinish.getModel().isEnabled()) {
                    if (drawLabyrinth != null) {
                        if (drawLabyrinth.getButtonColumn().getStartBool()) {
                            drawLabyrinth.getButtonColumn().setSTART(setStart, cancel, false);
                            setStart.setIcon(null);
                            setStart.setPreferredSize(new Dimension(75, 26));
                            setStart.setBorder(new LineBorder(Color.BLACK));
                        }
                        setFinish.setIcon(new ImageIcon("Pictures\\buttonpressed1.png"));
                        setFinish.setHorizontalTextPosition(SwingConstants.CENTER);
                        setFinish.setBorder(null);
                        cancel.setEnabled(true);
                    }
                }
            }
        });
        setStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (setStart.getModel().isEnabled()) {
                    if (drawLabyrinth != null) {
                        if (drawLabyrinth.getButtonColumn().getFinishBool()) {
                            drawLabyrinth.getButtonColumn().setSTART(setFinish, cancel, false);
                            setFinish.setIcon(null);
                            setFinish.setPreferredSize(new Dimension(75, 26));
                            setFinish.setBorder(new LineBorder(Color.BLACK));
                        }
                        setStart.setIcon(new ImageIcon("Pictures\\buttonpressed1.png"));
                        setStart.setHorizontalTextPosition(SwingConstants.CENTER);
                        setStart.setBorder(null);
                        cancel.setEnabled(true);
                    }
                }
            }
        });
        creators.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                creators.setIcon(new ImageIcon("Pictures\\buttonpressed.png"));
            }
        });

        setFinish.addActionListener(e -> drawLabyrinth.getButtonColumn().setFINISH(setFinish, cancel, true));

        setStart.addActionListener(e -> drawLabyrinth.getButtonColumn().setSTART(setStart, cancel, true));


        reference.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Нажмите 'Файл -> Новый', чтобы создать новый лабиринт\n\n" +
                    "Ячейки\n" + "<html><font color=YELLOW> Желтая</font> - старт</html> \n" +
                    "<html><font color=RED>Красная</font> - финиш</html>\n" +
                    "<html><font color=WHITE>Белая</font> - свободная\n" +
                    "<html><font color=GRAY>Серая</font> - стена\n" +
                    "<html><font color=BLUE>Синяя</font> - посещенные вершины\n" +
                    "<html><font color=ORANGE>Оранжевая</font> - может быть посещена\n" +
                    "<html><font color=GREEN>Зеленая</font> - путь\n", "Справка", JOptionPane.INFORMATION_MESSAGE);
            reference.setIcon(new ImageIcon("Pictures\\button.png"));
        });
        refresh.addActionListener(e -> {
            labyrinth.clearLab();
            repaint();
            pack();
            algorithm = new Algorithm(labyrinth);
            drawLabyrinth.getJTable().setEnabled(true);
            next_step.setEnabled(true);
            play_button.setEnabled(true);
            setFinish.setEnabled(true);
            setStart.setEnabled(true);
            save_file.setEnabled(true);
        });

        save_file.addActionListener(e -> {
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
        });

        next_step.addActionListener(e -> {
            save_file.setEnabled(false);
            drawLabyrinth.getJTable().setEnabled(false);
            if (algorithm.stepFindA(labyrinth)) {
                next_step.setEnabled(false);
                play_button.setEnabled(false);
                setFinish.setEnabled(false);
                setStart.setEnabled(false);

            } else if (algorithm.openSetIsEmpty()) {
                JOptionPane.showMessageDialog(drawLabyrinth.getJPanel(), "Путь не найден");
                drawLabyrinth.getJTable().setEnabled(false);
                next_step.setEnabled(false);
                play_button.setEnabled(false);
                setFinish.setEnabled(false);
                setStart.setEnabled(false);
            }
            repaint();
            pack();

            labyrinth.printLabyrinth();
        });

        scale_decrease.addActionListener(e -> {
            drawLabyrinth.ScaleDecrease();
            pack();
        });

        scale_increase.addActionListener(e -> {
            drawLabyrinth.ScaleIncrease();
            pack();
        });
        play_button.addActionListener(e -> {
            if (!algorithm.FindA(labyrinth) && algorithm.openSetIsEmpty()) {
                JOptionPane.showMessageDialog(drawLabyrinth.getJPanel(), "Путь не найден");
                drawLabyrinth.getJTable().setEnabled(false);
            }
            next_step.setEnabled(false);
            play_button.setEnabled(false);
            setFinish.setEnabled(false);
            setStart.setEnabled(false);
            save_file.setEnabled(false);
            repaint();
            pack();
            labyrinth.printLabyrinth();
        });

        new_lab.addActionListener(e -> {
            // Пустой лабиринт

            String size_lab = "";
            while ((size_lab != null) && (size_lab.length() > 3 || !size_lab.matches("[0-9]+") || Integer.parseInt(size_lab) > 25 || Integer.parseInt(size_lab) < 3))
                size_lab = JOptionPane.showInputDialog(null, "Размер лабиринта (От 3 до 25)");
            if (size_lab != null) {
                if (drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());
                remove(gif);
                labyrinth = new Labyrinth(Integer.parseInt(size_lab));
                algorithm = new Algorithm(labyrinth);
                setLabyrinth();
            }


        });


        open_file.addActionListener(actionEvent -> {

            JFileChooser fileOpen = new JFileChooser();
            fileOpen.setCurrentDirectory(new File("."));
            int ret = fileOpen.showDialog(null, "Открыть");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    //  Чтение лабиринта из файла;
                    if (drawLabyrinth != null)
                        remove(drawLabyrinth.getJPanel());
                    labyrinth = new Labyrinth(fileOpen.getSelectedFile());
                    algorithm = new Algorithm(labyrinth);
                    setLabyrinth();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (NoSuchElementException e) {
                    System.exit(0);
                }
            }
            remove(gif);
        });

        pattern1.addActionListener(actionEvent -> {
            // Случайная генерация лабиринта;
            if (drawLabyrinth != null)
                remove(drawLabyrinth.getJPanel());
            remove(gif);
            labyrinth = new Labyrinth(13, 1);
            algorithm = new Algorithm(labyrinth);
            setLabyrinth();
        });

        pattern2.addActionListener(actionEvent -> {
            // Случайная генерация лабиринта;
            if (drawLabyrinth != null)
                remove(drawLabyrinth.getJPanel());
            remove(gif);
            labyrinth = new Labyrinth(13, 2);
            algorithm = new Algorithm(labyrinth);
            setLabyrinth();
        });
        getContentPane().add(gif, BorderLayout.SOUTH);
        pack();
    }

    private void setLabyrinth() {
        labyrinth.printLabyrinth();
        drawLabyrinth = new DrawLabyrinth(labyrinth);
        labyrinth.printLabyrinth();
        add(drawLabyrinth.getJPanel(), BorderLayout.SOUTH);
        pack();
        scale_increase.setEnabled(true);
        scale_decrease.setEnabled(true);
        play_button.setEnabled(true);
        save_file.setEnabled(true);
        next_step.setEnabled(true);
        refresh.setEnabled(true);
        setFinish.setEnabled(true);
        setStart.setEnabled(true);
    }

}