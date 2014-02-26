/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Constant;
import charlie.util.Play;
import static charlie.util.Play.*;

/**
 *
 * @author Thomas Wojnar, Garrett Oreilly
 */
public class BasicStrategy {
    public static Play getPlay(Hand inHand, Card inCard)
    {   
        int[] handValues = inHand.getValues(); //gets both of the hand values.
        int hardHandValue = handValues[Constant.HAND_LITERAL_VALUE]; //gets hard hand value.
        Play returnedPlay;
        
        if (inHand.getCard(0).value() == inHand.getCard(1).value() && inHand.size() == 2) { //check if two cards that are the same.
            returnedPlay = sameCard(inHand, inCard);
        }
        else if (handValues[0] != handValues[1] && inHand.size() == 2) { //check if the hand values are the same, if not, there is an ace and another card.
            returnedPlay = aceHand(inHand, inCard);
        }
        else if (hardHandValue >= 5 && hardHandValue <= 11) { //low range range.
            returnedPlay = lowerHard(inHand, inCard);
        }
        else {
            returnedPlay = higherHard(inHand, inCard); //high range hand.
        }
        return returnedPlay;
    }
    
    private static Play sameCard(Hand inHand, Card inCard)
    {
        int cardValue = inHand.getCard(0).value();
        int upCardValue = inCard.value();
        if (upCardValue == 1) { //for simplicity sake.
            upCardValue = 11;
        }
        Play returnedPlay = SPLIT; //most common outcome. Now for the logic of the others, in blocks.
        if (cardValue == 5 && (upCardValue >= 2 && upCardValue <= 9)) { //this first to make the logic easier for the large block of HITS on the right side.
            returnedPlay = DOUBLE_DOWN;
        }
        else if ((cardValue >= 2 && cardValue <= 7) && (upCardValue >= 8 && upCardValue <= 11)) {
            returnedPlay = HIT;
        }
        else if (cardValue == 4 && ((upCardValue >= 2 && upCardValue <= 4) || upCardValue == 7)) {
            returnedPlay = HIT;
        }
        else if (cardValue == 6 && upCardValue == 7) {
            returnedPlay = HIT;
        }
        else if (cardValue == 9 && (upCardValue == 7 || upCardValue == 10 || upCardValue == 11)) {
            returnedPlay = STAY;
        }
        else if (cardValue == 10) {
            returnedPlay = STAY;
        }
        return returnedPlay;
    }
    
    private static Play aceHand(Hand inHand, Card inCard)
    {
        int cardValue1 = inHand.getCard(0).value();
        int cardValue2 = inHand.getCard(1).value();
        int cardValueToUse;
        if (cardValue1 == 1) { //figure out what is the value that is not the Ace.
            cardValueToUse = cardValue2;
        } else {
            cardValueToUse = cardValue1;
        }
        int upCardValue = inCard.value();
        if (upCardValue == 1) {
            upCardValue = 11;
        }
        Play returnedPlay = HIT;
        if ((cardValueToUse == 2 || cardValueToUse == 3) && (upCardValue == 5 || upCardValue == 6)) {
            returnedPlay = DOUBLE_DOWN;
        }
        else if ((cardValueToUse == 4 || cardValueToUse == 5) && (upCardValue >= 4 && upCardValue <= 6)) {
            returnedPlay = DOUBLE_DOWN;
        }
        else if ((cardValueToUse == 6 || cardValueToUse == 7) && (upCardValue >= 3 && upCardValue <= 6)) {
            returnedPlay = DOUBLE_DOWN;
        }
        else if ((cardValueToUse == 7)  && (upCardValue == 2 || upCardValue == 7 || upCardValue == 8)) {
            returnedPlay = STAY;
        }
        else if (cardValueToUse >= 8 && cardValueToUse <= 10) {
            returnedPlay = STAY;
        }
        return returnedPlay;
    }
    
    private static Play lowerHard(Hand inHand, Card inCard)
    {
        int handValue = inHand.getValues()[Constant.HAND_LITERAL_VALUE];
        int upCardValue = inCard.value();
        if (upCardValue == 1) {
            upCardValue = 11;
        }
        Play returnedPlay = HIT;
        if (handValue == 9 && (upCardValue >= 3 && upCardValue <= 6)) {
            returnedPlay = DOUBLE_DOWN;
        }
        else if (handValue == 10 && (upCardValue >= 2 && upCardValue <= 9)) {
            returnedPlay = DOUBLE_DOWN;
        }
        else if (handValue == 11 && (upCardValue >= 2 && upCardValue <= 10)) {
            returnedPlay = DOUBLE_DOWN;
        }
        return returnedPlay;
    }
    
    private static Play higherHard(Hand inHand, Card inCard)
    {
        int handValue = inHand.getValues()[Constant.HAND_LITERAL_VALUE];
        int upCardValue = inCard.value();
        if (upCardValue == 1) {
            upCardValue = 11;
        }
        Play returnedPlay = STAY;
        if (handValue == 12 && (upCardValue == 2 || upCardValue == 3)) {
            returnedPlay = HIT;
        }
        else if ((handValue >= 12 && handValue <= 16) && (upCardValue >= 7 && upCardValue <= 11)) {
            returnedPlay = HIT;
        }
        return returnedPlay;
    }
    
}
