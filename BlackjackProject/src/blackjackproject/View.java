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
    
    public void printDealerHandValue(Hand dealerHand){
        System.out.println("the dealer has: ");
        System.out.println(dealerHand.toString());
        System.out.println("dealerhand is valued at " +dealerHand.handValueOf());
    }
    public void printPlayerHandValue(Hand someHand){
        System.out.println("your hand is:");
        System.out.println(someHand.toString());
        System.out.println("you hand is valued at " +someHand.handValueOf());
    }
    public void printHandValues( Hand dealerhand, Hand playerhand){
        System.out.println("__________________________________________________________");
//        System.out.println("\n");
        System.out.println("the dealer has: ");
        System.out.println(dealerhand.toString());
        System.out.println("dealerhand is valued at " +dealerhand.handValueOf());
        System.out.println("the player has");
        System.out.println(playerhand.toString());
        System.out.println("playerhand is valued at " + playerhand.handValueOf());
//        System.out.println("\n");
System.out.println("__________________________________________________________");
     
    }
       
    public void announceSideRewards(int resultOfSidegame, int resultOfSideBJ, double sideBet){
        if (resultOfSideBJ==1 || resultOfSidegame==1){
            System.out.println("you get your money back, valued at " +sideBet );
        }
        else if (resultOfSideBJ==2){
            double sideReward=sideBet+(1.5*sideBet);
            System.out.println("you win extra reward valued at " +sideReward);
        }
        else if (resultOfSidegame==2){
            double sidewin=sideBet*2;
            System.out.println("your win is valued at " +sidewin);
        }
        else if (resultOfSidegame==0){
            System.out.println("you lose the money already invested valued at " + sideBet);
        }
    }
     public void announceResultSidegame( int resultOfGame){
         if (resultOfGame==0){
             System.out.println("player loses the hand ");
         }
         else if (resultOfGame==1){
             System.out.println("the hand is a draw ");
         }
         else{
             System.out.println(" player wins the hand");
         }
     } 
     
     public void announceResultMaingame( int resultOfGame){
         if (resultOfGame==0){
             System.out.println("player loses the hand ");
         }
         else if (resultOfGame==1){
             System.out.println("the hand is a draw ");
         }
         else{
             System.out.println(" player wins the hand");
         }
     } 
     
     
     
     
            
    public boolean askIfKeepPlaying(){
        System.out.println("This is the Blackjack game, do you want to keep playing y/n ?: ");

        String answer= input.next();
        if (answer.compareTo("y")==0 || answer.compareTo("yes")==0){
            return true;
        }
        else{
            return false;
        }    
    }
    
    public void rudeBehaviourMessage(){             // player is bankrupt print  message
        System.out.println("GET OUTTA HERE you're out money you shmuck!!!");
    }
    
    public boolean askInsuranceBet(){
        System.out.println("Do you want to place insurancebet? ");
        String answer= input.next();
        if ( answer.compareTo("yes")==0 || answer.compareTo("y")==0 ){
            
            return true;
        }
        return false;
    }
    
    public boolean askSplit(){
        System.out.println("Do you want to split (y/n) or (yes/no)? ");
        String answer= input.next();
        if ( answer.compareTo("yes")==0 || answer.compareTo("y")==0 ){
            return true;
        }
        else{
            return false;
        }
        
    }
    public void printSidegameStartMessage(){
        System.out.println("__________________________________________________________");
        System.out.println("THIS IS THE SIDEGAME");
    }
    public void printMaingameStartMessage(){
        System.out.println("__________________________________________________________");
        System.out.println("THIS IS THE MAINGAME: ");
    }
    public void printSidegameEndsMessage(){
        System.out.println("SIDEGAME ENDS: ");
        System.out.println("__________________________________________________________");
    }
    public void printMaingameEndsMessage(){
        System.out.println("MAINGAME ENDS: ");
        System.out.println("__________________________________________________________");
    }
    
    
    public void printRevealedDealerCardAndPlayerCards(Hand dealerHand, Hand playerHand){
        System.out.println("dealer's starting cards are ");
        String revealedDealerCard= dealerHand.revealOnlyOneCard();
        System.out.println(revealedDealerCard + " - " + "UNKNOWN");
        System.out.println("Player's starting cards are ");
        System.out.println(playerHand.toString());
    }
    
   public int askPlayerDecisionsRegular(){          // regular player choices WHEN NOT splitting
       System.out.println("your choices are as follows: ");
       System.out.println("1) stand  ");
       System.out.println("2) hit ");
       System.out.println("3) doubledown ");
        int answer=input.nextInt();
        return answer;
    
   }
   
   public int askPlayerDecisionsSideGame(){
       
       System.out.println("your sidegame choices are the following: ");
       System.out.println("1.) stand ");
       System.out.println("2.) hit ");
       System.out.println("3.) doubledown");
       int answer = input.nextInt();
       return answer;
   }
   
   public boolean askPlayerKeepHitting(){           // method is often used to ask if player wants extra cards in hitting loop, when the player is under 21 in hand value
       System.out.println("Do you want to keep hitting (y/n) or (yes/no)? ");
       String answer= input.next();
       if ( answer.compareTo("y")==0 || answer.compareTo("yes")==0 )
           return true;
       else
           return false;
   }
   
   
    public double askBetSize(){         // ask starting bet from player
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
    
    public int askHowManyDecksToUse(){          // asks player how many decks he wants to use in the blackjack game to form the so-called blackjack "shoe" (playing deck, consisting of one or more regular 52 card decks mixed and shuffled)
        System.out.println("How many decks do you want to play blackjack with?: ");
        int numberDecks= input.nextInt();
        while (numberDecks<=0){
            System.out.println("WRONG number of decks, give 1 or more (natural numbers): ");
            numberDecks = input.nextInt();
        }
        return numberDecks;
    }
    
    public void printInteger(int luku){
        System.out.println(luku);
    }
    
    public void printDouble(double luku){
        System.out.println(luku);
    }
    
    public void printString(String stringi){
        System.out.println(stringi);
    }
    
    
}
