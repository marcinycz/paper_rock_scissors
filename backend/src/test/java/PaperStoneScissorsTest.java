import org.junit.Assert;
import org.junit.Test;
import pl.marcinycz.paper_stone_scissors.PaperStoneScissors;

import static pl.marcinycz.paper_stone_scissors.ChoicePSS.*;

public class PaperStoneScissorsTest {
    @Test
    public void checkEnemyChoice(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        for(int i= 0; i < 1000; i++){
            Assert.assertTrue((paperStoneScissors.enemyChoice() > -1 && paperStoneScissors.enemyChoice() < 3));
            Assert.assertFalse(paperStoneScissors.enemyChoice() > 2);
            Assert.assertFalse(paperStoneScissors.enemyChoice() < 0);
        }
    }

    @Test
    public void checkJudge(){
        PaperStoneScissors paperStoneScissors = new PaperStoneScissors();

        Assert.assertEquals(0, paperStoneScissors.judge(PAPER.value, PAPER.value));
        Assert.assertEquals(0, paperStoneScissors.judge(STONE.value,STONE.value));
        Assert.assertEquals(0, paperStoneScissors.judge(SCISSORS.value,SCISSORS.value));

        Assert.assertEquals(1, paperStoneScissors.judge(PAPER.value,STONE.value));
        Assert.assertEquals(1, paperStoneScissors.judge(STONE.value,SCISSORS.value));
        Assert.assertEquals(1, paperStoneScissors.judge(SCISSORS.value,PAPER.value));

        Assert.assertEquals(-1, paperStoneScissors.judge(PAPER.value,SCISSORS.value));
        Assert.assertEquals(-1, paperStoneScissors.judge(STONE.value,PAPER.value));
        Assert.assertEquals(-1, paperStoneScissors.judge(SCISSORS.value,STONE.value));

        Assert.assertEquals(-1, paperStoneScissors.judge(12,PAPER.value));
        Assert.assertEquals(-1, paperStoneScissors.judge(SCISSORS.value,-454));
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
