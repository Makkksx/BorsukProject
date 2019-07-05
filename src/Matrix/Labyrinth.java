package Matrix;

import java.io.*;
import java.util.*;

public class Labyrinth {
    private static char [][] labyrinth;
    private static int size;
    private ArrayList<Integer> pathes_x = new ArrayList<Integer>();
    private ArrayList<Integer> pathes_y = new ArrayList<Integer>();

    public Labyrinth(File file) throws IOException {
        String line;
        Scanner sc = new Scanner(file);
        int i = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (i == 0) {
                labyrinth = new char[line.length()][line.length()];
                size =  line.length();
            }
            System.arraycopy(line.toCharArray(), 0, labyrinth[i], 0, line.length());
            i++;
        }
    }
    public void save(File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        for(int i=0;i<size; i++){
            for(int j=0;j<size;j++){
                fw.write(labyrinth[i][j]);
            }
            fw.write("\n");
        }
        fw.close();

    }
    public boolean floodFill(int x, int y) {
        if(labyrinth[x][y] == 'f') {
            labyrinth[x][y] = 'r';
            return true;
        }
        else {
            labyrinth[x][y] = '2';
            if (checker(x + 1, y)) {
                if (floodFill(x + 1, y)) {
                    pathes_x.add(x + 1);
                    pathes_y.add(y);
                    return true;
                }
            }
            if (checker(x - 1, y)) {
                if (floodFill(x - 1, y)) {
                    pathes_x.add(x - 1);
                    pathes_y.add(y);
                    return true;
                }
            }
            if (checker(x, y + 1)) {
                if (floodFill(x, y + 1)) {
                    pathes_x.add(x);
                    pathes_y.add(y + 1);
                    return true;
                }
            }
            if (checker(x, y - 1)) {
                if (floodFill(x, y - 1)) {
                    pathes_x.add(x);
                    pathes_y.add(y - 1);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checker(int x, int y){
        if(x < 0 || y < 0 || x >= size || y >= size)
            return false;
        return labyrinth[x][y] == '0';
    }

    public void  printLabyrinth(){
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(labyrinth[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        /*for(int i = 0; i < pathes_x.size(); i++) {
            System.out.print(pathes_x.get(i) + "," + pathes_y.get(i) + " <- ");
        }
        System.out.println("START!");*/
    }
}