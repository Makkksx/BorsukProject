package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 *  The ButtonColumn class provides a renderer and an editor that looks like a
 *  JButton. The renderer and editor will then be used for a specified column
 *  in the table. The TableModel will contain the String to be displayed on
 *  the button.
 *
 *  The button can be invoked by a mouse click or by pressing the space bar
 *  when the cell has focus. Optionally a mnemonic can be set to invoke the
 *  button. When the button is invoked the provided Action is invoked. The
 *  source of the Action will be the table. The action command will contain
 *  the model row number of the button that was clicked.
 *
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
    private JTable table;
    private boolean FINISH;
    private boolean START;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;
    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private Labyrinth labyrinth;
    public ButtonColumn(JTable table, Labyrinth labyrinth) {
        this.table = table;
        this.labyrinth = labyrinth;
        FINISH = false;
        START = false;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );
        TableColumnModel columnModel = table.getColumnModel();
        for(int i = 0; i < table.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(this);
            columnModel.getColumn(i).setCellEditor(this);
        }
        table.addMouseListener( this );
    }
    public void setFINISH(){
        FINISH = true;
    }
    public void setSTART(){
        START = true;
    }


    /**
     *  Get foreground color of the button when the cell has focus
     *
     *  @return the foreground color
     */
    public Border getFocusBorder()
    {
        return focusBorder;
    }

    /**
     *  The foreground color of the button when the cell has focus
     *
     *  @param focusBorder the foreground color
     */
    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }

    /**
     *  The mnemonic to activate the button when the cell has focus
     *
     *  @param mnemonic the mnemonic
     */
    public void setMnemonic(int mnemonic)
    {
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
    public Object getCellEditorValue()
    {
        return editorValue;
    }

    //
//  Implement TableCellRenderer interface
//
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(labyrinth.getCell(column,row) == '1')
            renderButton.setIcon(new ImageIcon("Pictures\\black_cell.png"));
        else if(labyrinth.getCell(column,row) == '0')
            renderButton.setIcon(new ImageIcon("Pictures\\white_cell.png"));
        else if(labyrinth.getCell(column,row) == 's')
            renderButton.setIcon(new ImageIcon("Pictures\\start_cell.png"));
        else if(labyrinth.getCell(column,row) == 'f')
            renderButton.setIcon(new ImageIcon("Pictures\\finish_cell.png"));
        else
            renderButton.setIcon(new ImageIcon("Pictures\\path_cell.png"));
        //else if(labyrinth.getCell(row,column) == '0')
        //renderButton.setBorderPainted(false);
        //renderButton.setFocusPainted(false);
        //renderButton.setContentAreaFilled(false);

        renderButton.setBorder(new LineBorder(Color.BLACK, 1));
        if(row == 0 && column == (table.getColumnCount() - 1))
            renderButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        else if(row == 0)
            renderButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
        else if(column == (table.getColumnCount() - 1))
            renderButton.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
        else
            renderButton.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));


        /*
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus) {
            renderButton.setBorder( focusBorder );
        }
        else {
            renderButton.setBorder( originalBorder );
        }

//		renderButton.setText( (value == null) ? "" : value.toString() );
        if (value == null) {
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }
        else if (value instanceof Icon) {
            renderButton.setText( "" );
            renderButton.setIcon( (Icon)value );
        }
        else {
            renderButton.setText( value.toString() );
            renderButton.setIcon( null );
        }

         */

        return renderButton;
    }

    //
//  Implement ActionListener interface
//
    /*
     *	The button has been pressed. Stop editing and invoke the custom Action
     */
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        int column = table.convertColumnIndexToModel(table.getEditingColumn());

        if(START){
            labyrinth.newStart(new Point(row,column));
            START = false;
            table.repaint();
        }
        else if(FINISH){
            labyrinth.newFinish(new Point(row,column));
            FINISH = false;
            table.repaint();
        }
        else if(labyrinth.getCell(column,row) == '1')
            labyrinth.setCell(column,row,'0');
        else
            labyrinth.setCell(column,row,'1');
        labyrinth.printLabyrinth();
        /*

        //  Invoke the Action

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                row + " " + column);
        action.actionPerformed(event);

         */
    }

    //
//  Implement MouseListener interface
//
    /*
     *  When the mouse is pressed the editor is invoked. If you then then drag
     *  the mouse to another cell before releasing it, the editor is still
     *  active. Make sure editing is stopped when the mouse is released.
     */
    public void mousePressed(MouseEvent e) {
        if (table.isEditing() &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
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