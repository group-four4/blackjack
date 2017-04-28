/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackproject;

/**
 *
 * @author Late
 */
public class BlackjackProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Deck pelipakka= new Deck();     //luodaan pelipakka olio johon laitetaan Card objeckteja
        pelipakka.createPlayingDeck(1);      // pelipakkaan generoidaan 52 Card objektia listaan
        System.out.println(pelipakka);      // pelipakka printataan ulos
        
        
       // Deck pelaajankäsi= new Deck();
        
        
        Hand playerhand= new Hand(pelipakka);
        playerhand.hitPlayerCard();
        
                System.out.println(playerhand.toString());
                
        System.out.println(pelipakka);

        
        pelipakka.shufflePlayingDeck();
        System.out.println(pelipakka);
        
        
        /*
        pelaajankäsi.drawOneCardFromDeck(pelipakka);
        System.out.println(pelaajankäsi);
        System.out.println("");
        System.out.println(pelipakka);
        
        
        Hand pelaajankäsi = new Hand(pelipakka);        // luodaan pelaajankäsi olio joka on  Hand-tyyppiä
        */
        
    }
    
}
