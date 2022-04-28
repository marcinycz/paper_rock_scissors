package pl.marcinycz.paper_stone_scissors;

import lombok.*;

import javax.persistence.*;
import java.util.Random;

import static pl.marcinycz.paper_stone_scissors.ChoicePSS.*;

@Entity
@Table(name = "pss")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PaperStoneScissors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private int myChoiceInt;

    private String computerChoice;
    private String myChoice;
    private int score;
    private String verdict;
    private int totalScore;

    public void startPaperStoneScissorsGame(int totalScore){
        this.totalScore = totalScore;
        int enemyChoice = enemyChoice();
        this.computerChoice = choiceName(enemyChoice);
        this.myChoice = choiceName(this.myChoiceInt);
        this.score = judge(this.myChoiceInt, enemyChoice);
        this.verdict = nameTheResult(this.score);
        this.totalScore += this.score;
    }

    public int judge (int myChoice, int computerChoice){
        if(computerChoice == myChoice){
            return 0;
        }else if((myChoice == PAPER.value && computerChoice == STONE.value) || (myChoice == STONE.value && computerChoice == SCISSORS.value) || (myChoice == SCISSORS.value && computerChoice == PAPER.value)){
            return 1;
        }else {
            return -1;
        }
    }

    public int enemyChoice(){
        Random random = new Random();
        return random.nextInt(3);
    }

    public String choiceName (int choice){
        if(choice == 0){
            return "PAPER";
        }else if(choice == 1){
            return "STONE";
        }else if(choice == 2) {
            return "SCISSORS";
        }else{
            return "WRONG CHOICE";
        }
    }

    public String nameTheResult(int point){
        if(point == 0){
            return "DRAW";
        }else if(point == 1){
            return "VICTORY";
        }else{
            return "DEFEAT";
        }
    }
}
