package Matrix;

import GUI.DrawLabyrinth;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Labyrinth {
    private static char [][] labyrinth;
    private static int size;
    private Point start;
    private Point finish;


    private ArrayList<Integer> pathes_x = new ArrayList<Integer>();
    private ArrayList<Integer> pathes_y = new ArrayList<Integer>();
	
    public int getSize() {
        return size;
    }

    public Point getStart() {
        return start;
    }
	
    public char getCell(int i, int j) {
        return labyrinth[i][j];
    }

    void setCell(Point ij, char val) { labyrinth[ij.x][ij.y] = val; }

    public void setCell(int i, int j, char val) {
        labyrinth[i][j] = val;
    }

    public Point getFinish() {
        return finish;
    }

    //Пустой лабиринт
    public Labyrinth() {
        size = 8;
        labyrinth = new char[size][size];
        for (char[] row : labyrinth)
            Arrays.fill(row, '0');
        labyrinth[size/2][size/2] = 's';
        labyrinth[size-1][size-1] = 'f';
        start = new Point(size /2, size /2);
        finish = new Point(size -1, size -1);
    }

    public Labyrinth(File file) throws IOException {
        String line;
        start = new Point();
        finish = new Point();
        Scanner sc = new Scanner(file);
        int i = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (i == 0) {
                labyrinth = new char[line.length()][line.length()];
                size =  line.length();
            }
            if (line.indexOf('s') != -1){
                start.x = i;
                start.y = line.indexOf('s');
            }
            if (line.indexOf('f') != -1){
                finish.x = i;
                finish.y = line.indexOf('f');
            }
            System.arraycopy(line.toCharArray(), 0, labyrinth[i], 0, line.length());
            i++;
        }
    }
	
    public Labyrinth(int n) {
        // Инициализация;
        labyrinth = new char[n][n];
        size = n;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == (size - 1) || j == (size - 1))
                    labyrinth[i][j] = '0';
                else labyrinth[i][j] = '1';
            }
        }

        labyrinth[n/2][n/2] = '0';
        digger(n/2, n/2);
        boolean flag = true;
        while (flag) {
            int curr_x = 2, curr_y = 2;
            boolean f = true;
            for (int i = 2; i < (size - 2); i += 2) {
                for (int j = 2; j < (size - 2); j += 2) {
                    if (labyrinth[i][j] == '1') {
                        curr_x = i;
                        curr_y = j;
                        f = false;
                        break;
                    }
                    if (i == (size - 3) && j == (size - 3)) flag = false;
                }
                if (!f) break;
            }
            if (flag) {
                labyrinth[curr_x][curr_y] = '0';
                digger(curr_x, curr_y);
            }
        }

        int count = size;
        while (count > 0) {
            int curr_x = (int)(Math.random() * (size - 2) + 1);
            int curr_y = (int)(Math.random() * (size - 2) + 1);
            while ((curr_x % 2 == 0 || curr_y % 2 == 0) || labyrinth[curr_x][curr_y] == '0' ||
                    (!(labyrinth[curr_x][curr_y + 1] == '0' && labyrinth[curr_x][curr_y - 1] == '0'
                            && labyrinth[curr_x + 1][curr_y] == '1' && labyrinth[curr_x - 1][curr_y] == '1') &&
                            !(labyrinth[curr_x + 1][curr_y] == '0' && labyrinth[curr_x - 1][curr_y] == '0'
                                    && labyrinth[curr_x][curr_y + 1] == '1' && labyrinth[curr_x][curr_y - 1] == '1'))) {
                curr_x = (int)(Math.random() * (size - 1) + 1);
                curr_y = (int)(Math.random() * (size - 1) + 1);
            }
            labyrinth[curr_x][curr_y] = '0';
            count--;
        }

        labyrinth[n/2][n/2] = 's';
        labyrinth[size - 1][size - 1] = 'f';
        start = new Point(n/2,n/2);
        finish = new Point(size - 1, size - 1);


    }

    private void digger(int curr_x, int curr_y) {
        boolean up = true, down = true, left = true, right = true;
        while (up || down || left || right) {
            int direction = (int)(Math.random() * 4);
            if (direction == 0) {
                if (up && curr_x >= 0 && curr_y + 2 >= 0 && curr_x < size && curr_y + 2 < size && labyrinth[curr_x][curr_y + 2] != '0') {
                    labyrinth[curr_x][curr_y + 1] = '0';
                    labyrinth[curr_x][curr_y + 2] = '0';
                    digger(curr_x, curr_y + 2);
                    return;
                } else up = false;
            }
            if (direction == 1) {
                if (down && curr_x >= 0 && curr_y - 2 >= 0 && curr_x < size && curr_y - 2 < size && labyrinth[curr_x][curr_y - 2] != '0') {
                    labyrinth[curr_x][curr_y - 1] = '0';
                    labyrinth[curr_x][curr_y - 2] = '0';
                    digger(curr_x, curr_y - 2);
                    return;
                } else down = false;
            }
            if (direction == 2) {
                if (right && curr_x + 2 >= 0 && curr_y >= 0 && curr_x + 2 < size && curr_y < size && labyrinth[curr_x + 2][curr_y] != '0') {
                    labyrinth[curr_x + 1][curr_y] = '0';
                    labyrinth[curr_x + 2][curr_y] = '0';
                    digger(curr_x + 2, curr_y);
                    return;
                } else right = false;
            }
            if (direction == 3) {
                if (left && curr_x - 2 >= 0 && curr_y >= 0 && curr_x - 2 < size && curr_y < size && labyrinth[curr_x - 2][curr_y] != '0') {
                    labyrinth[curr_x - 1][curr_y] = '0';
                    labyrinth[curr_x - 2][curr_y] = '0';
                    digger(curr_x - 2, curr_y);
                    return;
                } else left = false;
            }
        }
    }
    public void newFinish(Point point){
        labyrinth[finish.x][finish.y] = '0';
        finish = point;
        labyrinth[finish.x][finish.y] = 'f';
    }
    public void newStart(Point point){
        labyrinth[start.x][start.y] = '0';
        start = point;
        labyrinth[start.x][start.y] = 's';
    }
	
    public void save(File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                fw.write(labyrinth[i][j]);
            }
            fw.write("\n");
        }
        fw.close();
    }
	
    public void clearLab() {
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (labyrinth[i][j] == '2' || labyrinth[i][j] == '3' || labyrinth[i][j] == '4')
                    labyrinth[i][j] = '0';
            }
        }
	}

    public boolean checker(int x, int y) {
        if(x < 0 || y < 0 || x >= size || y >= size)
            return false;
        return labyrinth[x][y] == '0' || labyrinth[x][y] == 'f';
    }

    public void  printLabyrinth() {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(labyrinth[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

}