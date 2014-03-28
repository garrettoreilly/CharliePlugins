/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bot.server;

import static charlie.advisor.BasicStrategy.getPlay;
import charlie.card.Card;
import charlie.util.Play;
import charlie.card.Hand;
import charlie.dealer.Dealer;
import static charlie.util.Play.*;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Responder implements Runnable {
    
    protected B9 bot;
    protected Hand botHand;
    protected Dealer dealer;
    protected Hand dealerHand;
    
    public Responder (B9 bot, Hand myHand, Dealer dealer, Hand dealerHand) {
        this.bot = bot;
        this.botHand = myHand;
        this.dealer = dealer;
        this.dealerHand = dealerHand;
    }
    
    @Override
    public void run()
    {
        Card upCard = dealerHand.getCard(1);
        Play advice;
        advice = getPlay(this.botHand, upCard);
        if(this.botHand.size() != 2 && advice == DOUBLE_DOWN) {
            advice = HIT;
        }
    }
}