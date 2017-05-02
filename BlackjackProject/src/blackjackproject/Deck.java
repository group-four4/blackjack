/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackproject;
import java.util.ArrayList;
import static java.util.Collections.swap;
import java.util.Random;

/**
 *
 * @author Late
 */
public class Deck {
    
    private ArrayList<Card> carddeck;
    
    public Deck(){      // constructori
        this.carddeck= new ArrayList<>();
    }
    
   public void createTestingDeck(){     // tämä deckki on VAIN TESTAUSTA VARTEN
       Suit hearts= Suit.HEARTS;
       Card one= new Card(hearts, Value.ACE);       // playercard1
       Card two= new Card(hearts, Value.KING);      // playercard2
       Card three= new Card(hearts, Value.ACE);
       Card four= new Card(hearts, Value.SIX);
       Card five= new Card(hearts, Value.EIGHT);
       Card six= new Card(hearts, Value.FIVE);
       Card seven= new Card(hearts, Value.ACE);
       Card eight= new Card(hearts, Value.SEVEN);
       Card nine= new Card(hearts, Value.SEVEN);
       Card ten= new Card(hearts, Value.SEVEN);
       Card eleven= new Card(hearts, Value.SEVEN);
       Card twelve= new Card(hearts, Value.SEVEN);

/*          // tässä loopissa testattiin että jos dealer sai pelkästään ässiä korteiksi LOPPUTULOS=MEIDÄÄN ALGORITHMIT TOIMIVAT OIKEIN... LOPPU...
       carddeck.add(one);
      carddeck.add(two);
       for (int i=1; i<=21; i++){
           Card kortti= new Card(hearts, Value.ACE);
           carddeck.add(kortti);
       }      */
              carddeck.add(one);
       carddeck.add(two);
       carddeck.add(three);
       carddeck.add(four);
       carddeck.add(five);
       carddeck.add(six);
       carddeck.add(seven);
       carddeck.add(eight);
       carddeck.add(nine);
       carddeck.add(ten);
       carddeck.add(eleven);
       carddeck.add(twelve);

       

       
   }
   
    public void createPlayingDeck(int howManyDecks){        // generoi 52 kortin playingdeck:in
        for (int i=0; i<howManyDecks; i++){
        for (Suit cardSuit : Suit.values())
            for (Value cardValue : Value.values())
                carddeck.add(new Card(cardSuit, cardValue));
        }
    }
    
    public void shufflePlayingDeck(){       // alternative shuffle algorithm
        int randomInt;
        final int    alaR= 0, originalSizeDeck= carddeck.size();
        ArrayList<Card> temporaryDeck = new ArrayList<>();

        for (int i=0; i<originalSizeDeck; i++){       // arraylist carddeckin indexit menevät matemaattisella intervallilla [0, (originalsize-1)]

            randomInt = ( (int) (Math.random() * (carddeck.size()-1 - alaR + 1) + alaR) );     // täytyy käyttää loopissa sisällä uutta metodikutsua .size() koska pelipakan koko voi muuttua
            temporaryDeck.add(this.getCard(randomInt));
            carddeck.remove(randomInt);
        }
        
        carddeck= temporaryDeck;
    }
    
    public void shuffleArrayList(){     // shuffle arraylist algorithm, requires some java imports
        int originalSizeList= carddeck.size();
        Random random = new Random();
        random.nextInt();
        for (int j=0; j<originalSizeList; j++){
            int change = j + random.nextInt(originalSizeList-j);
            swap(carddeck, j, change);
        }
    }
    
    
    @Override
    public String toString(){
        String cardOutput = "";
        int i= 0;
        for (Card aCard : carddeck) {
            cardOutput = cardOutput + "\n" + (i+1) + " " + aCard.toString();
            i=i+1;
        }
        return cardOutput;
    }
    
    public void removeCard(int index){
        carddeck.remove(index);
    }
    
    public void addCard(Card addedCard){
        carddeck.add(addedCard);
    }
    
    public Card getCard(int index){
        return carddeck.get(index);
    }
    
    
    public int checkCardsAmountInDeck(){
        return carddeck.size();
    }
    
    /*      // joku wanha metodi
    public void drawOneCardFromDeck(Deck comingFrom){
        this.carddeck.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
        }
      */  

    }
  
    

