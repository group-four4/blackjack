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
public class DeltaTestiBlackjack {
    
    public static void main(String[] args) {
        


        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);                    // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(100);
        Bankaccount dealeraccount= new Bankaccount(150);
        Bankaccount pot= new Bankaccount(0);        // alkupotti olio

//        ///TESTIPAKKA ALKAA///
//        Deck testdeck= new Deck();
//        /// TESTIPAKKA PÄÄTTYY///

        Deck playingdeck= new Deck();                                                   // normaali pelipakka
        Hand playerhand= new Hand(playingdeck);                                         // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
        Hand dealerhand= new Hand(playingdeck);
        boolean weArePlaying=true;                                                        // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
     //   double betSize;
        controller.setBankaccounts(playeraccount, dealeraccount, pot);
        controller.setHandsToController(playerhand, dealerhand);                           // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
//       testdeck.createTestingDeck();
        final int numberOfDecksUsed= controller.playingDeckGenerateShuffle(playingdeck);            // asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
        final int boundaryValueForShuffle= (int)( (numberOfDecksUsed*52)/4 );                  // boundaryValue is the variable against which the remainingCArds variable is checked
        int remainingCards;             // this variable is checked per each blackjack round, for the amount of remainingcards  in the playingdeck
        int maingameResult = 0;
        int sidegameResult = 0;
        
        
        mainloop:
        while(weArePlaying){
            double insuranceBet=0;      // bettien määrä täytyy kirjaimellisesti "nollata " aina kun tulee uusi kierros, käsittääkseni...
            double sideBet=0;
            double betSize=0;
            boolean playerMadeInsuranceBet=false;       // boolean variablejen resetoiminen false arvoihin ennen blackjack kierroksen alkua
            boolean playerHasWonInsuranceBet=false;
            boolean playerIsBankrupt=false;

            if (!controller.userWillingnessToPlay()){        // 1.)asks if user wants to keep playing
                break;
            }

            betSize= controller.whatIsBetSize();        // 2.) asks the betsize from user,
            System.out.println("betsize was "+betSize);

            if (betSize==0)         // 3.) if user places illegal bet, he is kicked out of casino
                break;

            if(!controller.playerBettingSimplified(betSize)  )      // 4.) betting procedure, panos menee pot 
                break;      //if user is out of money, he is kicked out of casino
            
            // HUOM betSize menee pot nimiseen olioon joka on tyypiltään Bankaccount
            System.out.println("playersaldo is " +playeraccount.getMoneyAmount());
            controller.dealTwoStartingCards();      // 5.) deal starting cards to dealer and player

            if (controller.insuranceBetOffering()){         // 6.) insurancebet haara, ja insurancebet yritetään antaa,  tässä  myös  tarkistetaan insurancebetin voittaminen ja häviäminen TIETOA VOITOSTA TAI HÄVIÖSTÄ EI SAA PALJASTAA VIELÄ PELAAJALLE
                 insuranceBet= 0.5*betSize;           // TIETO insurancebetin voitosta tai tappiosta tulee vasta kun dealer on saanut luvan paljastaa omat alkukorttinsa
                playerMadeInsuranceBet=true;
                //boolean playerHasWonInsuranceBet=false;
                switch(controller.evaluateInsuranceBet(insuranceBet) ){
                    case 0:  playerIsBankrupt=true; break;             // break mainloop player runs out of money when betting, he gets thrown out from casino, Mainloop== the playing loop of the game
                    case 1:  playerHasWonInsuranceBet=false; break;         // player loses insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                    case 2:  playerHasWonInsuranceBet=true; break;          // player wins the insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                }
                if(playerIsBankrupt){       // breakataan while loopista ulos kun playerIsBankrupt
                    break;
                }
                
            }

            else{                      // insurancebet  rakenne  loppuu tähän else-haaraan, player ei tehnyt insuranceBetia
                System.out.println("you did not make insurancebet ");
            }

            int naturalBlackJackresult= controller.evaluateNaturalBlackJack();          // tässä tarkistetaan saiko pelaaja alkukorteissa "natural blackjack" elikkä joko voitto tai tasapeli heti alkuun

            if (naturalBlackJackresult==1){
                System.out.println("the game is a draw ");      // draw   TÄNNE LISÄTTÄVÄ player saa oman betSizen takaisin omalle accountille
                playeraccount.increaseMoneyAmount(betSize);
            }

            else if ( naturalBlackJackresult==2){
                System.out.println("player wins the hand ");        // player wins      TÄNNE LISÄTTÄVÄ extra maksu playeraccountille. Playerin rewardi = 1.5*betSize
                
            }                                                       // dealeraccountilta tulee vähentää  (1.5*betSize)

            else {  // naturalBlackJackresult should be  zero in this branch ==> no player natural blackjack...
                System.out.println(" player did NOT have natural blackjack ");      // Player doesnt have blackjack, program goes further...
            }


            boolean splitWasMade=false;                 //variable splitWasMade siihen sijoitetaan splitOffering() return value, että joko SPLITTAUS ONNISTUI tai EPÄONNISTUI
            splitWasMade=controller.splitOffering();
             sideBet=betSize;         // sideBet on eri betti kuin betSize, ne voidaan voittaa tai hävitä tai tasapelata toisistaan riippumatta joko molemmat tai toinen.

            
                
                        
            if (splitWasMade && naturalBlackJackresult==0){            // mennään sisään split haaraan ohjelmassa
                view.printSidegameStartMessage();
                Hand sidehand = new Hand(playingdeck);      // HUOM TÄRKEÄ!!! TESTDECKiin viittaus constructorissa ONLY TESTING PURPOSES!!! luodaan sidehand olio
                controller.setSidehandToController(sidehand);  // asetetaan liitetään sidehand ja controlleri
                controller.createSideHandWithTwoCards();        // luodaan sidehandiin tarvittavat kortit siten että, mainhand pilkotaan kahteen osaan, ja toinen kortti menee sidehandiin.
                playerhand.hitPlayerCard();         // TÄRKEÄ koodin pätkä! tämä koodi palauttaa takaisin playerhandiin = maingamen handiin yhden puuttuvan alkukortin ==> maingamessa playerhandillä on 2 alkukorttia nytten
                view.printRevealedDealerCardAndPlayerCards(dealerhand, sidehand);
                

                int sidehandBlackJackresult= controller.evaluateNaturalBlackJackParameters(dealerhand, sidehand);       // tarkistetaan onko playerillää sidehandin alkukorteissa natural blackjackia, JOS on ==> player joko voittaa tai pelaa tasapelin sidegamessa

                if (sidehandBlackJackresult==1){                // tässä kohdassa molemmilal oli natural blackjack==>tasapeli
                    System.out.println("the game is a draw");
                    view.printSidegameEndsMessage();
                    sidegameResult=1;       // result muuttujat ovat integer muuttujia joihin tallennetaan tietoa että kumpi voitti vai tuliko tasapeli
                }

                else if (sidehandBlackJackresult==2){       // elif haarassa playerilla oli alussa natural blackjack==>player voittaa
                    System.out.println("the player wins the hand");
                    view.printSidegameEndsMessage();
                    sidegameResult = 2;
                }

                else {                                                          // else haaraan mennään koska sidegamea täytyy evaluoida ja playeriltä kysytään sidegamen valinnat
                    int sidePlayerDecision= view.askPlayerDecisionsSideGame();

                    if (sidePlayerDecision ==1) {       // player stands in sidegame
                        switch(controller.evaluateStandSidegame()){
                            case 0: System.out.println("sidegame stand dealer wins "); sidegameResult=0; view.printSidegameEndsMessage(); break;
                            case 1: System.out.println("sidegame stand draw "); sidegameResult=1; view.printSidegameEndsMessage() ; break;
                            case 2: System.out.println("sidegame stand player wins"); sidegameResult=2;  view.printSidegameEndsMessage(); break;
                        }
                    }

                    else if (sidePlayerDecision ==2){           // player hits in sidegame
                        switch(controller.evaluateHitSidegame(dealerhand, sidehand)){
                            case 0: System.out.println("sidegame hit dealer wins "); sidegameResult=0;  view.printSidegameEndsMessage(); break;
                            case 1: System.out.println("sidegame hit draw"); sidegameResult=1; view.printSidegameEndsMessage() ; break;
                            case 2: System.out.println("sidegame hit player wins"); sidegameResult=2;  view.printSidegameEndsMessage();break;
                        }
                    }

                    else{       // player doubles down in sidegame

                        switch(controller.evaluateDoubleDownSidegame()){
                            case 0: System.out.println("sidegame doubledown dealer wins "); sidegameResult=0; view.printSidegameEndsMessage(); break;
                            case 1: System.out.println("sidegame doubledown draw "); sidegameResult=1; view.printSidegameEndsMessage(); break;
                            case 2: System.out.println("sidegame doubledown player wins "); sidegameResult=2; view.printSidegameEndsMessage(); break;
                        }
                    }
                }       // sidegamen evaluointi päättyy tähän...




                // TÄSTÄ ALKAA MAINGAME joka pelataan SIDEGAMEN jälkeen...
                // HUOM TÄRKEÄ DEALERILLÄ ON JO OLEMASSA OMAT KORTTINSA dealerhandissä
                // dealerhandiin nostettiin jo pakasta sidegamen aikana dealerin omat kortit.
                // HANDEJA EI SAA VIELÄ CLEARATA MISSÄÄN METODEISSA VIELÄ,
                //VASTA SITTEN CLEARATAAN HANDIT KUN TÄMÄ MAINGAME ON PELATTU LOPPUUN
                // JA TULOKSET ON PRINTATTU ULOS

                view.printMaingameStartMessage();
                  view.printRevealedDealerCardAndPlayerCards(dealerhand, playerhand);
                  int mainhandBlackJackResult= controller.evaluateNaturalBlackJackParameters(dealerhand, playerhand);

                  if(mainhandBlackJackResult==1){      // tässä kohdassa molemmilla oli natural blackjack==>tasapeli
                      System.out.println("the game is a draw");
                      maingameResult=1;
                      view.printMaingameEndsMessage();
                  }

                  else if(mainhandBlackJackResult==2){      // tässä kohdassa player voittaa natural blackjackilla==>pelaaja voitti
                      System.out.println("the player wins the hand");
                      maingameResult=2;
                      view.printMaingameEndsMessage();
                  }

                  else{                         // maingamessa player ei saanut natural blackjackia
                      
                      int mainPlayerDecision=controller.playerDecisionsRegular();       // player stands in maingame  /// HUOM TÄRKEÄ EDITOI EvaluateMetodi NIIN ETTEI NE CLEARAA HANDEJA EIVÄTKÄ PRINTTAA DEALERIN HANDIA
                      if (mainPlayerDecision==1){
                          switch(controller.maingameStandEvaluate()){
                              case 0: System.out.println("maingame stand branch  dealer wins "); maingameResult=0; break;
                              case 1: System.out.println("maingame stand branch  draw "); maingameResult=1; break;
                              case 2: System.out.println("maingame stand branch  player wins"); maingameResult= 2; break;
                          }
                      }

                      else if(mainPlayerDecision==2){           //  player hits in maingame /// /// HUOM TÄRKEÄ EDITOI EvaluateMetodi NIIN ETTEI NE CLEARAA HANDEJA EIVÄTKÄ PRINTTAA DEALERIN HANDIA
                          switch(controller.maingameHitEvaluate()){
                              case 0: System.out.println("maingame hit branch dealer wins "); maingameResult=0; break;
                              case 1: System.out.println("maingame hit branch draw "); maingameResult=1; break;
                              case 2: System.out.println("maingame hit branch player wins "); maingameResult=2; break;
                          }
                      }

                      else{             // player doubles down in maingame

                          switch(controller.maingameDoubleDownEvaluate() ){            /// HUOM TÄRKEÄ EDITOI EvaluateMetodi NIIN ETTEI NE CLEARAA HANDEJA EIVÄTKÄ PRINTTAA DEALERIN HANDIA
                              case 0: System.out.println("maingame doubledown branch dealer wins"); maingameResult=0; break;
                              case 1: System.out.println("maingame doubledown branch draw "); maingameResult=1; break;
                              case 2: System.out.println("maingame doubledown branch player wins "); maingameResult=2; break;
                          }
                      }
                      
                     
                      
                  }
 // lopuksi vasta printataan resultit /// ENSIKSI SIDEGAME RESULTIT
                  System.out.println("__________________________________________________________");
                  System.out.println("results for the sidegame are :");
                  view.announceResultSidegame(sidegameResult);      //// HUOM TÄRKEÄ NETBEANS HERJAA  TÄSTÄ
                  controller.showDealerHand();
                  controller.showSideHand();
//                  controller.clearAllHands();
                  System.out.println("__________________________________________________________");

                  // lopuksi vasta printataan resultit /// TOISEKSI MAINGAME RESULTIT
                  System.out.println("__________________________________________________________");
                  System.out.println("results for the maingame are :");
                  view.announceResultMaingame(maingameResult);          //// HUOM TÄRKEÄ NETBEANS HERJAA TÄSTÄ
                  controller.showDealerHand();
                  controller.showPlayerHand();
                  System.out.println("__________________________________________________________");
                  controller.clearAllHands();
                  

            }     // splittaus haara päättyy tähän... splittaushaara sekä regularPlayerDecisions haara ovat toisensa poissulkevia haaroja ohjelmassa...

            else if (!splitWasMade  && naturalBlackJackresult==0 ){    ///  elif haaran sisällä on se haara, jossa ei ole sidegamea ollenkaan ==> elikkä PLAYER EI SPLITANNUT, lisätty ehtolause EI VIELÄ TESTATTU KUNNOLLA   // pelaaja voi normaaliisti tehdä regular choices 1.) stand 2.) hit 3.) doubledown
                view.printRevealedDealerCardAndPlayerCards(dealerhand, playerhand);       //  printtaaa alkukortit uudestaan jos player unohti ne

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











            // Tähän kohtaan TULEE LISÄTÄ cardsRemaining checkaus playingdeckistä
            remainingCards=controller.checkRemainingCardsInDeck(playingdeck);
            if(remainingCards <= boundaryValueForShuffle){
                controller.restartDeckShuffle(numberOfDecksUsed, playingdeck);
            }





        }







    }
}
