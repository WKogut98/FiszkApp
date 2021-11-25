import static org.junit.Assert.*;

import com.example.fiszkapp.Experience;

import org.junit.Test;

public class ExperienceTest {

    @Test
    public void calculateNeededExp() {
        int level = 4;
        assertEquals(1750, Experience.calculateNeededExp(level));
    }

    @Test
    public void calculateExpFromCurrentLevel() {
        int level = 4;
        int exp = 4000;
        assertEquals(250,Experience.calculateExpFromCurrentLevel(exp,level));
    }

    @Test
    public void totalExpToGetLevel() {
        int level = 4;
        assertEquals(3750,Experience.totalExpToGetLevel(level));
    }

    @Test
    public void expToNextLevel() {
        int exp = 5000;
        int level = 4;
        assertEquals(500,Experience.expToNextLevel(exp,level));
    }
}