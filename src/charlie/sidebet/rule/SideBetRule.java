/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import charlie.util.Constant;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the side bet rule for Super 7.
 * @author Ron Coleman
 */
public class SideBetRule implements ISideBetRule {
    private final Logger LOG = LoggerFactory.getLogger(SideBetRule.class);
    
    private final Double PAYOFF_SUPER7 = 3.0;
    private final Double PAYOFF_EXACTLY13 = 1.0;
    private final Double PAYOFF_ROYALMATCH = 25.0;

    /**
     * Apply rule to the hand and return the payout if the rule matches
     * and the negative bet if the rule does not match.
     * @param hand Hand to analyze.
     * @return 
     */
    @Override
    public double apply(Hand hand) {

        
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = "+bet);
        
        if(bet == 0)
            return 0.0;
        
        LOG.info("side bet rule applying hand = "+hand);
        
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        int[] handValues = hand.getValues();
        int handValue = handValues[Constant.HAND_LITERAL_VALUE];
 
        if(card1.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches"); //check if Super 7, removes the conflict of Super 7 and Exactly 13 by having this first.
            return bet * PAYOFF_SUPER7;
        }
        else if(handValue == 13) {
            LOG.info("side bet EXACTLY 13 matches");
            return bet * PAYOFF_EXACTLY13;
        }
        else if(((card1.value() == Card.KING && card2.value() == Card.QUEEN) || (card1.value() == Card.QUEEN && card2.value() == Card.KING)) && (card1.getSuit() == card2.getSuit())) {
            LOG.info("side bet ROYAL MATCH matches");
            return bet * PAYOFF_ROYALMATCH;
        }
        
        LOG.info("side bet rule no match");
        
        return -bet;
    }
}
