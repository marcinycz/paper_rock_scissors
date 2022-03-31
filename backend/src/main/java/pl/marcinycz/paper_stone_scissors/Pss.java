package pl.marcinycz.paper_stone_scissors;

import lombok.*;

import javax.persistence.*;
import java.util.Random;
import java.util.Scanner;

@Entity
@Table(name = "pss")
@Getter
@Setter
@AllArgsConstructor
//@RequiredArgsConstructor
@ToString
@NoArgsConstructor
public class Pss {
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


    public void startPss(int totalScore){
        this.totalScore = totalScore;
        judge(this.myChoiceInt);
        this.verdict = result(this.score);
        this.totalScore += this.score;
    }


    public int getScore() {
        return score;
    }

    public void startInConsole(){
        Scanner scanner = new Scanner(System.in);
        for(;;){
            System.out.println();
            System.out.println("PAPIER = 0, KAMIEN = 1, NOŻYCE = 2, PODDAJĘ SIĘ = 9");
            System.out.print("WYBIERZ: ");

            int choice = scanner.nextInt();
            if(choice == 9){
                System.out.println("Mój wynik: " + (this.totalScore += this.score));
                System.out.println("DZIĘKI ZA GRĘ ;)");
                break;
            }
            judge(choice);
            System.out.println("Twój wybór: " + myChoice);
            System.out.println("Komputer: " + computerChoice);
            this.verdict = result(score);
            System.out.println(verdict);
            System.out.println("Mój wynik: " + (this.totalScore += this.score));
        }
    }

    public void judge (int myChoice){
        Random random = new Random();
        int computerChoice = random.nextInt(3);

        this.myChoice = choiceName(myChoice);
        this.computerChoice = choiceName(computerChoice);


        //Paper - 0, stone - 1, scissors - 2
        if(computerChoice == myChoice){
            this.score = 0;
        }else if((myChoice == 0 && computerChoice == 1) || (myChoice == 1 && computerChoice == 2) || (myChoice == 2 && computerChoice == 0)){
            this.score = 1;
        }else {
            this.score = -1;
        }
    }

    public String choiceName (int choice){
        if(choice == 0){
            return "PAPER";
        }else if(choice == 1){
            return "STONE";
        }else if(choice == 2) {
            return "SCISSORS";
        }else{
            return "CHEATING!!!";
        }
    }

    public String result (int point){
        if(point == 0){
            return "DRAW";
        }else if(point == 1){
            return "VICTORY";
        }else{
            return "DEFEAT";
        }
    }

}
