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
    private ArrayList<Card> playerhand;         // instanttimuuttuja playerhand on nimetty hieman sekavasti playerhand:iksi
                                                // playerhand voi olla mikä tahansa hand joko dealerin käsi tai playerin käsi
    public Hand() {                             // nämä Hand luokan metodit tehtiin ihan alkuvaiheessa kun suunnittelimme aluksi tekevämme pääohjelman ilman controller-oliota.
        this.playerhand = new ArrayList<>();    // Hand luokan constructori, ON TÄRKEÄÄ HUOMATA Hand-oliot ovat ArrayListejä jotka sisältävät Card-olioita
    }

    public Hand(Deck carddeck) {        // hand tuntee deckin, elikkä pelipakan josta kortit jaetaan dealeriller ja pelaajalle.
        this.carddeck = carddeck;
        this.playerhand = new ArrayList<>();
    }


    
    public void hitPlayerCard() {
        playerhand.add(carddeck.getCard(0));
        carddeck.removeCard(0);

    }

    public void clearHand(){
        playerhand.clear();
    }
    public void dealStartingCards(){
        for (int j=0; j<=1; j++){
            playerhand.add(carddeck.getCard(0));
            carddeck.removeCard(0);
        }
    }
    
    public String revealOnlyOneCard(){      // tämä metodi liittyy ihan alkutilanteeseen dealerin kortteihin, kun on jaettu 2 ja 2 korttia dealerille ja pelaajalle
        String revealedCard;                // dealer paljastaa vain yhden omista korteistaan pelaajalle tässä vaiheessa. HUOM kaikki alkukortit on kuitenkin jaettu jo, mutta 
        revealedCard= " - " + playerhand.get(0).toString();     //  sitä toista dealerin korttia ei vain printata näkyviin vielä
        return revealedCard;
    }
    
    public boolean doesPlayerHaveSameStartingCards(){
        if (  playerhand.get(0).getValue() == playerhand.get(1).getValue()   ){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isOpenCardAce(){
        Value maa= playerhand.get(0).getValue();
        if (maa==Value.ACE)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        String handOutput = "";
        for (Card aCard : playerhand) {
            handOutput = handOutput + " - "+ aCard.toString();
        }
        return handOutput;
    }
    
    
//    public int TESTIHandValueOf(){       // tämä on joku raakaversio uudesta testimetodista tätä ei käytetä pääohjelmatestaukseen
//        int handtotalValue=0, acesAmount=0;
//        for (Card theCard : playerhand){
//            switch (theCard.getValue()){
//                case TWO: handtotalValue += 2; break;
//                case THREE: handtotalValue += 3; break;
//                case FOUR: handtotalValue += 4; break;
//                case FIVE   : handtotalValue += 5; break;
//                case SIX: handtotalValue += 6; break;
//                case SEVEN: handtotalValue += 7; break;
//                case EIGHT: handtotalValue += 8; break;
//                case NINE: handtotalValue += 9; break;
//                case TEN: handtotalValue += 10; break;
//                case JACK: handtotalValue += 10; break;
//                case QUEEN: handtotalValue += 10; break;
//                case KING: handtotalValue += 10; break;
//                case ACE: acesAmount +=1; handtotalValue +=11; break;
//            }
//        }
//        
//        if (handtotalValue>21){
//            int j=1;
//            while(j<=acesAmount && handtotalValue<=21){
//                handtotalValue=handtotalValue-10;
//                j++;
//            }
//            return handtotalValue;
//        }
//        else
//            return handtotalValue;
//    }
//    
//    public int dealerHandValueOf(){     // joku paska koodi
//        int dealerHandValue=0;
//        for (Card aCard : playerhand){
//            switch (aCard.getValue()){
//                case TWO: dealerHandValue += 2; break;
//                case THREE: dealerHandValue += 3; break;
//                case FOUR: dealerHandValue += 4; break;
//                case FIVE   : dealerHandValue += 5; break;
//                case SIX: dealerHandValue += 6; break;
//                case SEVEN: dealerHandValue += 7; break;
//                case EIGHT: dealerHandValue += 8; break;
//                case NINE: dealerHandValue += 9; break;
//                case TEN: dealerHandValue += 10; break;
//                case JACK: dealerHandValue += 10; break;
//                case QUEEN: dealerHandValue += 10; break;
//                case KING: dealerHandValue += 10; break;
//                case ACE: dealerHandValue +=11; break; 
//            }
//            
//        }
//        return dealerHandValue;
//    }
    
    public int handValueOf(){       // tämä on uusi metodi jota testataan laskemaan ässien arvot oikein. vanha metodi siinä on bugi ässien arvon laskemisessa
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
                case ACE: acesAmount +=1; handtotalValue +=11; break;
            }
        }
        int j=1;
        while (handtotalValue>21 && j<=acesAmount){
                handtotalValue=handtotalValue-10;
                j++;
        }
        return handtotalValue;
    }
    /*
    public void recalculateAcesValue(){
        for (Card aceCard : playerhand){
            if (aceCard.getValueAsString().compareTo(ACE)==0){
                
            }
        }
        
    }
    */
      
    /*
        public int handValueOf(){       // tämä oli vanha metodi siinä on bugi ässien arvon laskemisessa
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
    */
    
    
    
    
    
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
    
    

