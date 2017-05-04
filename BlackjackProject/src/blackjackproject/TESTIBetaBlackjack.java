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

public class TESTIBetaBlackjack {
    
    public static void main(String[] args) {
        

//        HUOM tämä on BETA VERSIO BLACKJACKISTA TÄSSÄ VERSIOSSA TESTATAAN CONTROLLERIN METODIEN TOIMIVUUTTA
//          muuta testattavaa asiaa ovat  controllerin maksumetodit sekä insurancebetin toiminta OIKEAOPPISESTI 
//          SITEN ETTÄ insurancebetin tiedot eivät tule näkyviin liian aikaisin pelaajan tietoon.
/*
Muuta huomioitavaa
natural blackjack arvointi on korjattu oikein ainakin omasta mielestäni
esim. Kun, player voittaa natural blackjackillä niin sitten hänelle annetaan extra rahat dealeriltä (1.5*betSize menee dealerin tililtä pottiin, ja player saa potin itselleen (nyt potti siis koostui 1.5*betSize + betSize)

natural blackjack on pakko arvioida omana "special casenaan" koska jos dealerillä on natural, ja pelaajalle EI ole naturalia mutta kuitenkin niin että pelaajalla on 21 ==> dealeri voittaa siitä huolimatta
sama pätee tietenkin jos playerillä on natural ja dealerillä ei ole naturalia ==> pelaaja voitta extra rewardin ilman korttien nostamista tai päätöksiä


*/


        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);                    // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(150);
        Bankaccount dealeraccount= new Bankaccount(150);
        Bankaccount pot= new Bankaccount(0);
        Deck playingdeck= new Deck();                                                   // normaali pelipakka   
        Hand playerhand= new Hand(playingdeck);                                         // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
        Hand dealerhand= new Hand(playingdeck);
        boolean weArePlaying=true;                                                        // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;             
        controller.setBankaccounts(playeraccount, dealeraccount, pot);
        controller.setHandsToController(playerhand, dealerhand);                           // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
        final int numberOfDecksUsed= controller.playingDeckGenerateShuffle(playingdeck);            // asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
        int boundaryValueForShuffle= (int)( (numberOfDecksUsed*52)/4 );                  // boundaryValue is the variable against which the remainingCArds variable is checked
        int remainingCards;
        
        
        mainloop:while(weArePlaying){
            
            boolean playerMadeInsuranceBet=false;
            if (!controller.userWillingnessToPlay()){        // 1.)asks if user wants to keep playing
                break;
            }
            
            betSize= controller.whatIsBetSize();        // 2.) asks the betsize from user, 
            System.out.println("betsize was "+betSize);
            
            if (betSize==0)         // 3.) if user places illegal bet, he is kicked out of casino
                break;
            
            if(!controller.playerBettingSimplified(betSize)  )      // 4.) betting procedure, if user is out of money, he is kicked out of casino
                break;
            
            System.out.println("playersaldo is " +playeraccount.getMoneyAmount());
            controller.dealTwoStartingCards();      // 5.) deal starting cards to dealer and player
            
            if (controller.insuranceBetOffering()){         // 6.) insurancebet haara, ja insurancebet yritetään antaa,  tässä  myös  tarkistetaan insurancebetin voittaminen ja häviäminen TIETOA VOITOSTA TAI HÄVIÖSTÄ EI SAA PALJASTAA VIELÄ PELAAJALLE
                double insuranceBet= 0.5*betSize;           // TIETO insurancebetin voitosta tai tappiosta tulee vasta kun dealer on saanut luvan paljastaa omat alkukorttinsa
                playerMadeInsuranceBet=true;
                boolean playerHasWonInsuranceBet=false;
                switch(controller.evaluateInsuranceBet(insuranceBet) ){
                    case 0: break mainloop;             // player runs out of money when betting, he gets thrown out from casino, Mainloop== the playing loop of the game
                    case 1:  playerHasWonInsuranceBet=false; break;         // player loses insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                    case 2:  playerHasWonInsuranceBet=true; break;          // player wins the insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                }
            }
            else{                      // insurancebet if/else rakenne  loppuu tähän else-haaraan
                System.out.println("you did not make insurancebet ");
            }  
            
            int naturalBlackJackresult= controller.evaluateNaturalBlackJack();          // tässä tarkistetaan saiko pelaaja alkukorteissa "natural blackjack" elikkä joko voitto tai tasapeli heti alkuun
            
            if (naturalBlackJackresult==1){
                System.out.println("the game is a draw ");      // draw   TÄNNE LISÄTTÄVÄ player saa oman betSizen takaisin omalle accountille
            }
            else if ( naturalBlackJackresult==2){
                System.out.println("player wins the hand ");        // player wins      TÄNNE LISÄTTÄVÄ extra maksu playeraccountille. Playerin rewardi = 1.5*betSize
            }                                                       // dealeraccountilta tulee vähentää  (1.5*betSize)
            else {  // naturalBlackJackresult should be  zero in this branch ==> no player natural blackjack...
                System.out.println(" player did NOT have natural blackjack ");      // Player doesnt have blackjack, program goes further...
            }
            
            
            
            if (controller.splitOffering() && naturalBlackJackresult==0){            // KUN PELAAJALLA ON PARI (esim. pokeripari) alkukorteissaan, niin hänellä on neljäs vaihtoehto 4.) split// tässä ohjelma haarautuu kahtia viimeisiin kahteen eri lohkoon kun pelaajan täytyy päättää mitä tehdä alkukorteillensa
                System.out.println("give player split choices SPECIAL CHOICES ");        
                // split branch ohjelmasta tulee tännä if haaran sisälle
            }                                                                      
      
            else if (controller.splitOffering()==false  && naturalBlackJackresult==0 ){    /// else haara korjattu elif haaralla, lisätty ehtolause EI VIELÄ TESTATTU KUNNOLLA   // pelaaja voi normaaliisti tehdä regular choices 1.) stand 2.) hit 3.) doubledown               
                int regularPlayerDecision=controller.playerDecisionsRegular();
                if (regularPlayerDecision ==1)                  // player stands 
                    switch(controller.playerStandEvaluate() ){
                        case 0: System.out.println("STAND BRANCH DEALER WINS!"); break;     // tänne pakko lisätä insuranceBet tulos ja betSizen lisäys dealerinaccountille
                        case 1: System.out.println("STAND BRANCH DRAW!"); break;        // tänne on pakko lisätä insuranceBet tulos ja betSizen lisäys playeraccountille
                        case 2: System.out.println("STAND BRANCH PLAYER WINS!"); break;     // tänne on pakko lisätä insuranceBet tulos ja betSizen vähennys dealeraccountilta, jos dealeraccount on bankrupt, peli loppuu// jos taas dealeraccount EI OLE bankrupt, ==> betSize lisätään playeraccountille
                    }                                                                       
                
                else if(regularPlayerDecision == 2){            // player hits a card, or many cards
                    switch(controller.playerHitEvaluate() ){
                        case 0: System.out.println("HIT BRANCH DEALER WINS!"); break;
                        case 1: System.out.println("HIT BRANCH DRAW!"); break;
                        case 2: System.out.println("HIT BRANCH PLAYER WINS!"); break;
                    }
                }
                else{
                    switch(controller.playerDoubleDownEvaluate() ){
                        case 0: System.out.println("DD BRANCH DEALER WINS!"); break;
                        case 1: System.out.println("DD BRANCH DRAW!"); break;
                        case 2: System.out.println("DD BRANCH PLAYER WINS!"); break;
                    }
                    
                    
                    
                    
                }
            }
            
            else{
                // empty else branch, nothing special happens here
                // tätä main programmin rakennetta piti tarkentaa tällä tavalla koska tuli 
                // aluksi pikku bugi että playeri voitti natural blackjackillä, mutta siltä kysyttiin silti
                //että haluaako playeri  hitata standata tai doubledownata PELI OLI JO OHI SILTÄ KIERROKSELTA!!!
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        }
        
        
        
        
        
        
        
    }
    
}
