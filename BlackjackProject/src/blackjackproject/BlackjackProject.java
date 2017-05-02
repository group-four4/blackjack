
package blackjackproject;


public class BlackjackProject {     // <----------CONTROLLERIN METODIT LÖYTYY TÄÄLTÄ (Controller class = BlackjackProject class)
    
    private final View theView;
    private  Hand playerhand;
    private Hand dealerhand;
    
    public BlackjackProject(View theView){      //CONTROLLERIN CONSTRUCTOR controller tuntee view:in
        this.theView= theView;
    }
    
    public boolean userWillingnessToPlay(){
        return theView.askIfKeepPlaying();
    }
    
    public double whatIsBetSize(){
        return theView.askBetSize();
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
    
    public boolean canPlayerSplit(){
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
        theView.printString("player hand is valued at "+playerhand.handValueOf() );         // tämä oli tuore lisäys koodiin TÄTÄ EI OLE TESTATTU MERKITTÄVÄSTI VIELÄ
    }
    public void showDealerHand(){
        theView.printString("the dealer has: ");
        theView.printString(dealerhand.toString());
        theView.printString("dealerhand is valued at " + dealerhand.handValueOf() );        // tämä oli tuore lisäys koodiin TÄTÄ EI OLE TESTATTU MERKITTÄVÄSTI VIELÄ
    }
    public void dealerHitCard(){
         dealerhand.hitPlayerCard();
         dealerhand.handValueOf();      // paluuarvoa ei hyödynnetä tässä metodissa MUTTA JOKAISEN HITTAUKSEN JÄLKEEN jälkeen pitää tarkistaa uusi pelikorttien tilanne ässien arvon uudelleen laskemista varten.
         theView.printString(dealerhand.toString());
         System.out.println("dealerhand is valued at "+dealerhand.handValueOf());
    }
    
    public void playerHitCard(){        // metodia on editoidu MUTTA EI VIELÄ testattu että laskeeko se uudelleen ässien arvot oikein!!
        playerhand.hitPlayerCard();
        playerhand.handValueOf();
        theView.printString(playerhand.toString());
        System.out.println("playerhand is valued at "+playerhand.handValueOf());
    }
    
    public void clearBothHands(){
        dealerhand.clearHand();
        playerhand.clearHand();
    }
    
    public int checkRemainingCardsInDeck(Deck playingdeck){
        int remainingcards = playingdeck.checkCardsAmountInDeck();
        return remainingcards;
    }
    
    public void restartDeckShuffle(int amountOfDecks, Deck playingdeck){
        theView.printString("There aren't enough cards in the shoe, we will shuffle the deck(s) again now. ");
        int number= amountOfDecks;
        playingdeck.createPlayingDeck(number);
        playingdeck.shuffleArrayList();
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
        
        boolean weArePlaying=true;                                                                                                           // weArePlaying boolean muuttuja oli aluksi sitä varten että yritin koodata loopista poistumista sen avulla, mutta eihän se sitten toiminut ollenkaan. Lopulta oikea lopputulos ja haluttu ohjelmointiratkaisu saatiin break lauseita käyttämällä.
        double betSize;                                                                                                                     // muuttuja betSize on lähinnä vain panoksen koon kysymista ja tallennusta varten. 
          
        
        controller.setHandsToController(playerhand, dealerhand);                                                                                            // controller tuntee playerhandin ja dealerhandin asetusmetodilla. controllerista löytyy instanssimuuttujat erikseen playerhand ja dealerhand, ja voidaan käyttä eri metodeja controlelrissa.
        testingdeck.createTestingDeck();
        /*   TÄSSÄ OLI PERUSPELIPAKAN KOODIA ÄLÄ POISTA
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
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            /*  ÄLÄ POISTA TÄTÄ KOODIA LIITTYY OIKEAAN `PELIPAKKAAN
            remainingCards = controller.checkRemainingCardsInDeck(playingdeck);
            if (remainingCards <= boundaryValueForShuffle){
                  controller.restartDeckShuffle(numberOfDecksUsed, playingdeck);


            }
            */
            
            
            
        }
        
        

      
        
    }
    
}
