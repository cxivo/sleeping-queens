package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class HandTest {
    private DrawingAndTrashPile pile;
    private Hand hand;

    void setUp() {
        pile = new DrawingAndTrashPile(Game.getFullCardList(), new Random(0));
    }
    
    @Test
    public void testCardPicking() {
    	setUp();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < Game.CARDS_PER_PLAYER; i++) {
            cards.add(pile.getTopCard());
        }

        hand = new Hand(cards, pile);
        
        List<Integer> cardPositions = new ArrayList<>();
        for (int i = 0; i < Game.CARDS_PER_PLAYER; i++) {
            cardPositions.add(i);
        }

        List<Card> pickedCards = hand.pickCards(cardPositions);
        assertEquals(cards, pickedCards);
    }

    @Test
    public void testCardHaving() {
        setUp();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardType.King, 0));
        cards.add(new Card(CardType.SleepingPotion, 0));


        hand = new Hand(cards, pile);
        assertEquals(hand.hasCardOfType(CardType.King), 0);
        assertEquals(hand.hasCardOfType(CardType.SleepingPotion), 1);
        assertEquals(hand.hasCardOfType(CardType.MagicWand), -1);
    }

    @Test
    public void testRedrawing() {
        setUp();

        List<Card> cardsInHand = new ArrayList<>();

        cardsInHand.add(new Card(CardType.Number, 1));
        cardsInHand.add(new Card(CardType.Number, 2));
        cardsInHand.add(new Card(CardType.Number, 6));
        cardsInHand.add(new Card(CardType.Number, 5));
        cardsInHand.add(new Card(CardType.Number, 3));
        List<Card> backupCards = new ArrayList<>(cardsInHand);

        Hand hand = new Hand(cardsInHand, pile);

        assertEquals(backupCards, hand.getCards());
    
        hand.pickCards(new ArrayList<>());
        hand.removePickedCardsAndRedraw();

        assertEquals(backupCards, hand.getCards());

        assertEquals(hand.pickCards(Collections.singletonList(2)).get(0).getValue(), 6);

        for (int i = 0; i < 5; i++) {
            System.out.println(hand.getCards().get(i).getValue());
        }
        hand.removePickedCardsAndRedraw();

        assertEquals(backupCards.get(0), hand.getCards().get(0));
        assertEquals(backupCards.get(1), hand.getCards().get(1));
        assertEquals(backupCards.get(3), hand.getCards().get(2));
        assertEquals(backupCards.get(4), hand.getCards().get(3));
    }
}
