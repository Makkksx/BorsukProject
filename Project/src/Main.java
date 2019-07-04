import GUI.GUI;
import Matrix.Labyrinth;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args)  {
        System.out.println(new File(".").getAbsolutePath());
        try {
            Labyrinth labyrinth = new Labyrinth(new File("./Test/lab"));
            labyrinth.printLabyrinth();
            labyrinth.floodFill(1, 1);
            labyrinth.printLabyrinth();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e){

        }
         GUI g = new GUI();
        g.setVisible(true);
    }
}
