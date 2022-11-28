package sk.uniba.fmph.dcs;

import java.util.ArrayList;
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
    public void test_determinism() {
    	setUp();
        for (int i = 0; i < 62; i++) {
            assertEquals(pile1.getTopCard().getType(), pile2.getTopCard().getType());
        }
    }
        
    @Test
    public void test_pile_gives_correct_number_of_cards() {
    	setUp();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 62; i++) {
            List<Card> newCards = pile1.discardAndDraw(cards);
            assertEquals(i, newCards.size());
            cards.add(pile1.getTopCard());
        }


    }
 }
        
