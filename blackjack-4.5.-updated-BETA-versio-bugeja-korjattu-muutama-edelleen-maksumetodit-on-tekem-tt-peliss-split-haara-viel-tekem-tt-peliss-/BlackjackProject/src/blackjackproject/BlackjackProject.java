
package blackjackproject;


public class BlackjackProject {     // <----------CONTROLLERIN METODIT LÖYTYY TÄÄLTÄ (Controller class = BlackjackProject class)
    
    private final View theView;
    private  Hand playerhand;
    private Hand dealerhand;
    private Bankaccount playeraccount;
    private Bankaccount dealeraccount;
    private Bankaccount pot;
    
    public BlackjackProject(View theView){      //CONTROLLERIN CONSTRUCTOR controller tuntee view:in
        this.theView= theView;
    }
    
    public boolean userWillingnessToPlay(){
        return theView.askIfKeepPlaying();
    }
    
    public double whatIsBetSize(){
        return theView.askBetSize();
    }
    
    public void setBankaccounts(Bankaccount playeraccount, Bankaccount dealeraccount, Bankaccount thePot){
        this.playeraccount=playeraccount;
        this.dealeraccount=dealeraccount;
        this.pot=thePot;
    }
    
    
    public boolean playerBettingSimplified(double betSize){         // parannelty metodi playerin bettaamista varten alussa elikkä alkupanos HUOM EI VIELÄ TESTATTU
        if (playeraccount.reduceMoneyAmount(betSize)){
            pot.increaseMoneyAmount(betSize);
            return true;
        }
        else{
            theView.rudeBehaviourMessage();
            return false;
        }
    }
    
    public boolean playerBetting(double bettingMoney, Bankaccount bettingAccount, Bankaccount thePot){
        if (bettingAccount.reduceMoneyAmount(bettingMoney)){
            thePot.increaseMoneyAmount(bettingMoney);
            return true;
        }
        else{
            theView.rudeBehaviourMessage();
            return false;
        }
    }
    
    public int playingDeckGenerateShuffle(Deck thePlayingDeck){
        int numberOfDecks= theView.askHowManyDecksToUse();
        thePlayingDeck.createPlayingDeck(numberOfDecks);
        thePlayingDeck.shuffleArrayList();
        return numberOfDecks;
    }
   
    public void setHandsToController(Hand playerhand, Hand dealerhand){
        this.dealerhand=dealerhand;
        this.playerhand=playerhand;
    }
    public void dealTwoStartingCards(){
        playerhand.dealStartingCards();
        dealerhand.dealStartingCards();
        String revealedDealerCard= dealerhand.revealOnlyOneCard();
        theView.printString("Dealer's starting cards are ");
        theView.printString(revealedDealerCard + " - " + "UNKNOWN");
        theView.printString("Player's starting cards are ");
        theView.printString(playerhand.toString());
    }
    
    public int getPlayerHandValue(){
        return playerhand.handValueOf();
    }
    
    public int getDealerHandValue(){
        return dealerhand.handValueOf();
    }
    
    public boolean insuranceBetOffering(){
        if (dealerhand.isOpenCardAce()){
            if(theView.askInsuranceBet()){
                return true;
            }
            else
                return false;
        }
        else
            return false; 
    }
    public int evaluateInsuranceBet(double insuranceBet){
        if (!playeraccount.reduceMoneyAmount(insuranceBet)){
                       System.out.println("youure broke, please leave the casino!!" );
                       return 0;
                }
        else{
                System.out.println("you offered the insurancebet valued at " + insuranceBet +", your accountsaldo is " +playeraccount.getMoneyAmount());
                
                if(dealerhand.handValueOf()==21){
                    playeraccount.increaseMoneyAmount(insuranceBet);
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at " + playerhand.handValueOf());
                    System.out.println("congratulations, you won the insurancebet!!, your reward for insurancebet is " +(insuranceBet*3) );
                    return 2;
                }
                else {
                    dealeraccount.increaseMoneyAmount(insuranceBet);
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at " + playerhand.handValueOf());
                    System.out.println("you lost insurancebet!! you lose your insurance bet valued at " + insuranceBet );
                    return 1;
                }
            }
        
        
        
    }
    
    
    
    
    public boolean splitOffering(){         // uusi testimetodi joka on näppärämpi boolean metodi splittauksen päättämiseen EI VIELÄ TESTATTU KUNNOLLA // PÄIVITYS KORJATTU METODI
        if (playerhand.doesPlayerHaveSameStartingCards() ){
            if(theView.askSplit()){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    public boolean canPlayerSplit(){            // JOKU WANHA METODI
        if ( playerhand.doesPlayerHaveSameStartingCards() ){
            return true;
        }
        else
            return false;
    }
    
    public int playerDecisionsRegular(){
        return theView.askPlayerDecisionsRegular();

    }
    
    public void showPlayerHand(){
        theView.printString("the player has: ");
        theView.printString(playerhand.toString());
        theView.printString("player hand is valued at "+playerhand.handValueOf() );         // tämä oli tuore lisäys koodiin TÄTÄ EI OLE TESTATTU MERKITTÄVÄSTI VIELÄ /// tätä metodia nyt testattiin jonkin verran ja vaikuttaa toimivan OK, metodin sisällöt on otettu parempaan hhyötykäyttöön joissakin controller.evaluate yms metodeissa
    }
    public void showDealerHand(){
        theView.printString("the dealer has: ");
        theView.printString(dealerhand.toString());
        theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );        // tämä oli tuore lisäys koodiin TÄTÄ EI OLE TESTATTU MERKITTÄVÄSTI VIELÄ /// tätä metodia nyt testattiin jonkin verran ja vaikuttaa toimivan OK, metodin sisällöt on otettu parempaan hhyötykäyttöön joissakin controller.evaluate yms metodeissa
    }
    public void dealerHitCard(){
         dealerhand.hitPlayerCard();
         dealerhand.handValueOf();      // paluuarvoa ei hyödynnetä tässä metodissa MUTTA JOKAISEN HITTAUKSEN JÄLKEEN jälkeen pitää tarkistaa uusi pelikorttien tilanne ässien arvon uudelleen laskemista varten.
         theView.printString(dealerhand.toString());
         System.out.println("dealerhand is valued at "+dealerhand.handValueOf());
    }
    
    public void playerHitCard(){        // metodia on editoidu MUTTA EI VIELÄ testattu että laskeeko se uudelleen ässien arvot oikein!! /// metodi vaikuttaisi laskevan oikein pläyerin handvaluen sekä ässien arvot oikein tilanteen mukaan 11 tai 1
        playerhand.hitPlayerCard();
        playerhand.handValueOf();
        theView.printString(playerhand.toString());
        System.out.println("playerhand is valued at "+playerhand.handValueOf());
    }
    
    public void clearBothHands(){           // metodia käytttiin kun yksi rundi pelattiin loppuun, niin hand-oliot pitää tyhjentää korteistaan.
        dealerhand.clearHand();
        playerhand.clearHand();
    }
    
    public int checkRemainingCardsInDeck(Deck playingdeck){         // tärkeaä metodi!!! estää korttien loppumisen playingdeckistä, metodi liittyy juuurikin siihen että kun playingdeckin cardit on tarpeeksi "alhaalla" lukumäärässä niin rundin jälkeen VIIMEISEKSI OPERAATIOKSI(??) tarkastataan riittävätkö kortit jne. jne...
        int remainingcards = playingdeck.checkCardsAmountInDeck();
        return remainingcards;
    }
    
    public void restartDeckShuffle(int amountOfDecks, Deck playingdeck){        // metodi liittyy korttien loppumisen playingdeckistä ja uudelleen sekoittamiseen ja playingdeckin "restarttaamiseen" 
        theView.printString("There aren't enough cards in the shoe, we will shuffle the deck(s) again now. ");
        int number= amountOfDecks;
        playingdeck.createPlayingDeck(number);
        playingdeck.shuffleArrayList();
    }
    
    public int evaluateNaturalBlackJack(){          // metodi tarkastaa että JOS pelaaja sai ace + ten = 21 alkukorteissaan, niin sitten mennään tänne tarkistamaan että tuliko suoraan voitto playerille, vai tasapeli playerille.
        if (playerhand.handValueOf()==21){
            if (playerhand.handValueOf()==21 && dealerhand.handValueOf()==21){
                theView.printString("both have natural blackjack, the game is a draw  ");
                return 1;
            }
            else {
                theView.printString("player has natural blackjack, the player wins ");
                return 2;
            }
        }
        return 0;
    }
    
    public int playerHitEvaluate() {            // tämä on erittäin iso metodi, ja tämä metodi evaluoi kokonaisuudessaan PLAYER HITS extra cards playerin valinnan, että mitä tapahtui yhden blackjack rundin aikana, tuliko tasapeli, voitto vai tappio. Metodin paluuarvo on integer ja 2== voitto 1 == tasapeli 0== tappio 
        System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("player hits ");
        theView.printString("the player has: ");
        theView.printString(playerhand.toString());
        theView.printString("player hand is valued at " + playerhand.handValueOf());
        boolean playerKeepHitting = true;
        System.out.println("the player starts hitting ");

        while (playerKeepHitting == true) {       // player keeps hitting until its illegal or until he stops hitting and therefore he actually stands
            System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
            playerhand.hitPlayerCard();
            playerhand.handValueOf();
            theView.printString(playerhand.toString());
            System.out.println("playerhand is valued at " + playerhand.handValueOf());
            if (playerhand.handValueOf() > 21) {      // break statement seems to be necessary (not sure though) because of method usage (playerHitCard method)
                break;
            }
            playerKeepHitting = theView.askPlayerKeepHitting();
        }
        if (dealerhand.handValueOf()==21){       // tests if dealer has natural blackjack in his starting cards, if dealer has natural, then player loses immediately
            theView.printString("the dealer has natural blackjack, the dealer wins: ");
            theView.printString(dealerhand.toString());
            dealerhand.clearHand();
            playerhand.clearHand();
            return 0;
        }
        if (playerhand.handValueOf() > 21) {                  // if player busts by going over 21, player  loses the hand
            System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
            theView.printString("the dealer has: ");
            theView.printString(dealerhand.toString());
            theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
            theView.printString("the player has: ");
            theView.printString(playerhand.toString());
            theView.printString("player hand is valued at " + playerhand.handValueOf());
            System.out.println("");
            System.out.println("player busts and dealer wins the hand ");
            dealerhand.clearHand();
            playerhand.clearHand();
            return 0;
        } else {                                           // if the player DID NOT bust by going over 21, we go to this else-branch to evaluate further
            System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
            theView.printString("the dealer has: ");
            theView.printString(dealerhand.toString());
            theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
            if(dealerhand.handValueOf()>=17){
                System.out.println("the dealer stands ");
            }
            else{
                System.out.println("the dealer starts hitting ");
            }
            

            while (dealerhand.handValueOf() < 17 && playerhand.handValueOf() <= 21) {       // dealer keeps hitting until his handvalue is big enough
                dealerhand.hitPlayerCard();
            //    dealerhand.handValueOf();      // paluuarvoa ei hyödynnetä tässä metodissa MUTTA JOKAISEN HITTAUKSEN JÄLKEEN jälkeen pitää tarkistaa uusi pelikorttien tilanne ässien arvon uudelleen laskemista varten. Ässien arvo ei ole fixattu missään vaiheessa peliä oikeastaan, vaan jos ässä oli alussa 11 arvoinen, niin seuraavan kortin tultua, se ässä voi palata ykkösen arvoiseksi
              if(dealerhand.handValueOf()>=17){
                  theView.printString(dealerhand.toString());
                System.out.println("dealerhand is valued at " + dealerhand.handValueOf());
                break;
              }
            theView.printString(dealerhand.toString());
                System.out.println("dealerhand is valued at " + dealerhand.handValueOf());
            }

            int dealerHandSum = dealerhand.handValueOf();        //  variable for dealervalue temporary usage here...

            if (dealerHandSum > 21 || playerhand.handValueOf() > dealerHandSum) {
                theView.printString("the dealer has: ");
                theView.printString(dealerhand.toString());
                theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
                theView.printString("the player has: ");
                theView.printString(playerhand.toString());
                theView.printString("player hand is valued at " + playerhand.handValueOf());
                System.out.println("");
                System.out.println("player wins the hand");
                dealerhand.clearHand();
                playerhand.clearHand();
                return 2;
            } else if (playerhand.handValueOf() == dealerhand.handValueOf()) {
                theView.printString("the dealer has: ");
                theView.printString(dealerhand.toString());
                theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
                theView.printString("the player has: ");
                theView.printString(playerhand.toString());
                theView.printString("player hand is valued at " + playerhand.handValueOf());
                System.out.println("");
                System.out.println("the hand is a draw ");
                dealerhand.clearHand();
                playerhand.clearHand();
                return 1;
            } else {
                theView.printString("the dealer has: ");
                theView.printString(dealerhand.toString());
                theView.printString("dealerhand is valued at " + dealerhand.handValueOf());
                theView.printString("the player has: ");
                theView.printString(playerhand.toString());
                theView.printString("player hand is valued at " + playerhand.handValueOf());
                System.out.println("");
                System.out.println("the dealer wins the hand");
                dealerhand.clearHand();
                playerhand.clearHand();
                return 0;
            }
        }

    }
    
    public int playerStandEvaluate(){           // tämä isohko metodi evaluoi tulokset mitä tapahtui kun pelaaja ständäsi alkuperäisillä alkukorteillaan, returnvalue on integer ja muistaakseni 2==voitto 1==tappio 0==tasapeli
        System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        theView.printString("player stands ");
        theView.printString("the dealer has: ");
        theView.printString(dealerhand.toString());
        theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );     
        theView.printString("the player has: ");
        theView.printString(playerhand.toString());
        theView.printString("player hand is valued at "+playerhand.handValueOf() );
        System.out.println("");
        
        if (dealerhand.handValueOf()==21){       // tests if dealer has natural blackjack iin his starting cards
            theView.printString("the dealer has natural blackjack, the dealer wins: ");
            theView.printString(dealerhand.toString());
            dealerhand.clearHand();
            playerhand.clearHand();
            return 0;
        }
        
        if(dealerhand.handValueOf()>=17){
                System.out.println("the dealer stands ");
            }
            else{
                System.out.println("the dealer starts hitting ");
            }
        
   //     theView.printString("the dealer starts hitting ");
        
        while (dealerhand.handValueOf()<17){
         dealerhand.hitPlayerCard();
     //    dealerhand.handValueOf();      //  HUOM kokeile muuttaa tämän loopin ehtolausetta ja lisää vaikkapa break lause tai jotain, HYÖDYNNÄ if lausetta ja RETURN VALUETA. /// ei ole vielä kokeiltu kokeilemme!!!
         if (dealerhand.handValueOf()>=17){
             theView.printString(dealerhand.toString());
         System.out.println("dealerhand is valued at "+dealerhand.handValueOf());
         break;
         }
     theView.printString(dealerhand.toString());
         System.out.println("dealerhand is valued at "+dealerhand.handValueOf());
        }
        
        if (  playerhand.handValueOf()>dealerhand.handValueOf() || dealerhand.handValueOf()>21 ){
                    System.out.println("");
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                    System.out.println(" players wins the hand");
                    dealerhand.clearHand();
                    playerhand.clearHand();
                    return 2;
                }
                else if( playerhand.handValueOf() == dealerhand.handValueOf() ){
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                    System.out.println("the hand is a draw ");
                    dealerhand.clearHand();
                    playerhand.clearHand();
                    return 1;
                }
                else{
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                    System.out.println(" dealer wins the hand ");
                    dealerhand.clearHand();
                    playerhand.clearHand();
                    return 0;
                }
                
    }
    
    public int playerDoubleDownEvaluate(){
        System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("player doubles down ");
        playerhand.hitPlayerCard();
        playerhand.handValueOf();
        theView.printString(playerhand.toString());
        System.out.println("playerhand is valued at "+playerhand.handValueOf());
        System.out.println("");
        
        if (dealerhand.handValueOf()==21){       // tests if dealer has natural blackjack iin his starting cards
            theView.printString("the dealer has natural blackjack, the dealer wins: ");
            theView.printString(dealerhand.toString());
            dealerhand.clearHand();
            playerhand.clearHand();
            return 0;
        }
                if (playerhand.handValueOf() >21){                  // if player busts by going over 21, player  loses the hand
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                    System.out.println("");
                    System.out.println("player busts and dealer wins  the hand ");
                    dealerhand.clearHand();
                    playerhand.clearHand();
                    return 0;
                }
                else{                                           // if the player did not bust by going over 21, we go to this else-branch to evaluate further
                    theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    
                    if (dealerhand.handValueOf() >= 17) {
                        System.out.println("the dealer stands ");
                    } 
                    else {
                        System.out.println("the dealer starts hitting ");
                    }
                  //  System.out.println("the dealer starts hitting ");

                    while (dealerhand.handValueOf() < 17 && playerhand.handValueOf() <= 21) {       // dealer keeps hitting until his handvalue is big enough
                        dealerhand.hitPlayerCard();
//                        dealerhand.handValueOf();      // paluuarvoa ei hyödynnetä tässä metodissa MUTTA JOKAISEN HITTAUKSEN JÄLKEEN jälkeen pitää tarkistaa uusi pelikorttien tilanne ässien arvon uudelleen laskemista varten.
                        if (dealerhand.handValueOf() >= 17) {
                            theView.printString(dealerhand.toString());
                            System.out.println("dealerhand is valued at " + dealerhand.handValueOf());
                            break;
                        }

                        theView.printString(dealerhand.toString());
                        System.out.println("dealerhand is valued at " + dealerhand.handValueOf());
                    }
                    
                    if (dealerhand.handValueOf() > 21 || playerhand.handValueOf()> dealerhand.handValueOf()) {
                        theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                        System.out.println("");
                        System.out.println("player wins the hand, PLAYER WINS DOUBLE MONEY!! FUG YEA! ");
                        dealerhand.clearHand();
                    playerhand.clearHand();
                    return 2;
                    }
                    else if ( playerhand.handValueOf()== dealerhand.handValueOf() ) {
                        theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                        System.out.println("");
                        System.out.println("the hand is a draw ");
                        dealerhand.clearHand();
                    playerhand.clearHand();
                    return 1;
                    }
                    else {
                        theView.printString("the dealer has: ");
                    theView.printString(dealerhand.toString());
                    theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );
                    theView.printString("the player has: ");
                    theView.printString(playerhand.toString());
                    theView.printString("player hand is valued at "+playerhand.handValueOf() );
                        System.out.println("");
                        System.out.println("the dealer wins the hand");
                        dealerhand.clearHand();
                    playerhand.clearHand();
                    return 0;
                    }
            } 
             
        
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  //////////////////////////   main program begins    /////////////////////////
        
        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);        // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(150);
        Bankaccount dealeraccount= new Bankaccount(150);
        Bankaccount blackjackPot= new Bankaccount(0);
        Deck playingdeck= new Deck();                       // normaali pelipakka
        Deck testingdeck= new Deck();
        Hand playerhand= new Hand(testingdeck);                                                                                             // playerhand tuntee playingdeck:in, josta nostetaan kortteja handiin itseensä.
        Hand dealerhand= new Hand(testingdeck);                                                                                                 // myös dealerhand tuntee playingdeck:in josta nostetaan kortteja dealerhandiin itseensä.
      //  controller.simulate();
        boolean weArePlaying=true;                                                                                                           // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;                                                                                                                     // muuttuja betSize on lähinnä vain panoksen koon kysymista ja tallennusta varten. 
          
        
        controller.setHandsToController(playerhand, dealerhand);                                                                                            // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
        testingdeck.createTestingDeck();
        /*   TÄSSÄ OLI PERUSPELIPAKAN KOODIA ÄLÄ POISTA!!!
        final int numberOfDecksUsed= controller.playingDeckGenerateShuffle(playingdeck);                                                                // asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
        
        int boundaryValueForShuffle= (int)( (numberOfDecksUsed*52)/4 );
        int remainingCards;
        */
        
        while(weArePlaying){      // main game loop 
            
            if (!controller.userWillingnessToPlay()){        // asks if user wants to keep playing
                break;
            }
            
            betSize= controller.whatIsBetSize();        // asks the betsize from user, if user places illegal bet, he is kicked out of casino
            System.out.println("betsize was "+betSize);
            if (betSize==0)
                break;
            
            if(!controller.playerBetting(betSize, playeraccount, blackjackPot)){        // betting procedure, if user is out of money, he is kicked out of casino
                break;
            }
            System.out.println("playersaldo is " +playeraccount.getMoneyAmount());
            
            
            controller.dealTwoStartingCards();      // deal starting cards to dealer and player
            
            if (controller.getPlayerHandValue()==21){        // CASE 1.) check if the player has "natural blackjack" 
                System.out.println("player has BLACKJACK ");

                controller.showDealerHand();
                if (controller.getDealerHandValue()==21){       // check if dealer also has "natural blackjack", in this case the game is "draw" 
                    playeraccount.increaseMoneyAmount(blackjackPot.getMoneyAmount() );
                    
                    System.out.println("the game is a draw");
                    System.out.println("playermoney is: " +playeraccount.getMoneyAmount());
                    System.out.println("dealermoney is: " +dealeraccount.getMoneyAmount());
                }
                else {
                    while(  controller.getDealerHandValue()<17   ){   // dealer hits extra cards to get to 21
                        controller.dealerHitCard();
                    }
                    if (controller.getDealerHandValue()==21){
                        System.out.println("the dealer has 21, the game is a draw: ");
                        playeraccount.increaseMoneyAmount(blackjackPot.getMoneyAmount());
                        System.out.println("playermoney is: " +playeraccount.getMoneyAmount());
                        System.out.println("dealermoney is: " +dealeraccount.getMoneyAmount());
                    }
                    
                    else{
                        System.out.println("the dealer busts, the player wins the game");
                        System.out.println("dealerhandvalue was " +controller.getDealerHandValue());
                        blackjackPot.setMoneyAmount(1.5*betSize+betSize);
                        System.out.println("player reward is valued at " + blackjackPot.getMoneyAmount() );
                        if (   !dealeraccount.reduceMoneyAmount( blackjackPot.getMoneyAmount()  )  ){
                            System.out.println("CASINO IS BANKRUPT game ends now, BYE!");
                            break;
                        }
                        else
                            playeraccount.increaseMoneyAmount(blackjackPot.getMoneyAmount());
                            
                        
                        System.out.println("playermoney is: " +playeraccount.getMoneyAmount());
                        System.out.println("dealermoney is: " +dealeraccount.getMoneyAmount());
                    }     // player wins because he has "natural blackjack", and dealer busts
                }
                    
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            /*  ÄLÄ POISTA TÄTÄ KOODIA LIITTYY OIKEAAN `PELIPAKKAAN!!!
            remainingCards = controller.checkRemainingCardsInDeck(playingdeck);
            if (remainingCards <= boundaryValueForShuffle){
                  controller.restartDeckShuffle(numberOfDecksUsed, playingdeck);


            }
            */
            
            
            
        }
        
        

      
        
    }
    
}
