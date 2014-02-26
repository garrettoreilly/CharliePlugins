/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bs.section3;

import charlie.advisor.Advisor;
import charlie.card.Card;
import static charlie.card.Card.Suit.*;
import charlie.card.Hand;
import charlie.card.Hid;
import static charlie.dealer.Seat.*;
import charlie.util.Play;
import static charlie.util.Play.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Garrett, Cat
 */
public class Test01_A2_7 {
	
	public Test01_A2_7() {
	}
	
	@Test
	public void test01()
	{
		Hid hid = new Hid(YOU, 0.0, 0.0);
		Card card1 = new Card(1, CLUBS);
		Card card2 = new Card(5, HEARTS);
		Card upCard = new Card(9, DIAMONDS);
		Hand userHand = new Hand(hid);
		userHand.hit(card1);
		userHand.hit(card2);
		Advisor advisor = new Advisor();
		Play result = advisor.advise(userHand, upCard);
		Play expected = HIT;
		assertEquals(expected, result);
	}
}

