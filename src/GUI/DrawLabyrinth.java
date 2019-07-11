package GUI;

import Matrix.Labyrinth;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Drawing the window with labyrinth by matrix;

public class DrawLabyrinth {
    public static final int WIDTH = 50;
    public static final int HIGH = 50;
    private JPanel board;
    private JTable table;
    private int TableScale;
    private ButtonColumn button;

    DrawLabyrinth(Labyrinth labyrinth) {
        TableScale = 40;
        int SIZE = labyrinth.getSize();
        board = new JPanel();
        table = new JTable(SIZE, SIZE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(TableScale);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(TableScale);
        }
        button = new ButtonColumn(table, labyrinth);
        board.add(table);
        board.setVisible(true);
    }

    JPanel getJPanel() {
        return board;
    }

    JTable getJTable() {
        return table;
    }
    ButtonColumn getButtonColumn(){return button;}

    void ScaleIncrease() {
        TableScale += 10;
        table.setRowHeight(TableScale);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(TableScale);
        }
    }

    void ScaleDecrease() {
        if ((TableScale - 10) > 0) {
            TableScale -= 10;
            table.setRowHeight(TableScale);
            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(TableScale);
            }
        }
    }
}