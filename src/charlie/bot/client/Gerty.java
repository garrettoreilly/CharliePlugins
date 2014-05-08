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
import charlie.card.HoleCard;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.util.Play;
import static charlie.util.Play.*;
import charlie.view.AMoneyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Gerty implements IGerty{
    private final Logger LOG = LoggerFactory.getLogger(Gerty.class);
   
    protected Courier courier;
    protected AMoneyManager moneyManager;
    protected final static int minBet = 5;
    protected int betAmount = 5;
    protected int lastBet = 0;
    protected Hid hid;
    protected double numberOfDecks;
    protected int runningCount = 0;
    protected double trueCount;
    protected Hand botHand;
    protected Card upCard;
    protected int pushes;
    protected int losses;
    protected int busts;
    protected int wins;
    protected int charlies;
    protected int blackjacks;
    protected int gameCount = 0;
    protected double avgBet = 0;
    protected DecimalFormat df = new DecimalFormat("#.##");
    protected int maxBet = 0;
    protected Font font = new Font("Arial", Font.PLAIN, 18);
    protected Color textColor = Color.WHITE;
    
    @Override
     public void go( )
     {
         if(this.gameCount != 0) {
            this.betAmount = ((int) Math.max(1.0, 1.0 + this.trueCount)) * this.minBet;
            LOG.info("runningCount = " + this.runningCount);
            LOG.info("trueCount = " + this.trueCount);
            LOG.info("betAmount = " + this.betAmount);
         }
         if(this.lastBet > this.betAmount) {
             this.moneyManager.clearBet();
             this.lastBet = 0;
             try{
                 Thread.sleep(500);
             }
             catch (InterruptedException ex){
                 LOG.info("Thread error in sleep.");
             }
         }
         for(int i = this.lastBet; i < this.betAmount; i += this.minBet) {
                this.moneyManager.upBet(this.minBet);
                this.lastBet += this.minBet;
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex){
                }
         }
         this.hid = this.courier.bet(this.lastBet, 0);
         this.botHand = new Hand(this.hid);
         this.upCard = null;
         
         try {
                Thread.sleep(3000);
            }
        catch (InterruptedException ex) {
        }
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
       g.setFont(font);
       g.setColor(textColor);
       g.drawString("Pushes: " + pushes, 5, 390);
       g.drawString("Losses: " + losses, 5, 370);
       g.drawString("Breaks: " + busts, 5, 350);
       g.drawString("Wins: " + wins, 5, 330);
       g.drawString("Charlies: " + charlies, 5, 310);
       g.drawString("Blackjacks: " + blackjacks, 5, 290);
       g.drawString("Mean bet: " + df.format(avgBet), 5, 270);
       g.drawString("Max bet: " + maxBet, 5, 250);
       g.drawString("Minutes: ", 5, 230);
       g.drawString("Games: " + gameCount, 5, 210);
       g.drawString("True count: ", 5, 190);
       g.drawString("Running count: ", 5, 170);
       g.drawString("Shoe size: ", 5, 150);
       g.drawString("Counting system: Hi-Lo", 5, 130);
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
        this.numberOfDecks = shoeSize / 52.0;
        this.trueCount = runningCount / numberOfDecks;
        this.botHand = new Hand(this.hid);
        this.gameCount++;
	avgBet = (avgBet * (gameCount - 1) + betAmount) / gameCount;
	if (betAmount > maxBet) {
		maxBet = betAmount;
	}
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
        //LOG.info("dis card's value " + card.value());
        int currentCardValue = card.getRank();
        if(currentCardValue > 9 || currentCardValue == 1) {
            --runningCount;
        }
        else if(currentCardValue < 7) {
            ++runningCount;
        }
        
        if((hid.getSeat() == this.hid.getSeat())) {
            LOG.info("Card added!");
            botHand.hit(card);
        }
        else if(!(hid.getSeat().equals(Seat.YOU)) && !(card instanceof HoleCard)) {
            LOG.info("Upcard added!");
            this.upCard = new Card(card);
        }
        
        if((hid.getSeat().equals(Seat.YOU)) && (this.botHand.size() > 2) && !(this.botHand.isBroke())) {
            play(hid);
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
        busts++;
    }
    
    /**
     * Tells player the hand won.
     * @param hid Hand id
     */  
     @Override
    public void win(Hid hid)
    {
        wins++;
    }
    
    /**
     * Tells player the hand won with blackjack.
     * @param hid Hand id
     */   
    @Override
    public void blackjack(Hid hid)
    {
        blackjacks++;
    }
    
     /**
     * Tells player the hand won with Charlie.
     * @param hid Hand id
     */    
    @Override
    public void charlie(Hid hid)
    {
        charlies++;
    }
    
    /**
     * Tells player the hand lost to dealer.
     * @param hid Hand id
     */    
    @Override
    public void lose(Hid hid)
    {
        losses++;
    }
    
    /**
     * Tells player the hand pushed.
     * @param hid Hand id
     */    
    @Override
    public void push(Hid hid)
    {
        pushes++;
    }
    
    /**
     * Tells player the hand pushed.
     */    
    @Override
    public void shuffling()
    {
        this.runningCount = 0;
        this.trueCount = 0;
    }
    
    /**
     * Tells player to start playing hand.
     * @param hid Hand id
     */  
    @Override
    public void play(Hid hid)
    {
         try {
                Thread.sleep(3000);
            }
        catch (InterruptedException ex) {
        }
        if((hid.getSeat().equals(Seat.YOU)) && (this.botHand.size() >= 2) && !(this.botHand.isBroke())) {
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
                //play(hid);
            }
            else if(advice == DOUBLE_DOWN) {
                courier.dubble(hid);
                //play(hid);
            }
        }
    }
}
