package Test;

import Matrix.Labyrinth;

import org.junit.*;

import static junit.framework.TestCase.*;

class LabyrinthTest {
    @Test
    public void testGetStart() {
        Labyrinth labyrinth = new Labyrinth(13);
        assertNotNull(labyrinth.getStart());
    }

    @Test
    public void testGetFinish() {
        Labyrinth labyrinth = new Labyrinth(13);
        assertNotNull(labyrinth.getFinish());
    }

    @Test
    public void testGetSize() {
        Labyrinth labyrinth = new Labyrinth(13);
        assertEquals(13, labyrinth.getSize());
    }
    /*
    @Test
    public void testChecker() {
        Labyrinth labyrinth = new Labyrinth();
        assertFalse(labyrinth.checker(13, 13));
        assertFalse(labyrinth.checker(-1, 5));
        assertFalse(labyrinth.checker(0, 15));
        assertTrue(labyrinth.checker(5, 5));
        assertTrue(labyrinth.checker(0, 0));
    }

     */
}