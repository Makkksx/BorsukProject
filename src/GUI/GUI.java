package GUI;

import Matrix.Algorithm;
import Matrix.Labyrinth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
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
        JButton reference =  new JButton("Справка",new ImageIcon("Pictures\\button.png"));
        JButton creators =  new JButton("Об авторах",new ImageIcon("Pictures\\button.png"));
        creators.setHorizontalTextPosition(SwingConstants.CENTER);
        creators.setBorder(null);
        creators.setPreferredSize(new Dimension(40,30));

        reference.setHorizontalTextPosition(SwingConstants.CENTER);
        reference.setBorder(null);
        reference.setPreferredSize(new Dimension(40,30));
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
       // fileMenu.setIcon(new ImageIcon("Pictures\\button.png"));
        fileMenu.setPreferredSize(new Dimension(70,30));
        fileMenu.setHorizontalTextPosition(SwingConstants.CENTER);
       // fileMenu.setForeground(Color.orange);
        setFinish = new JButton("Финиш");
        setStart = new JButton("Старт");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(setFinish,BorderLayout.LINE_START);
        panel1.add(setStart,BorderLayout.AFTER_LAST_LINE);
        panel2.add(scale_decrease);
        panel2.add(scale_increase);
        panel2.add(next_step);
        panel2.add(refresh);
        panel2.add(play_button);
        add(panel1,BorderLayout.LINE_END);
        add(panel2,BorderLayout.LINE_START);

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
        setSize(400,200);
        setVisible(true);
        save_file.setEnabled(false);
        play_button.setEnabled(false);
        next_step.setEnabled(false);
        refresh.setEnabled(false);
        setFinish.setEnabled(false);
        setStart.setEnabled(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        creators.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                         } else { /* TODO: error handling */ }

                        }
                    });
                    JOptionPane.showMessageDialog(null,button);
                }catch (Exception ex){

                }
                creators.setIcon(new ImageIcon("Pictures\\button.png"));
            }
        });
        reference.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                reference.setIcon(new ImageIcon("Pictures\\buttonpressed.png"));
            }
        });
        creators.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                creators.setIcon(new ImageIcon("Pictures\\buttonpressed.png"));
            }
        });

        setFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.getButtonColumn().setFINISH();
            }
        });

        setStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.getButtonColumn().setSTART();
            }
        });

        setSaveButton(save_file);
        reference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Нажмите 'Файл -> Новый', чтобы создать новый лабиринт\n\n" +
                        "Ячейки\n" + "<html><font color=YELLOW>Желтая</font> - старт</html> \n" +
                        "<html><font color=RED>Красная</font> - финиш</html>\n" +
                        "<html><font color=WHITE>Белая</font> - свободная\n" +
                        "<html><font color=GRAY>Серая</font> - стена\n");
                reference.setIcon(new ImageIcon("Pictures\\button.png"));
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.clearLab();
                repaint();
                pack();
                algorithm = new Algorithm(labyrinth);
                drawLabyrinth.getJTable().setEnabled(true);
                next_step.setEnabled(true);
                play_button.setEnabled(true);
                setFinish.setEnabled(true);
                setStart.setEnabled(true);
            }
        });

        next_step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabyrinth.getJTable().setEnabled(false);
                if (algorithm.stepFindA(labyrinth)) {
                    next_step.setEnabled(false);
                    play_button.setEnabled(false);
                    setFinish.setEnabled(false);
                    setStart.setEnabled(false);
                }
                repaint();
                pack();
                if (algorithm.openSetIsEmpty()){
                    JOptionPane.showMessageDialog(drawLabyrinth.getJPanel(), "Путь не найден");
                    drawLabyrinth.getJTable().setEnabled(false);
                    next_step.setEnabled(false);
                    play_button.setEnabled(false);
                    setFinish.setEnabled(false);
                    setStart.setEnabled(false);
                }
                labyrinth.printLabyrinth();
            }
        });

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

        new_lab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Пустой лабиринт
                if(drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());


                String size_lab = "30";
                while((size_lab != null) && (size_lab.length() > 3 || !size_lab.matches("[0-9]+") || Integer.parseInt(size_lab) > 25 || Integer.parseInt(size_lab) < 3))
                    size_lab = JOptionPane.showInputDialog(null, "Размер лабиринта (От 3 до 25)");
                if (size_lab != null) {
                    remove(gif);
                    labyrinth = new Labyrinth(Integer.parseInt(size_lab));
                    algorithm = new Algorithm(labyrinth);
                    setLabyrinth();
                }


            }
        });


        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                remove(gif);
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setCurrentDirectory(new File("."));
                int ret = fileOpen.showDialog(null, "Открыть");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        //  Чтение лабиринта из файла;
                        if(drawLabyrinth != null)
                            remove(drawLabyrinth.getJPanel());
                        labyrinth = new Labyrinth(fileOpen.getSelectedFile());
                        algorithm = new Algorithm(labyrinth);
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
                remove(gif);
                // Случайная генерация лабиринта;
                if(drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());
                String size_lab = "30";
                while((size_lab != null) && (size_lab.length() > 3 || !size_lab.matches("[0-9]+") || Integer.parseInt(size_lab) > 25 || Integer.parseInt(size_lab) < 3))
                    size_lab = JOptionPane.showInputDialog(null, "Размер лабиринта (От 3 до 25)");
                if (size_lab != null) {
                    remove(gif);
                    labyrinth = new Labyrinth(Integer.parseInt(size_lab),1);
                    algorithm = new Algorithm(labyrinth);
                    setLabyrinth();
                }
            }
        });

        pattern2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                remove(gif);
                // Случайная генерация лабиринта;
                if(drawLabyrinth != null)
                    remove(drawLabyrinth.getJPanel());
                String size_lab = "30";
                while((size_lab != null) && (size_lab.length() > 3 || !size_lab.matches("[0-9]+") || Integer.parseInt(size_lab) > 25 || Integer.parseInt(size_lab) < 3))
                    size_lab = JOptionPane.showInputDialog(null, "Размер лабиринта (От 3 до 25)");
                if (size_lab != null) {
                    remove(gif);
                    labyrinth = new Labyrinth(Integer.parseInt(size_lab),2);
                    algorithm = new Algorithm(labyrinth);
                    setLabyrinth();
                }
            }
        });
        getContentPane().add(gif,BorderLayout.SOUTH);
        //ffffff

        pack();
    }

    private void setLabyrinth() {
        labyrinth.printLabyrinth();
        drawLabyrinth = new DrawLabyrinth(labyrinth);
        labyrinth.printLabyrinth();
        add(drawLabyrinth.getJPanel(),BorderLayout.SOUTH);
        pack();
        scale_increase.setEnabled(true);
        scale_decrease.setEnabled(true);
        play_button.setEnabled(true);
        setPlay(play_button);
        save_file.setEnabled(true);
        next_step.setEnabled(true);
        refresh.setEnabled(true);
        setFinish.setEnabled(true);
        setStart.setEnabled(true);
    }

    private void setPlay(JMenuItem play_button) {
        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithm = new Algorithm(labyrinth);
                algorithm.FindA(labyrinth);
                if (algorithm.openSetIsEmpty()){
                    JOptionPane.showMessageDialog(drawLabyrinth.getJPanel(), "Путь не найден");
                    drawLabyrinth.getJTable().setEnabled(false);
                }
                next_step.setEnabled(false);
                play_button.setEnabled(false);
                setFinish.setEnabled(false);
                setStart.setEnabled(false);
                repaint();
                pack();
                labyrinth.printLabyrinth();
            }
        });
    }

    private void setSaveButton(JMenuItem save_file) {
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