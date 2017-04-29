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
public class BlackjackProject {     // <----------CONTROLLERIN METODIT LÖYTYY TÄÄLTÄ (Controller class = BlackjackProject class)
    
    private final View theView;
    
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
    
    public void playingDeckGenerateShuffle(Deck thePlayingDeck){
        int numberOfDecks= theView.askHowManyDecksToUse();
        thePlayingDeck.createPlayingDeck(numberOfDecks);
        thePlayingDeck.shuffleArrayList();
    }
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        View view= new View();
        BlackjackProject controller= new BlackjackProject(view);        // controlleri tuntee view:in ja tämä tulee olla constructorissa
        Bankaccount playeraccount= new Bankaccount(100);
        Bankaccount dealeraccount= new Bankaccount(100);
        Bankaccount blackjackPot= new Bankaccount(0);
        Deck playingdeck= new Deck();
        Hand playerhand= new Hand(playingdeck);         // playerhand tuntee playingdeck:in, josta nostetaan kortteja handiin itseensä.
        Hand dealerhand= new Hand(playingdeck);         // myös dealerhand tuntee playingdeck:ing josta nostetaan kortteja dealerhandiin itseensä.
        
        boolean weArePlaying=true;
        double betSize;
        
        while(weArePlaying){      // <-----------main game loop
            
            if (!controller.userWillingnessToPlay()){        // asks if user wants to keep playing
                break;
            }
            
            betSize= controller.whatIsBetSize();        // asks the betsize from user, if user places illegal bet, he is kicked out of casino
            if (betSize==0)
                break;
            
            if(!controller.playerBetting(betSize, playeraccount, blackjackPot)){        // betting procedure, if user is out of money, he is kicked out of casino
                break;
            }
            controller.playingDeckGenerateShuffle(playingdeck);     // asks how many decks to use for playing deck, and also populates playing deck with cards, and also shuffles it once
            System.out.println(playingdeck);
            
            

            
            
            
        }
        
        

      
        
    }
    
}
