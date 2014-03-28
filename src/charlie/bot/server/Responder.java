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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Responder implements Runnable {
    
    private final Logger LOG = LoggerFactory.getLogger(Responder.class);
    protected B9 bot;
    protected Hand botHand;
    protected Dealer dealer;
    protected Hand dealerHand;
    
    public Responder (B9 bot, Hand myHand, Dealer dealer, Hand dealerHand) 
    {
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
        //LOG.info("meow");
        advice = getPlay(this.botHand, upCard);
        LOG.info("got basic advice");
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
        LOG.info("modified advice");
        /*
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            LOG.info("invalid thread-thingy meow");
        }
        */
        if(advice == HIT) {
            this.dealer.hit(bot, this.botHand.getHid());
        }
        else if(advice == STAY) {
            this.dealer.stay(bot, this.botHand.getHid());
        }
        else if(advice == DOUBLE_DOWN) {
            this.dealer.doubleDown(bot, this.botHand.getHid());
        }
        else {
            LOG.info("invalid play meow");
        }
        LOG.info("send, I think");
    }
}