/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bot.test;

import java.util.Random;

/**
 * This class implements a shoe.
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class Shoe01 extends charlie.card.Shoe {   
    @Override
    public void init() {
        super.ran = new Random(System.currentTimeMillis());
        
        super.numDecks = 1;
        
        super.load();
        
        super.shuffle();
    }
}