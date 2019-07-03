package Matrix;

import java.io.*;
import java.util.Scanner;

public class Labyrinth {
    private static char [][] labyrinth;
    private static int size;

    public Labyrinth(File file) throws IOException {
        char []buf = new char[256];
        FileReader reader = new FileReader(file);
        Scanner scan = new Scanner(file);
        reader.read(buf);
        size = buf.length;
        labyrinth = new char[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
                labyrinth[i][j] = scan.next().charAt(0);
                System.out.println(labyrinth[i][j]);
            }
            System.out.println(" ");
        }
    }

    public void floodFill(int x, int y) {
        if(checker(x, y)) labyrinth[x][y] = '2';
        if(checker(x + 1, y)) floodFill(x + 1, y);
        if(checker(x - 1, y)) floodFill(x - 1, y);
        if(checker(x, y + 1)) floodFill(x, y + 1);
        if(checker(x, y - 1)) floodFill(x, y - 1);
    }

    private boolean checker(int x, int y){
        if(x < 0 || y < 0 || x >= size || y >= size) return false;
        if(labyrinth[x][y] == '0' || labyrinth[x][y] == 's') return true;
        else return false;
    }
}
