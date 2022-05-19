import org.junit.Assert;
import org.junit.Test;
import pl.marcinycz.paper_rock_scissors.PaperRockScissors;

import static pl.marcinycz.paper_rock_scissors.ChoicePRS.*;

public class PaperRockScissorsTest {
    @Test
    public void checkEnemyChoice(){
        PaperRockScissors paperRockScissors = new PaperRockScissors();

        for(int i= 0; i < 1000; i++){
            Assert.assertTrue((paperRockScissors.enemyChoice() > -1 && paperRockScissors.enemyChoice() < 3));
            Assert.assertFalse(paperRockScissors.enemyChoice() > 2);
            Assert.assertFalse(paperRockScissors.enemyChoice() < 0);
        }
    }

    @Test
    public void checkJudge(){
        PaperRockScissors paperRockScissors = new PaperRockScissors();

        Assert.assertEquals(0, paperRockScissors.judge(PAPER.value, PAPER.value));
        Assert.assertEquals(0, paperRockScissors.judge(ROCK.value, ROCK.value));
        Assert.assertEquals(0, paperRockScissors.judge(SCISSORS.value,SCISSORS.value));

        Assert.assertEquals(1, paperRockScissors.judge(PAPER.value, ROCK.value));
        Assert.assertEquals(1, paperRockScissors.judge(ROCK.value,SCISSORS.value));
        Assert.assertEquals(1, paperRockScissors.judge(SCISSORS.value,PAPER.value));

        Assert.assertEquals(-1, paperRockScissors.judge(PAPER.value,SCISSORS.value));
        Assert.assertEquals(-1, paperRockScissors.judge(ROCK.value,PAPER.value));
        Assert.assertEquals(-1, paperRockScissors.judge(SCISSORS.value, ROCK.value));

        Assert.assertEquals(-1, paperRockScissors.judge(12,PAPER.value));
        Assert.assertEquals(-1, paperRockScissors.judge(SCISSORS.value,-454));
        Assert.assertEquals(-1, paperRockScissors.judge(-1111,-789456123));
    }

    @Test
    public void shouldChangeIntToName(){
        PaperRockScissors paperRockScissors = new PaperRockScissors();

        Assert.assertEquals("PAPER", paperRockScissors.choiceName(0));
        Assert.assertEquals("ROCK", paperRockScissors.choiceName(1));
        Assert.assertEquals("SCISSORS", paperRockScissors.choiceName(2));
        Assert.assertEquals("WRONG CHOICE", paperRockScissors.choiceName(3));
        Assert.assertEquals("WRONG CHOICE", paperRockScissors.choiceName(-1));
        Assert.assertEquals("WRONG CHOICE", paperRockScissors.choiceName(-7474739));
        Assert.assertEquals("WRONG CHOICE", paperRockScissors.choiceName(8943734));
    }

    @Test
    public void shouldChangeIntToVerdict(){
        PaperRockScissors paperRockScissors = new PaperRockScissors();

        Assert.assertEquals("DRAW", paperRockScissors.nameTheResult(0));
        Assert.assertEquals("VICTORY", paperRockScissors.nameTheResult(1));
        Assert.assertEquals("DEFEAT", paperRockScissors.nameTheResult(2));
        Assert.assertEquals("DEFEAT", paperRockScissors.nameTheResult(-1));
        Assert.assertEquals("DEFEAT", paperRockScissors.nameTheResult(256));
        Assert.assertEquals("DEFEAT", paperRockScissors.nameTheResult(-1111));
    }
}
