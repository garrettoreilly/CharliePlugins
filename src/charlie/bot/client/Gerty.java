/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bot.client;

import charlie.actor.Courier;
import charlie.advisor.BasicStrategy;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.plugin.IGerty;
import charlie.util.Play;
import static charlie.util.Play.*;
import charlie.view.AMoneyManager;
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Gerty implements IGerty{
    protected Courier courier;
    protected AMoneyManager moneyManager;
    protected int betAmount = 5;
    protected int currentBet = 0;
    protected Hid hid;
    protected double numberOfDecks;
    protected int runningCount;
    protected double trueCount;
    protected Hand botHand;
    protected Card upCard;
    
    @Override
     public void go( )
     {
         if(currentBet > betAmount) {
             moneyManager.clearBet();
             currentBet = 0;
         }
         for(int i = currentBet; i < betAmount; i += 5) {
                moneyManager.upBet(5);
                currentBet += 5;
         }
         this.hid = courier.bet(currentBet, 0);
         botHand = new Hand(this.hid);
     }
     
     /**
     * Sets the courier actor through which we communicate with the controller.
     * @param courier Courier
     */
    @Override
    public void setCourier(Courier courier)
    {
        this.courier = courier;
    }
    
     /**
     * Sets the money manager for managing bets.
     * @param moneyManager Money manager
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager)
    {
        this.moneyManager = moneyManager;
    }
    
    /**
     * Updates the bot.
     */
    @Override
    public void update()
    {
        
    }
    
    /**
     * Renders the bot.
     * @param g Graphics context.
     */
    @Override
    public void render(Graphics2D g)
    {
        
    }
    
    /**
     * Starts game with list of hand ids and shoe size.
     * The number of decks is shoeSize / 52.
     * @param hids Hand ids
     * @param shoeSize Starting shoe size
     */
    @Override
    public void startGame(List<Hid> hids,int shoeSize)
    {
        numberOfDecks = shoeSize / 52.0;
    }
   
    /**
     * Ends a game with shoe size.
     * @param shoeSize Shoe size
     */
    @Override
    public void endGame(int shoeSize)
    {
        numberOfDecks = shoeSize / 52.0;
    }
    
    /**
     * Deals a card to player.
     * All players receive all cards which is useful for card counting.
     * @param hid Hand id which might not necessarily belong to player.
     * @param card Card being dealt
     * @param values Hand values, literal and soft
     */
    @Override
    public void deal(Hid hid, Card card, int[] values)
    {
        int currentCardValue = card.getRank();
        if(currentCardValue > 9 || currentCardValue == 1) {
            --runningCount;
        }
        else if(currentCardValue < 7) {
            ++runningCount;
        }
        
        if(hid.equals(this.hid)) {
            botHand.hit(card);
        }
        else {
            upCard = card;
        }
    }
    
    /**
     * Offers player chance to buy insurance.
     */
    @Override
    public void insure()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Tells player the hand is broke.
     * @param hid Hand id
     */
    @Override
    public void bust(Hid hid)
    {
        
    }
    
    /**
     * Tells player the hand won.
     * @param hid Hand id
     */  
     @Override
    public void win(Hid hid)
    {
        
    }
    
    /**
     * Tells player the hand won with blackjack.
     * @param hid Hand id
     */   
    @Override
    public void blackjack(Hid hid)
    {
        
    }
    
     /**
     * Tells player the hand won with Charlie.
     * @param hid Hand id
     */    
    @Override
    public void charlie(Hid hid)
    {
        
    }
    
    /**
     * Tells player the hand lost to dealer.
     * @param hid Hand id
     */    
    @Override
    public void lose(Hid hid)
    {
        
    }
    
    /**
     * Tells player the hand pushed.
     * @param hid Hand id
     */    
    @Override
    public void push(Hid hid)
    {
        
    }
    
    /**
     * Tells player the hand pushed.
     */    
    @Override
    public void shuffling()
    {
        
    }
    
    /**
     * Tells player to start playing hand.
     * @param hid Hand id
     */  
    @Override
    public void play(Hid hid)
    {
        Play advice;
        advice = BasicStrategy.getPlay(this.botHand, this.upCard);
        if(this.botHand.size() != 2 && advice == DOUBLE_DOWN) {
            advice = HIT;
        }
        else if(advice == SPLIT) {
            int handValue = this.botHand.getValue();
            if(handValue >= 17) {
                advice = STAY;
            }
            else if(handValue == 11) {
                advice = DOUBLE_DOWN;
            }
            else if(handValue <= 10) {
                advice = HIT;
            }
        }
        
        if(advice == STAY) {
            courier.stay(hid);
        }
        else if(advice == HIT) {
            courier.hit(hid);
        }
        else if(advice == DOUBLE_DOWN) {
            courier.dubble(hid);
        }
    }
}
