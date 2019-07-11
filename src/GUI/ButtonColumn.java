package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
    private JTable table;
    private boolean FINISH;
    private boolean START;
    private int mnemonic;
    private Border focusBorder;
    private JButton renderButton;
    private JButton finishButton;
    private JButton startButton;
    private JButton cancel;
    private JButton editButton;
    private boolean canChange;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private Labyrinth labyrinth;

    ButtonColumn(JTable table, Labyrinth labyrinth) {
        this.table = table;
        this.labyrinth = labyrinth;
        FINISH = false;
        canChange = false;
        START = false;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
        Border originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );
        TableColumnModel columnModel = table.getColumnModel();
        for(int i = 0; i < table.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(this);
            columnModel.getColumn(i).setCellEditor(this);
        }
        table.addMouseListener(this);
    }
    boolean  getFinishbool(){return FINISH;}
    boolean  getStarthbool(){return START;}

    void setFINISH(JButton finish,JButton cancel ,boolean val) {
        finishButton = finish;
        this.cancel = cancel;
        FINISH = val;
    }

    void setSTART(JButton start,JButton cancel, boolean val) {
        startButton = start;
        this.cancel = cancel;
        START = val;
    }

    public Border getFocusBorder() {
        return focusBorder;
    }

    private void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder(focusBorder);
    }

    public int getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(int mnemonic) {
        this.mnemonic = mnemonic;
        renderButton.setMnemonic(mnemonic);
        editButton.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            editButton.setText( "" );
            editButton.setIcon( null );
        }
        else if (value instanceof Icon) {
            editButton.setText( "" );
            editButton.setIcon( (Icon)value );
        }
        else {
            editButton.setText( value.toString() );
            editButton.setIcon( null );
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(labyrinth.getCell(row,column) == '1')
            renderButton.setIcon(new ImageIcon("Pictures\\black_cell.png"));
        else if(labyrinth.getCell(row,column) == '0')
            renderButton.setIcon(new ImageIcon("Pictures\\white_cell.png"));
        else if(labyrinth.getCell(row,column) == 's')
            renderButton.setIcon(new ImageIcon("Pictures\\start_cell.png"));
        else if(labyrinth.getCell(row,column) == 'f')
            renderButton.setIcon(new ImageIcon("Pictures\\finish_cell.png"));
        else if (labyrinth.getCell(row,column) == '3')
            renderButton.setIcon(new ImageIcon("Pictures\\queue_cell.png"));
        else if (labyrinth.getCell(row,column) == '4')
            renderButton.setIcon(new ImageIcon("Pictures\\current_step.png"));
        else
            renderButton.setIcon(new ImageIcon("Pictures\\path_cell.png"));

        renderButton.setBorder(new LineBorder(Color.BLACK, 1));
        if(row == 0 && column == (table.getColumnCount() - 1))
            renderButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        else if(row == 0)
            renderButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
        else if(column == (table.getColumnCount() - 1))
            renderButton.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
        else
            renderButton.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
        return renderButton;
    }

    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int column = table.convertColumnIndexToModel(table.getEditingColumn());

        if(START && !((row == labyrinth.getFinish().x) && (column == labyrinth.getFinish().y))){
            labyrinth.newStart(new Point(row,column));
            startButton.setIcon(null);
            startButton.setPreferredSize(new Dimension(75,26));
            startButton.setBorder(new LineBorder(Color.BLACK));
            cancel.setEnabled(false);
            START = false;
            table.repaint();
        }
        else if(FINISH && !((row == labyrinth.getStart().x) && (column == labyrinth.getStart().y))){
            labyrinth.newFinish(new Point(row,column));
            finishButton.setIcon(null);
            finishButton.setPreferredSize(new Dimension(75,26));
            finishButton.setBorder(new LineBorder(Color.BLACK));
            cancel.setEnabled(false);
            FINISH = false;
            table.repaint();
        }
        else if(labyrinth.getCell(row,column) == '1')
            labyrinth.setCell(row,column,'0');
        else if(labyrinth.getCell(row,column) == '0' || canChange) {
            canChange = false;
            labyrinth.setCell(row, column, '1');
        }
        labyrinth.printLabyrinth();
    }

    public void mousePressed(MouseEvent e) {
        if (table.isEditing() &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
        if(e.getButton()==MouseEvent.BUTTON3){
            canChange = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnEditor
                &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}