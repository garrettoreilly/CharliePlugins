/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.sidebet.test;

import charlie.card.Card;
import charlie.card.Shoe;

/**
 *
 * @author Garrett
 */
public class testShoe extends Shoe{
    @Override
    public void init() {
        cards.clear();
	//First hand
        cards.add(new Card(7, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(9, Card.Suit.SPADES));
        cards.add(new Card(9, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.HEARTS));
	//Second hand
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(9, Card.Suit.CLUBS)); 
        cards.add(new Card(8, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.SPADES));
	//Third hand
        cards.add(new Card(9, Card.Suit.HEARTS));
        cards.add(new Card(Card.KING, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.SPADES));
        cards.add(new Card(8, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
	//Fourth hand
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(Card.KING, Card.Suit.HEARTS));
        cards.add(new Card(9, Card.Suit.SPADES));
        cards.add(new Card(10, Card.Suit.DIAMONDS));
        cards.add(new Card(3, Card.Suit.CLUBS));
	//Fifth hand
        cards.add(new Card(9, Card.Suit.HEARTS));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.DIAMONDS));
        cards.add(new Card(10, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.SPADES));
	//Sixth hand
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(Card.KING, Card.Suit.SPADES));
        cards.add(new Card(Card.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.DIAMONDS));
	//Seventh hand
        cards.add(new Card(Card.KING, Card.Suit.SPADES));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(Card.QUEEN, Card.Suit.DIAMONDS));
        cards.add(new Card(8, Card.Suit.HEARTS));
	//Eighth hand
        cards.add(new Card(8, Card.Suit.HEARTS));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(6, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING, Card.Suit.HEARTS));
	//Ninth hand
        cards.add(new Card(7, Card.Suit.SPADES));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.KING, Card.Suit.HEARTS));
	//Tenth hand
        cards.add(new Card(6, Card.Suit.SPADES));
        cards.add(new Card(Card.KING, Card.Suit.DIAMONDS));
        cards.add(new Card(8, Card.Suit.SPADES));
        cards.add(new Card(6, Card.Suit.HEARTS));
        cards.add(new Card(Card.KING, Card.Suit.CLUBS));


    }    
    
    @Override
    public boolean shuffleNeeded() {
        return false;
    }
}