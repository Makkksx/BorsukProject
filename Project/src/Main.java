import Matrix.Labyrinth;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args)  {
        try {
            Labyrinth labyrinth = new Labyrinth(new File("./src/lab"));
            labyrinth.printLabyrinth();
            labyrinth.floodFill(1, 1);
            labyrinth.printLabyrinth();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e){

        }
    }
}
