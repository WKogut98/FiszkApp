import static org.junit.Assert.*;

import com.example.fiszkapp.FlashCard;

import org.junit.Test;

public class FlashCardTest {

    private FlashCard card = new FlashCard(1,"f","b",0);
    @Test
    public void rightAnswerFromZero()
    {
        card.rightAnswer();
        assertEquals(2,card.getPriority());
    }

    @Test
    public void rightAnswer()
    {
        card.setPriority(44);
        card.rightAnswer();
        assertEquals(90,card.getPriority());
    }

    @Test
    public void wrongAnswerZero()
    {
        card.wrongAnswer();
        assertEquals(0,card.getPriority());
    }

    @Test
    public void wrongAnswerEven()
    {
        card.setPriority(44);
        card.wrongAnswer();
        assertEquals(22,card.getPriority());
    }

    @Test
    public void wrongAnswerOdd()
    {
        card.setPriority(101);
        card.wrongAnswer();
        assertEquals(50,card.getPriority());
    }
}