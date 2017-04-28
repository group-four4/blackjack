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
public class Deck {
    
    private ArrayList<Card> carddeck;
    
    public Deck(){      // constructori
        this.carddeck= new ArrayList<>();
    }
    
    public void createPlayingDeck(int howManyDecks){        // generoi 52 kortin playingdeck:in
        for (int i=0; i<howManyDecks; i++){
        for (Suit cardSuit : Suit.values())
            for (Value cardValue : Value.values())
                this.carddeck.add(new Card(cardSuit, cardValue));
        }
    }
    
    public void shufflePlayingDeck(){
        int randomInt;
        final int    alaR= 0, originalSizeDeck= this.carddeck.size();
        ArrayList<Card> temporaryDeck = new ArrayList<>();

        for (int i=0; i<originalSizeDeck; i++){       // arraylist carddeckin indexit menevät matemaattisella intervallilla [0, (originalsize-1)]

            randomInt = ( (int) (Math.random() * (this.carddeck.size()-1 - alaR + 1) + alaR) );     // täytyy käyttää loopissa sisällä uutta metodikutsua .size() koska pelipakan koko voi muuttua
            temporaryDeck.add(this.getCard(randomInt));
            this.carddeck.remove(randomInt);
        }
        
        this.carddeck= temporaryDeck;
    }
    
    @Override
    public String toString(){
        String cardOutput = "";
        int i= 0;
        for (Card aCard : this.carddeck) {
            cardOutput = cardOutput + "\n" + (i+1) + " " + aCard.toString();
            i=i+1;
        }
        return cardOutput;
    }
    
    public void removeCard(int index){
        this.carddeck.remove(index);
    }
    
    public void addCard(Card addedCard){
        this.carddeck.add(addedCard);
    }
    
    public Card getCard(int index){
        return this.carddeck.get(index);
    }
    
    /*      // joku wanha metodi
    public void drawOneCardFromDeck(Deck comingFrom){
        this.carddeck.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
        }
      */  

    }
  
    

