/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackproject;
import java.util.Scanner;


/**
 *
 * @author Late
 */

//            System.out.println("Welcome to Blackjack table, place your bets now!: ");

public class View {
    

    static Scanner input = new Scanner(System.in);
 
    
    
    
    public View(){      //constructor 
 
    }
    
    public boolean askIfKeepPlaying(){
        System.out.println("This is the Blackjack game, do you want to keep playing y/n ?: ");

        String answer= input.next();
        if (answer.compareTo("y")==0){
            return true;
        }
        else{
            return false;
        }    
    }
    
    public void rudeBehaviourMessage(){
        System.out.println("GET OUTTA HERE you're out money you shmuck!!!");
    }
    
    public double askBetSize(){
        int j=0;

        System.out.println("Allowed betsizes are: 5$, 25$,  100$ : ");
        System.out.println("Players, place your bets now!: ");
        double betAmount = input.nextDouble();
        if (betAmount!=5 && betAmount!=25 && betAmount!= 100){
            System.out.println("GET OUTTA HERE you rude customer!!!");
            betAmount=0;
            return betAmount;
        }
        else
           return betAmount;
    }
    
    public int askHowManyDecksToUse(){
        System.out.println("How many decks do you want to play blackjack with?: ");
        int numberDecks= input.nextInt();
        while (numberDecks<=0){
            System.out.println("WRONG number of decks, give 1 or more (natural numbers): ");
            numberDecks = input.nextInt();
        }
        return numberDecks;
    }
    
    
    
}
