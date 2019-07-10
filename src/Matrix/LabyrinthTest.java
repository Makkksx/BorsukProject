package Matrix;

import org.junit.Assert;
import org.junit.Test;

public class LabyrinthTest {
    @Test
    public void testGetStart() {
        Labyrinth labyrinth = new Labyrinth(13,1);
        Assert.assertNotNull(labyrinth.getStart());
    }

    @Test
    public void testGetFinish() {
        Labyrinth labyrinth = new Labyrinth(13,1);
        Assert.assertNotNull(labyrinth.getFinish());
    }

    @Test
    public void testGetSize() {
        Labyrinth labyrinth = new Labyrinth(13,1);
        Assert.assertEquals(13, labyrinth.getSize());
    }
    @Test
    public void testChecker() {
        Labyrinth labyrinth = new Labyrinth(8);
        Assert.assertFalse(labyrinth.checker(13, 13));
        Assert.assertFalse(labyrinth.checker(-1, 5));
        Assert.assertFalse(labyrinth.checker(0, 15));
        Assert.assertTrue(labyrinth.checker(5, 5));
        Assert.assertTrue(labyrinth.checker(0, 0));
    }
}