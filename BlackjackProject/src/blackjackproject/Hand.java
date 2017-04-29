/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackproject;
import java.util.ArrayList;

/**
 *
 * @author Late
 */
public class Hand {
    
    private Deck carddeck;
    private ArrayList<Card> playerhand;

    public Hand() {
        this.playerhand = new ArrayList<>();
    }

    public Hand(Deck carddeck) {
        this.carddeck = carddeck;
        this.playerhand = new ArrayList<>();
    }

    public void hitPlayerCard() {
        playerhand.add(carddeck.getCard(0));
        carddeck.removeCard(0);

    }
    
    public void dealStartingCards(){
        for (int j=0; j<=1; j++){
            playerhand.add(carddeck.getCard(0));
            carddeck.removeCard(0);
        }
    }

    @Override
    public String toString() {
        String handOutput = "";
        int j = 0;
        for (Card aCard : playerhand) {
            handOutput = handOutput + "\n" + (j + 1) + " " + aCard.toString();
            j = j + 1;
        }
        return handOutput;
    }
    
    public int handValueOf(){
        int handtotalValue=0, acesAmount=0;
        
        for (Card theCard : playerhand){
            switch (theCard.getValue()){
                case TWO: handtotalValue += 2; break;
                case THREE: handtotalValue += 3; break;
                case FOUR: handtotalValue += 4; break;
                case FIVE   : handtotalValue += 5; break;
                case SIX: handtotalValue += 6; break;
                case SEVEN: handtotalValue += 7; break;
                case EIGHT: handtotalValue += 8; break;
                case NINE: handtotalValue += 9; break;
                case TEN: handtotalValue += 10; break;
                case JACK: handtotalValue += 10; break;
                case QUEEN: handtotalValue += 10; break;
                case KING: handtotalValue += 10; break;
                case ACE: acesAmount +=1; break;
            }
        }
        for (int j=0; j< acesAmount; j++){
            if (handtotalValue > 10)
                handtotalValue += 1;
            else {
                handtotalValue += 11;
            }
            
        }
        return handtotalValue;
    }
      
    
    
    
    
    
          /*
    public void handinPrinttaus(){      // wanhaa koodia 
        
        this.carddeck.toString();
    }
    */
    
    /*
      public void hitPlayerCard(Deck whereFrom){    // wanhaa koodia
          this.playerhand.add(whereFrom.getCard(0));
          whereFrom.removeCard(0);
          
      }
      */
      
      
     }
    
    

