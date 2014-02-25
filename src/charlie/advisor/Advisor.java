/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import static charlie.util.Play.HIT;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Advisor implements IAdvisor {
    @Override
    public Play advise(Hand myHand,Card upCard)
    {
        return HIT;
    }
}
