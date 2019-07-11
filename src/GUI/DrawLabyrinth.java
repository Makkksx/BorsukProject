package GUI;

import Matrix.Labyrinth;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

// Drawing the window with labyrinth by matrix;

class DrawLabyrinth {
    private JPanel board;
    private JTable table;
    private int TableScale;
    private ButtonColumn button;

    DrawLabyrinth(Labyrinth labyrinth) {
        if (labyrinth.getSize() < 18)
            TableScale = 40;
        else if (labyrinth.getSize() < 26)
            TableScale = 27;
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

    ButtonColumn getButtonColumn() {
        return button;
    }

    void ScaleIncrease() {
        if (button.getLabyrinth().getSize() < 11 && ((TableScale + 10) < 80))
            TableScale += 10;
        else if (button.getLabyrinth().getSize() < 20 && ((TableScale + 10) < 50))
            TableScale += 10;
        else if (button.getLabyrinth().getSize() < 26 && ((TableScale + 10) < 37))
            TableScale += 10;
        table.setRowHeight(TableScale);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(TableScale);
        }
    }


    void ScaleDecrease() {
        if ((TableScale - 10) > 10) {
            TableScale -= 10;
            table.setRowHeight(TableScale);
            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(TableScale);
            }
        }
    }
}