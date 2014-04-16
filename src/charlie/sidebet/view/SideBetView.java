/*
 Copyright (c) 2014 Ron Coleman


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

package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.util.Constant;
import charlie.view.AMoneyManager;
import charlie.view.sprite.AtStakeSprite;
import charlie.view.sprite.Chip;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the side bet view
 * @author Ron Coleman, Ph.D.
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);
    
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    protected AtStakeSprite sideWager = new AtStakeSprite(375,175,0);
    
    protected List<Chip> chips = new ArrayList<>();
    protected Random ran = new Random();
    protected Integer[] amounts = { 100, 25, 5 };
    protected final static String[] UP_FILES =
        {"chip-100-1.png","chip-25-1.png","chip-5-1.png"};
    ImageIcon icon = new ImageIcon(Constant.DIR_IMGS+UP_FILES[0]);
    Image img = icon.getImage();
    private final int width = img.getWidth(null);
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);   

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;

    public SideBetView() {
        LOG.info("side bet view constructed");
    }
    
    /**
     * Sets the money manager.
     * @param moneyManager 
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }
    
    /**
     * Registers a click for the side bet.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;
        
        // Test if any chip button has been pressed.
        for(int i=0; i < buttons.size(); i++) {
            ChipButton button = buttons.get(i);
            if(button.isReady() && button.isPressed(x, y)) {
                int n = chips.size();
                
                int placeX = X + n * width/3 + ran.nextInt(10) + 25;
                
                int placeY = Y + ran.nextInt(5) - 25;
                
                Chip chip = new Chip(button.getImage(),placeX,placeY,amounts[i]);
                
                chips.add(chip);
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
		SoundFactory.play(Effect.CHIPS_IN);
            } 
        }
        
        if(this.sideWager.isPressed(x, y)) {
            amt = 0;
            chips.clear();
            LOG.info("B. side bet amount cleared");
	    SoundFactory.play(Effect.CHIPS_OUT);
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        
        if(bet == 0)
            return;

        LOG.info("side bet outcome = "+bet);
        
        // Update the bankroll
        moneyManager.increase(bet);
        
        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED); 
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);
        
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        String text = amt + "";
        FontMetrics fm = g.getFontMetrics(font);
        int x = X - fm.charsWidth(text.toCharArray(), 0, text.length()) / 2;
        int y = Y + fm.getHeight() / 4;
        
        g.drawString(amt+"", x, y);
        g.setColor(Color.BLACK);
	g.drawString("SUPER 7 pays 3:1", 445, 150);
	g.drawString("ROYAL MATCH pays 25:1", 445, 170);
	g.drawString("EXACTLY 13 pays 1:1", 445, 190);
        
         for(int i=0; i < chips.size(); i++) {
            Chip chip = chips.get(i);
            chip.render(g);
        }
    }
}
