package GUI;

import Matrix.Labyrinth;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.table.TableColumn;

// Drawing the window with labyrinth by matrix;

public class DrawLabyrinth{
    public static int SIZE;
    public static final int WIDTH = 50;
    public static final int HIGH = 50;
    public Labyrinth labyrinth;
    private JPanel board;
    private JTable table;
    private int TableScale;

    public DrawLabyrinth(Labyrinth labyrinth) {
        TableScale = 40;
        SIZE = labyrinth.getSize();
        this.labyrinth = labyrinth;
        board = new JPanel();
        //frame.setBounds(800, 200, WIDTH* (SIZE+1), HIGH* (SIZE+1));
        //frame.setVisible(true);
        table = new JTable(SIZE,SIZE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(TableScale);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(TableScale);
        }
        ButtonColumn button = new ButtonColumn(table,this.labyrinth);
        board.add(table);
        board.setVisible(true);
        JButton setFinish = new JButton("Финиш");
        JButton setStart = new JButton("Старт");
        setFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setFINISH();
            }
        });
        setStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setSTART();
            }
        });
        board.add(setFinish,BorderLayout.EAST);
        board.add(setStart,BorderLayout.EAST);
    }
    public JPanel getJPanel(){
        return board;
    }
    public JTable getJTable(){
        return table;
    }
    public void ScaleIncrease(){
        TableScale +=10;
        table.setRowHeight(TableScale);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(TableScale);
        }
    }
    public void ScaleDecrease(){
        if((TableScale - 10) > 0) {
            TableScale -= 10;
            table.setRowHeight(TableScale);
            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(TableScale);
            }
        }
    }
    /*
     class myComponent extends JComponent{
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.WHITE);
            g.fillRect(0,0, WIDTH* SIZE, HIGH * SIZE);
            paintLab(g2);
            paintField(g2);
        }
    }
    public void paintLab(Graphics2D g){

        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                switch (labyrinth.getCell(i,j)){
                    case '1':
                        g.setColor(Color.GRAY);
                        g.fillRect(i*WIDTH,j*HIGH,WIDTH,HIGH);
                        break;
                    case 's':
                        g.setColor(Color.YELLOW);
                        g.fillRect(i*WIDTH,j*HIGH,WIDTH,HIGH);
                        break;
                    case 'f':
                        g.setColor(Color.RED);
                        g.fillRect(i*WIDTH,j*HIGH,WIDTH,HIGH);
                        break;
                    default:

                }
            }
        }

    }
    public void paintField(Graphics2D g) {

        g.setColor(Color.BLACK);
        for(int xx = WIDTH; xx <= WIDTH* SIZE; xx+=WIDTH){
            g.draw(new Line2D.Double(xx, 0, xx, HIGH * SIZE));
        }
        for(int yy = HIGH; yy <= HIGH * SIZE; yy+=HIGH){
            g.draw(new Line2D.Double(0, yy,WIDTH* SIZE, yy ));
        }
    }


     */
    /*public DrawLabyrinth() {
        JFrame frame = new JFrame("Generated Labyrinth");
        frame.setBounds(630, 150, 650, 650);
        JPanel container = new JPanel();
        frame.setLayout(null);
        frame.getContentPane().add(container);
        frame.setVisible(true);
        // pathes;
        JRadioButton min_path = new JRadioButton("Min Path");
        min_path.setBounds(50, 10, 200, 20);
        JRadioButton max_path = new JRadioButton("Max Path");
        max_path.setBounds(400, 10, 200, 20);
        ButtonGroup patternGroup = new ButtonGroup();
        patternGroup.add(min_path);
        patternGroup.add(max_path);
        frame.add(min_path);
        min_path.setSelected(true); // Selected in a default mode;
        frame.add(max_path);
    }*/
}
