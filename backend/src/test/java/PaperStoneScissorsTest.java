import org.junit.Assert;
import org.junit.Test;
import pl.marcinycz.paper_stone_scissors.PaperStoneScissors;

public class PaperStoneScissorsTest {
    @Test
    public void shouldChangeIntToName(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        Assert.assertEquals("PAPER", paperStoneScissors.choiceName(0));
        Assert.assertEquals("STONE", paperStoneScissors.choiceName(1));
        Assert.assertEquals("SCISSORS", paperStoneScissors.choiceName(2));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(3));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(-1));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(-7474739));
        Assert.assertEquals("WRONG CHOICE", paperStoneScissors.choiceName(8943734));
    }

    @Test
    public void shouldChangeIntToVerdict(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        Assert.assertEquals("DRAW", paperStoneScissors.nameTheResult(0));
        Assert.assertEquals("VICTORY", paperStoneScissors.nameTheResult(1));
        Assert.assertEquals("DEFEAT", paperStoneScissors.nameTheResult(2));
        Assert.assertEquals("DEFEAT", paperStoneScissors.nameTheResult(-1));
        Assert.assertEquals("DEFEAT", paperStoneScissors.nameTheResult(256));
        Assert.assertEquals("DEFEAT", paperStoneScissors.nameTheResult(-1111));
    }


}
