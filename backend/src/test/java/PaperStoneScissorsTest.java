import org.junit.Assert;
import org.junit.Test;
import pl.marcinycz.paper_stone_scissors.PaperStoneScissors;

public class PaperStoneScissorsTest {
    @Test
    public void checkJudge(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        //Choice: 0 - PAPER, 1 - STONE, 2 - SCISSORS

        Assert.assertEquals(0, paperStoneScissors.judge(0,0));
        Assert.assertEquals(0, paperStoneScissors.judge(1,1));
        Assert.assertEquals(0, paperStoneScissors.judge(2,2));

        Assert.assertEquals(1, paperStoneScissors.judge(0,1));
        Assert.assertEquals(1, paperStoneScissors.judge(1,2));
        Assert.assertEquals(1, paperStoneScissors.judge(2,0));

        Assert.assertEquals(-1, paperStoneScissors.judge(0,2));
        Assert.assertEquals(-1, paperStoneScissors.judge(1,0));
        Assert.assertEquals(-1, paperStoneScissors.judge(2,1));

        Assert.assertEquals(-1, paperStoneScissors.judge(12,123));
        Assert.assertEquals(-1, paperStoneScissors.judge(-455,454));
        Assert.assertEquals(-1, paperStoneScissors.judge(-1111,-789456123));
    }

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
