package GUI;

import Matrix.Labyrinth;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

// Drawing the window with labyrinth by matrix;

public class DrawLabyrinth {
    public static int SIZE;
    public static final int WIDTH = 50;
    public static final int HIGH = 50;
    public Labyrinth labyrinth;

    public DrawLabyrinth(Labyrinth labyrinth) {
        SIZE = labyrinth.getSize();
        this.labyrinth = labyrinth;
        JFrame frame = new JFrame("Generated Labyrinth");
        frame.setBounds(800, 200, WIDTH* (SIZE+1), HIGH* (SIZE+1));
        frame.add(new myComponent());
        frame.setVisible(true);
    }

    class myComponent extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.WHITE);
            g.fillRect(0,0, WIDTH* SIZE, HIGH * SIZE);
            paintLab(g2);
            paintField(g2);
        }
    }

    public void paintLab(Graphics2D g) {
        for (int i=0;i<SIZE;i++) {
            for (int j=0;j<SIZE;j++) {
                switch (labyrinth.getCell(i,j)) {
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
        for (int xx = WIDTH; xx <= WIDTH* SIZE; xx+=WIDTH) {
            g.draw(new Line2D.Double(xx, 0, xx, HIGH * SIZE));
        }
        for (int yy = HIGH; yy <= HIGH * SIZE; yy+=HIGH) {
            g.draw(new Line2D.Double(0, yy,WIDTH* SIZE, yy ));
        }
    }
}