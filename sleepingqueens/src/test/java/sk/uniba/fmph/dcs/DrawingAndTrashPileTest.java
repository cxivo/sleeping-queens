package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DrawingAndTrashPileTest  {
    private DrawingAndTrashPile pile1;
    private DrawingAndTrashPile pile2;

    void setUp() {
        pile1 = new DrawingAndTrashPile(Game.getFullCardList(), new Random(0));
        pile2 = new DrawingAndTrashPile(Game.getFullCardList(), new Random(0));
    }
    
    @Test
    public void testDeterminism() {
    	setUp();
        for (int i = 0; i < 62; i++) {
            assertEquals(pile1.getTopCard().getType(), pile2.getTopCard().getType());
        }
    }
        
    @Test
    public void testPileGivesCorrectNumberOfCards() {
    	setUp();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 62; i++) {
            List<Card> newCards = pile1.discardAndDraw(cards);
            assertEquals(i, newCards.size());
            cards.add(pile1.getTopCard());
        }
    }

    @Test
    public void testReshuffleOrder() {
        
        class NonShufflingPile extends DrawingAndTrashPile {
            public NonShufflingPile(List<Card> cards) {
                super(cards, new Random(0));
            }

            @Override
            protected void shuffleDrawingPile() {
                // don't shuffle
            }
        }

        DrawingAndTrashPile pile = new NonShufflingPile(Game.getFullCardList());
        List<Card> cards1 = new ArrayList<>();

        for (int i = 0; i < 62; i++) {
            cards1.add(pile.getTopCard());
        }
        for (int i = 0; i < 62; i++) {
            pile.throwCard(cards1.get(i));
        }

        List<Card> cards2 = new ArrayList<>();

        for (int i = 0; i < 62; i++) {
            cards2.add(pile.getTopCard());
        }

        Collections.reverse(cards2);

        assertEquals(cards1, cards2);

    }
 }
        
