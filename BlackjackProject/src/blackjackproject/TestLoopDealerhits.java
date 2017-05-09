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
public class TestLoopDealerhits {
    
    
    public static void main(String[] args) {
        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);                    // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(150);
        Bankaccount dealeraccount= new Bankaccount(150);
        Bankaccount pot= new Bankaccount(0);
        
        ///TESTIPAKKA ALKAA///
        Deck testdeck= new Deck();
        /// TESTIPAKKA PÄÄTTYY///
        
        Deck playingdeck= new Deck();                                                   // normaali pelipakka   
        Hand playerhand= new Hand(testdeck);                                         // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
        Hand dealerhand= new Hand(testdeck);
        boolean weArePlaying=true;                                                        // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;             
        controller.setBankaccounts(playeraccount, dealeraccount, pot);
        controller.setHandsToController(playerhand, dealerhand);                           // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
        testdeck.createTestingDeck();
        
        controller.dealTwoStartingCards();      
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
            
             if ( naturalBlackJackresult==0 ){            
               // view.printRevealedDealerCardAndPlayerCards(dealerhand, playerhand);       //  printtaaa alkukortit uudestaan jos player unohti ne
                int regularPlayerDecision=controller.playerDecisionsRegular();
                if (regularPlayerDecision ==1){                  // player stands 
                    switch(controller.playerStandEvaluate() ){
                        case 0: System.out.println("STAND BRANCH DEALER WINS!"); break;     // tänne pakko lisätä insuranceBet tulos ja betSizen lisäys dealerinaccountille
                        case 1: System.out.println("STAND BRANCH DRAW!"); break;        // tänne on pakko lisätä insuranceBet tulos ja betSizen lisäys playeraccountille
                        case 2: System.out.println("STAND BRANCH PLAYER WINS!"); break;     // tänne on pakko lisätä insuranceBet tulos ja betSizen vähennys dealeraccountilta, jos dealeraccount on bankrupt, peli loppuu// jos taas dealeraccount EI OLE bankrupt, ==> betSize lisätään playeraccountille
                    }
                }
                
                else if(regularPlayerDecision == 2){            // player hits a card, or many cards
                    switch(controller.playerHitEvaluate() ){
                        case 0: System.out.println("HIT BRANCH DEALER WINS!"); break;
                        case 1: System.out.println("HIT BRANCH DRAW!"); break;
                        case 2: System.out.println("HIT BRANCH PLAYER WINS!"); break;
                    }
                }
                else{           // player doubles down
                    switch(controller.playerDoubleDownEvaluate() ){
                        case 0: System.out.println("DD BRANCH DEALER WINS!"); break;
                        case 1: System.out.println("DD BRANCH DRAW!"); break;
                        case 2: System.out.println("DD BRANCH PLAYER WINS!"); break;
                    }  
                }
            }
        
    }
}
