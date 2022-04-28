import org.junit.Assert;
import org.junit.Test;
import pl.marcinycz.paper_stone_scissors.PaperStoneScissors;

public class PaperStoneScissorsTest {

    @Test
    public void shouldChangeIntToString(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        Assert.assertEquals("PAPER", paperStoneScissors.choiceName(0));
        Assert.assertEquals("STONE", paperStoneScissors.choiceName(1));
        Assert.assertEquals("SCISSORS", paperStoneScissors.choiceName(2));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(3));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(-1));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(-7474739));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(8943734));

    }
}
