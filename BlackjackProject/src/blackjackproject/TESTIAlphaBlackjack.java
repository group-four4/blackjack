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
public class TESTIAlphaBlackjack {
    public static void main(String[] args) {
        
        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);        // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(150);
        Bankaccount dealeraccount= new Bankaccount(150);
        Bankaccount blackjackPot= new Bankaccount(0);
        Deck playingdeck= new Deck();                       // normaali pelipakka
        Deck testingdeck= new Deck();                   // testipakka jolla testataan ohjelman toiminta
//        Hand playerhand= new Hand(testingdeck);           // hand liitetään testipakkaan älä poista                                                                                  // playerhand tuntee playingdeck:in, josta nostetaan kortteja handiin itseensä.
//        Hand dealerhand= new Hand(testingdeck);      // hand liitetään testipakkaan älä poista                                                                                           // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
        Hand playerhand= new Hand(playingdeck);
        Hand dealerhand= new Hand(playingdeck);
        boolean weArePlaying=true;                                                                                                           // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;             
        
        controller.setHandsToController(playerhand, dealerhand);                                                                                            // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
//        testingdeck.createTestingDeck();
        final int numberOfDecksUsed= controller.playingDeckGenerateShuffle(playingdeck);                                                                // asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
        int boundaryValueForShuffle= (int)( (numberOfDecksUsed*52)/4 );
        int remainingCards;
        
        while(weArePlaying){        // main game loop
            
            
            
            if (!controller.userWillingnessToPlay()){        // 1.)asks if user wants to keep playing
                break;
            }
            
            betSize= controller.whatIsBetSize();        // 2.) asks the betsize from user, 
            System.out.println("betsize was "+betSize);
            
            if (betSize==0)         // 3.) if user places illegal bet, he is kicked out of casino
                break;
            
            if(!controller.playerBetting(betSize, playeraccount, blackjackPot))        // 4.) betting procedure, if user is out of money, he is kicked out of casino
                break;
            
            System.out.println("playersaldo is " +playeraccount.getMoneyAmount());
            controller.dealTwoStartingCards();      // 5.) deal starting cards to dealer and player
            
            
            
            if (controller.insuranceBetOffering()){         // 6.) insurancebet haara, ja insurancebet yritetään antaa, myös tässä if-haarassa   tarkistetaan insurancebetin voittaminen ja häviäminen
                double insuranceBet= 0.5*betSize;
                if (!playeraccount.reduceMoneyAmount(insuranceBet)){
                       System.out.println("youure broke, please leave the casino!!" );
                       break;
                }
                
                System.out.println("you offered the insurancebet valued at " + insuranceBet +", your accountsaldo is " +playeraccount.getMoneyAmount());
                
                if(controller.getDealerHandValue()==21){
                    playeraccount.increaseMoneyAmount(insuranceBet*3);
                    controller.showDealerHand();
                    controller.showPlayerHand();
                    System.out.println("congratulations, you won the insurancebet!!, your reward for insurancebet is " +(insuranceBet*3) );
                }
                else {
                    dealeraccount.increaseMoneyAmount(insuranceBet);
                    controller.showDealerHand();
                    controller.showPlayerHand();
                    System.out.println("you lost insurancebet!! you lose your insurance bet valued at " + insuranceBet );
                }
            }          

            else{                                                   // insurancebet if/else rakenne  loppuu tähän else-haaraan
                System.out.println("you did not make insurancebet ");
            }     
            
            
            
            if ( controller.getDealerHandValue()==21 || controller.getPlayerHandValue()==21 ) {     // tässä if-haarassa testataan oliko kummallakaan dealer tai pelaaja "natural blackjack" elikkä alkukortit value = 21
                if ( controller.getDealerHandValue()==21 &&  controller.getPlayerHandValue()==21 ){
                    System.out.println("the hand is a draw ");
                    controller.clearBothHands();
                }
                else if ( controller.getDealerHandValue()!=21 && controller.getPlayerHandValue()==21 ){
                    System.out.println("the player wins the hand");
                    controller.clearBothHands();
                }
                else{
                    System.out.println("the dealer wins the hand");
                    controller.clearBothHands();
                }
               
            }
            
            else{                           // tämä on else- haara "natural blackjackin" if haaralle, TÄNNE HAARAAN MENNÄÄN KUN KUMMALLAKAAN EI OLE NATURAL BLACKJACKIA
                
               if ( controller.canPlayerSplit() ){     // tässä testaaan onko pelaajan alkukortit samat, elikkä onko splittaus mahdollista, jos mahdollista niin siirrtyään uuteen haaraan
                System.out.println("tänne tulee playerDecisionWithSplit haara");
            }
            
            int playerDecisionRegular=controller.playerDecisionsRegular();      // alussa ensiksi kysytään playeriltä että onko päätös 1) stand, 2) hit 3) double down
            
            
            
            if (playerDecisionRegular==1){                  // 1.) player stands
                System.out.println("player stands ");
                while (controller.getDealerHandValue()<17){
                    controller.dealerHitCard();
                }
                if (  controller.getPlayerHandValue()>controller.getDealerHandValue() || controller.getDealerHandValue()>21 ){
                    System.out.println(" players wins the hand");
                    controller.clearBothHands();
                }
                else if( controller.getPlayerHandValue() == controller.getDealerHandValue() ){
                    System.out.println("the hand is a draw ");
                    controller.clearBothHands();
                }
                else{
                    System.out.println(" dealer wins the hand ");
                    controller.clearBothHands();
                }
            }
            
            
            
            else if (playerDecisionRegular==2){                 // 2.) player hits and ask if keep hitting
                System.out.println("player hits ");
                boolean playerKeepHitting=true;
                
                while (playerKeepHitting==true ){       // player keeps hitting until its illegal or until he stops hitting and therefore he actually stands
                    controller.playerHitCard();         // break statement seems to be necessary (not sure though) because of method usage (playerHitCard method)
                    if (controller.getPlayerHandValue()>21){
                        break;
                    }
                    playerKeepHitting=view.askPlayerKeepHitting();
                }
                
                if (controller.getPlayerHandValue()>21){                  // if player busts by going over 21, player  loses the hand
                    System.out.println("player busts and dealer wins  the hand ");
                    controller.clearBothHands();
                }
                else{
                    while ( controller.getDealerHandValue()<17 && controller.getPlayerHandValue()<=21 ){       // dealer keeps hitting until his handvalue is big enough
                    controller.dealerHitCard();
                    }
                    int dealerHandSum = controller.getDealerHandValue();        // temporary variable for dealervalue
                    
                    if (dealerHandSum > 21 || controller.getPlayerHandValue()> dealerHandSum) {
                        System.out.println("player wins the hand");
                        controller.clearBothHands();
                    }
                    else if ( controller.getPlayerHandValue()== controller.getDealerHandValue() ) {
                        System.out.println("the hand is a draw ");
                        controller.clearBothHands();
                    }
                    else {
                        System.out.println("the dealer wins the hand");
                        controller.clearBothHands();
                    }
                }
                
            }
            
            
            
            else{                                                               // 3.) doubledown
                System.out.println("player doubles down ");
            } 
                
                
                
            }
            
            
            // kopy pastee tähän kohtaan notepadista. ÄLÄ POISTA VIELÄ!!! TÄSSÄ KOHTAA OLI AIEMMIN ELSE-haara johon jouduttiin kun kummallakaan playerilla ja dealerille ei ollut natural blackjackia
            /*
             if ( controller.canPlayerSplit() ){     // tässä testaaan onko pelaajan alkukortit samat, elikkä onko splittaus mahdollista, jos mahdollista niin siirrtyään uuteen haaraan
                System.out.println("tänne tulee playerDecisionWithSplit");
            }
            
            int playerDecisionRegular=controller.playerDecisionsRegular();      // alussa ensiksi kysytään playeriltä että onko päätös 1) stand, 2) hit 3) double down
            
            
            
            if (playerDecisionRegular==1){                  // 1.) player stands
                System.out.println("player stands ");
                while (controller.getDealerHandValue()<17){
                    controller.dealerHitCard();
                }
                if (  controller.getPlayerHandValue()>controller.getDealerHandValue() || controller.getDealerHandValue()>21 )
                    System.out.println(" players wins the hand");
                else if( controller.getPlayerHandValue() == controller.getDealerHandValue() )
                    System.out.println("the hand is a draw ");
                else
                    System.out.println(" dealer wins the hand ");
            }
            
            
            
            else if (playerDecisionRegular==2){                 // 2.) player hits and ask if keep hitting
                System.out.println("player hits ");
                boolean playerKeepHitting=true;
                
                while (playerKeepHitting==true ){       // player keeps hitting until its illegal or until he stops hitting and therefore he stands
                    
                    controller.playerHitCard();         // break statement seems to be necessary (not sure though) because of method usage (playerHitCard method)
                    if (controller.getPlayerHandValue()>21)
                        break;
                    playerKeepHitting=view.askPlayerKeepHitting();
                }
                
                if (controller.getPlayerHandValue()>21){                  // if player busts by going over 21, player  loses the hand
                    System.out.println("player busts and dealer wins  the hand ");
                }
                else{
                    while ( controller.getDealerHandValue()<17 && controller.getPlayerHandValue()<=21 ){       // dealer keeps hitting until his handvalue is big enough
                    controller.dealerHitCard();
                    }
                    int dealerHandSum = controller.getDealerHandValue();        // temporary variable for dealervalue
                    
                    if (dealerHandSum > 21 || controller.getPlayerHandValue()> dealerHandSum) {
                        System.out.println("player wins the hand");
                    }
                    else if ( controller.getPlayerHandValue()== controller.getDealerHandValue() ) {
                        System.out.println("the hand is a draw ");
                    }
                    else {
                        System.out.println("the dealer wins the hand");
                    }
                }
                
            }
            
            
            
            else{                                                               // 3.) doubledown
                System.out.println("player doubles down ");
            }
            */
            
                
                
                
        }
        
        
        
        
    }
    
}
