package Matrix;

import java.io.*;
import java.util.Scanner;

public class Labyrinth {
    private static char [][] labyrinth;
    private static int size;

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

    public void floodFill(int x, int y) {
        if(checker(x, y)) {
            if(labyrinth[x][y] == 'f')
                labyrinth[x][y] = 'r';
            else
                labyrinth[x][y] = '1';
        }
        if(checker(x + 1, y))
            floodFill(x + 1, y);
        if(checker(x - 1, y))
            floodFill(x - 1, y);
        if(checker(x, y + 1))
            floodFill(x, y + 1);
        if(checker(x, y - 1))
            floodFill(x, y - 1);
    }
    public void  printLabyrinth(){
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(labyrinth[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    private boolean checker(int x, int y){
        if(x < 0 || y < 0 || x >= size || y >= size)
            return false;
        if(labyrinth[x][y] != '1')
            return true;
        else return false;
    }
}
