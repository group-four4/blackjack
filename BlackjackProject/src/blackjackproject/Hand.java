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
        this.playerhand.add(this.carddeck.getCard(0));
        this.carddeck.removeCard(0);

    }

    @Override
    public String toString() {
        String handOutput = "";
        int j = 0;
        for (Card aCard : this.playerhand) {
            handOutput = handOutput + "\n" + (j + 1) + " " + aCard.toString();
            j = j + 1;
        }
        return handOutput;
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
    
    

