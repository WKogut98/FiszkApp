import static org.junit.Assert.*;

import org.junit.Test;

public class ExperienceTest {

    @Test
    public void calculateNeededExp() {
        int level = 4;
        assertEquals(1750,Experience.calculateNeededExp(level));
    }

    @Test
    public void calculateExpFromCurrentLevel() {
        Experience.calculateExpFromCurrentLevel()
    }

    @Test
    public void totalExpToGetLevel() {
    }

    @Test
    public void expToNextLevel() {
    }
}