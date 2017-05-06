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
public class TESTICharlieBlackJack {

    /*
    TÄMÄ ON CHARLIE VAIHEEN TESTIVERSIO
    pyritään saamaan tästä toimiva raakaversio
    
    + printtaukset  on korjattu oikein lopullista versiota varten
    
    tehtäviä jäljellä
    
    -optimoi evaluate metodeja BlackjackProject classissa
    -käytä local muuttujia evaluate metodien sisällä BlackjackProject classissa
    -nimeä uudelleen evaluate metodit BlackjackProject classissa
    -tee valmiiksi rewardPlayer metodi    jolla (insuranceBet) siirtyy dealeraccountilta => playeraccountille
    -tee valmiiksi rewardPlayer metodi  jolla (betSize) siirtyy  dealeraccountilta => playeraccountille 
    -tee valmiiksi rewardPlayer metodi jolla (sideBet) siirtyy dealeraccountilta =>player accountille
    -tee valmiiksi rewardDealer metodi jolla (insuranceBet) siirtyy suoraan dealeraccountille (pluslasku)
    -tee valmiiksi rewardDealer metodi jolla (betSize) siirtyy suoraan dealeraccountille (pluslasku)
    -tee valmiiksi rewardDealer metodi jolla (sideBet) siirtyy suoraan dealeraccountille (pluslasku)
    
     */
    public static void main(String[] args) {
        View view = new View();
        BlackjackProject controller = new BlackjackProject(view);                    // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount = new Bankaccount(150);
        Bankaccount dealeraccount = new Bankaccount(150);
        Bankaccount pot = new Bankaccount(0);

        

        Deck playingdeck = new Deck();                                                   // normaali pelipakka   
        Hand playerhand = new Hand(playingdeck);                                         // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
        Hand dealerhand = new Hand(playingdeck);
        boolean weArePlaying = true;                                                        // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;
        controller.setBankaccounts(playeraccount, dealeraccount, pot);
        controller.setHandsToController(playerhand, dealerhand);                           // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.

        final int numberOfDecksUsed= controller.playingDeckGenerateShuffle(playingdeck);            //   0. PÄÄOHJELMA ALKAA, playeriltä kysytään monellako pakalla (n*52) luodaan pelipakka (eng. "shoe")           asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
        final int boundaryValueForShuffle= (int)( (numberOfDecksUsed*52)/4 );                  // boundaryValue is the variable against which the remainingCArds variable is checked
        int remainingCards;             // this variable is checked per each blackjack round, for the amount of remainingcards  in the playingdeck

        mainloop:
        while (weArePlaying) {

            boolean playerMadeInsuranceBet = false;       // boolean variablejen resetoiminen false arvoihin ennen blackjack kierroksen alkua
            boolean playerHasWonInsuranceBet = false;
            boolean playerIsBankrupt = false;       // boolean tarkistaa että menikö playerilta kaikki rahat loppu
            boolean dealerIsBankrup = false;        // Boolean tarkistaa että loppuiko casinolta omat kassavarannot
            
            if (!controller.userWillingnessToPlay()) {        //   1.)asks if user wants to keep playing
                break;
            }

            betSize = controller.whatIsBetSize();        //   2.) asks the betsize from user, 
            System.out.println("betsize was " + betSize);

            if (betSize == 0) // 3.) if user places illegal bet, he is kicked out of casino
            {
                break;
            }

            if (!controller.playerBettingSimplified(betSize)) //   4.) betting procedure, if user is out of money, he is kicked out of casino
            {
                break;
            }

            System.out.println("playersaldo is " + playeraccount.getMoneyAmount());
            
            controller.dealTwoStartingCards();      // 5.) deal starting cards to dealer and player

            if (controller.insuranceBetOffering()) {         // 6.) insurancebet haara, ja insurancebet yritetään antaa,  tässä  myös  tarkistetaan insurancebetin voittaminen ja häviäminen TIETOA VOITOSTA TAI HÄVIÖSTÄ EI SAA PALJASTAA VIELÄ PELAAJALLE
                double insuranceBet = 0.5 * betSize;           // TIETO insurancebetin voitosta tai tappiosta tulee vasta kun dealer on saanut luvan paljastaa omat alkukorttinsa
                playerMadeInsuranceBet = true;
                switch (controller.evaluateInsuranceBet(insuranceBet)) {
                    case 0:
                        playerIsBankrupt = true;
                        break;             // break mainloop player runs out of money when betting, he gets thrown out from casino, Mainloop== the playing loop of the game
                    case 1:
                        playerHasWonInsuranceBet = false;
                        break;         // player loses insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                    case 2:
                        playerHasWonInsuranceBet = true;
                        break;          // player wins the insurancebet, but the game doesn't reveal any extra information to the player yet about dealer's cards
                }
                if (playerIsBankrupt) {       // breakataan while loopista ulos kun playerIsBankrupt
                    break;
                }
            } else {                      // insurancebet  rakenne  loppuu tähän else-haaraan
                System.out.println("you did not make insurancebet ");
            }

            int naturalBlackJackresult = controller.evaluateNaturalBlackJack();          // tässä tarkistetaan saiko pelaaja alkukorteissa "natural blackjack" elikkä joko voitto tai tasapeli heti alkuun

            if (naturalBlackJackresult == 1) {
                System.out.println("the game is a draw ");      // draw   TÄNNE LISÄTTÄVÄ player saa oman betSizen takaisin omalle accountille
            } else if (naturalBlackJackresult == 2) {
                System.out.println("player wins the hand ");        // player wins      TÄNNE LISÄTTÄVÄ extra maksu playeraccountille. Playerin rewardi = 1.5*betSize
            } // dealeraccountilta tulee vähentää  (1.5*betSize)
            else {  // naturalBlackJackresult should be  zero in this branch ==> no player natural blackjack...
                System.out.println(" player did NOT have natural blackjack ");      // Player doesnt have blackjack, program goes further...
            }

            boolean splitWasMade = false;                 //variable splitWasMade siihen sijoitetaan splitOffering() return value, että joko SPLITTAUS HYVÄKSYTTIIN tai SPLITTAUS KIELTÄYDYTTIIN
            splitWasMade = controller.splitOffering();

            if (splitWasMade && naturalBlackJackresult == 0) {            // KUN PELAAJALLA ON PARI (esim. pokeripari) alkukorteissaan, niin hänellä on neljäs vaihtoehto 4.) split// tässä ohjelma haarautuu kahtia viimeisiin kahteen eri lohkoon kun pelaajan täytyy päättää mitä tehdä alkukorteillensa
                // split branch ohjelmasta, se tulee tännä if- haaran sisälle
                System.out.println("__________________________________________________________");
                System.out.println("THIS IS THE SIDEGAME: ");
                double sideBet = betSize;         // sideBet on eri betti kuin betSize, ne voidaan voittaa tai hävitä tai tasapelata toisistaan riippumatta joko molemmat tai toinen.
                
                if (!controller.playerSideBetting(sideBet)){        // jos playerin rahat eivät riitä sideBetin tekemiseen niin pelaaja saa häädön casinolta
                    break;
                }
                
                Hand sidehand = new Hand(playingdeck);      // HUOM TÄRKEÄ!!! TESTDECKiin viittaus constructorissa ONLY TESTING PURPOSES!!! luodaan sidehand olio
                controller.setSidehandToController(sidehand);  // asetetaan liitetään sidehand ja controlleri
                controller.createSideHandWithTwoCards();        // luodaan sidehandiin tarvittavat kortit siten että, mainhand pilkotaan kahteen osaan, ja toinen kortti menee sidehandiin.
                playerhand.hitPlayerCard();         // TÄRKEÄ koodin pätkä! tämä koodi palauttaa takaisin playerhandiin = maingamen handiin yhden puuttuvan alkukortin ==> maingamessa playerhandillä on 2 alkukorttia nytten
                view.printRevealedDealerCardAndPlayerCards(dealerhand, sidehand);
                
                int sidegameResult;     // variable tallentaa sidegamen lopputuloksen, en ole varma onko juurikaan tarpeellinen mutta onpahan tehty /// näitä gameResult variableja voi käyttää bettien voittamisen laskemiseen.

                int sidehandBlackJackresult = controller.evaluateNaturalBlackJackParameters(dealerhand, sidehand);       // tarkistetaan onko playerillää sidehandin alkukorteissa natural blackjackia, JOS on ==> player joko voittaa tai pelaa tasapelin sidegamessa

                if (sidehandBlackJackresult == 1) {                // tässä kohdassa molemmilal oli natural blackjack
                    System.out.println("the game is a draw");
                    view.printSidegameEndsMessage();
                    sidegameResult = 1;
                } else if (sidehandBlackJackresult == 2) {       // elif haarassa playerilla oli alussa natural blackjack==>player voittaa
                    System.out.println("the player wins the hand");
                    view.printSidegameEndsMessage();
                    sidegameResult = 2;
                } else {                                                          // else haaraan mennään kun playreillä ei ollut sidegamessa natural blackjackia sidehandissään
                    int sidePlayerDecision = view.askPlayerDecisionsSideGame();

                    if (sidePlayerDecision == 1) {       // player stands in sidegame
                        switch (controller.evaluateStandSidegame()) {
                            case 0:
                                System.out.println("sidegame stand dealer wins ");
                                sidegameResult = 0;
                                view.printSidegameEndsMessage();
                                break;
                            case 1:
                                System.out.println("sidegame stand draw ");
                                sidegameResult = 1;
                                view.printSidegameEndsMessage();
                                break;
                            case 2:
                                System.out.println("sidegame stand player wins");
                                sidegameResult = 2;
                                view.printSidegameEndsMessage();
                                break;
                        }
                    } else if (sidePlayerDecision == 2) {           // player hits in sidegame
                        switch (controller.evaluateHitSidegame(dealerhand, sidehand)) {
                            case 0:
                                System.out.println("sidegame hit dealer wins ");
                                sidegameResult = 0;
                                view.printSidegameEndsMessage();
                                break;
                            case 1:
                                System.out.println("sidegame hit draw");
                                sidegameResult = 1;
                                view.printSidegameEndsMessage();
                                break;
                            case 2:
                                System.out.println("sidegame hit player wins");
                                sidegameResult = 2;
                                view.printSidegameEndsMessage();
                                break;
                        }
                    } else {
                        // nothing here yet.... tänne mahdollisesti koodataan doubledown haara tänne else haaran sisälle
                        // tämä olisi sidegamen double down haara
                    }
                }

                // tästä alkaa maingame joka pelataan sidegamen jälkeen...
                // HUOM TÄRKEÄ DEALERILLÄ ON JO OLEMASSA OMAT KORTTINSA dealerhandissä
                // dealerhandiin nostettiin jo pakasta sidegamen aikana dealerin omat kortit.
                // DEALERIN KORTIT EIVÄT MUUTU NE OVAT TALLESSA dealerhand oliossa
                
                view.printMaingameStartMessage();
                int maingameResult;
                view.printRevealedDealerCardAndPlayerCards(dealerhand, playerhand);

                int mainhandBlackJackResult = controller.evaluateNaturalBlackJackParameters(dealerhand, playerhand);

                if (mainhandBlackJackResult == 1) {
                    System.out.println("the game is a draw");
                    maingameResult = 1;
                    view.printMaingameEndsMessage();
                } else if (mainhandBlackJackResult == 2) {
                    System.out.println("the player wins the hand");
                    maingameResult = 2;
                    view.printMaingameEndsMessage();
                } else {
                    int mainPlayerDecision = controller.playerDecisionsRegular();
                    if (mainPlayerDecision == 1) {
                        switch (controller.playerStandEvaluate()) {         // HUOM MUISTA EDITOIDA BOOLEANIT PARAMETRIKSI!!! splitWasMade
                            case 0:
                                System.out.println("maingame stand branch  dealer wins ");
                                break;
                            case 1:
                                System.out.println("maingame stand branch  draw ");
                                break;
                            case 2:
                                System.out.println("maingame stand branch  player wins");
                                break;
                        }
                    } else if (mainPlayerDecision == 2) {           // HUOM TÄRKEÄ!!! JOS playerillä on splittien jälkeen vain 2 vaihtoehtoa 1.)hit 2.) stand, niin virheentarkistus playerin input valinnoissa tulee ottaa tässä kohtaa huomioon
                        switch (controller.playerHitEvaluate()) {
                            case 0:
                                System.out.println("maingame hit branch dealer wins ");
                                break;
                            case 1:
                                System.out.println("maingame hit branch draw ");
                                break;
                            case 2:
                                System.out.println("maingame hit branch player wins ");
                                break;
                        }
                    } else {
                        //nothing here yet, 
                        //maingame doubledown haara tulee tänne else haaran sisään
                    }
                }

            } // splittaus haara päättyy tähän... splittaushaara sekä regularPlayerDecisions haara ovat toisensa poissulkevia haaroja ohjelmassa...                                                                  
            else if (!splitWasMade && naturalBlackJackresult == 0) {    ///  elif haaran sisällä on se haara, jossa ei ole sidegamea ollenkaan ==> elikkä PLAYER EI SPLITANNUT, lisätty ehtolause EI VIELÄ TESTATTU KUNNOLLA   // pelaaja voi normaaliisti tehdä regular choices 1.) stand 2.) hit 3.) doubledown               
                view.printRevealedDealerCardAndPlayerCards(dealerhand, playerhand);       //  printtaaa alkukortit uudestaan jos player unohti ne
                int regularPlayerDecision = controller.playerDecisionsRegular();
                if (regularPlayerDecision == 1) {                  // player stands 
                    switch (controller.playerStandEvaluate()) {         // HUOM MUISTA EDIOIDA BOOLEANIT PARAMETRIKSI splitWasMade
                        case 0:
                            System.out.println("STAND BRANCH DEALER WINS!");
                            break;     // tänne pakko lisätä insuranceBet tulos ja betSizen lisäys dealerinaccountille
                        case 1:
                            System.out.println("STAND BRANCH DRAW!");
                            break;        // tänne on pakko lisätä insuranceBet tulos ja betSizen lisäys playeraccountille
                        case 2:
                            System.out.println("STAND BRANCH PLAYER WINS!");
                            break;     // tänne on pakko lisätä insuranceBet tulos ja betSizen vähennys dealeraccountilta, jos dealeraccount on bankrupt, peli loppuu// jos taas dealeraccount EI OLE bankrupt, ==> betSize lisätään playeraccountille
                    }
                } else if (regularPlayerDecision == 2) {            // player hits a card, or many cards
                    switch (controller.playerHitEvaluate()) {
                        case 0:
                            System.out.println("HIT BRANCH DEALER WINS!");
                            break;
                        case 1:
                            System.out.println("HIT BRANCH DRAW!");
                            break;
                        case 2:
                            System.out.println("HIT BRANCH PLAYER WINS!");
                            break;
                    }
                } else {           // player doubles down
                    switch (controller.playerDoubleDownEvaluate()) {
                        case 0:
                            System.out.println("DD BRANCH DEALER WINS!");
                            break;
                        case 1:
                            System.out.println("DD BRANCH DRAW!");
                            break;
                        case 2:
                            System.out.println("DD BRANCH PLAYER WINS!");
                            break;
                    }
                }
            } 

            // Tähän kohtaan TULEE LISÄTÄ cardsRemaining checkaus playingdeckistä
            remainingCards = playingdeck.checkCardsAmountInDeck();
            if (remainingCards <= boundaryValueForShuffle){
                controller.restartDeckShuffle(numberOfDecksUsed, playingdeck);      // tämä restarttaa playingdeckin täysillä alkuperäisten korttien lukumäärällä, metodi myös sekoittaa playingdeckin uudelleen.
            }
        }

    }

}
