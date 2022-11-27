package sk.uniba.fmph.dcs;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawingAndTrashPile {
    private Stack<Card> drawingPile;
    private Stack<Card> trashPile;
    private List<Card> cardsDiscardedThisTurn;
    private Random random;

    public DrawingAndTrashPile(List<Card> cards, Random random) {
        this.random = random;

        Collections.shuffle(drawingPile, random); 
        drawingPile = new Stack<Card>();
        drawingPile.addAll(cards);

        trashPile = new Stack<Card>();
        cardsDiscardedThisTurn = new ArrayList<>();
    }
        
    public List<Card> discardAndDraw(List<Card> toDiscard) {
        for (Card card : toDiscard) {
            trashPile.add(card);
            cardsDiscardedThisTurn.add(card);
        }

        List<Card> toDraw = new ArrayList<>();

        for (int i = 0; i < toDiscard.size(); i++) {
            toDraw.add(getTopCard());
        }
        
        return toDraw;
    }

    // no idea what this method is for, but it's in the design outline
    public List<Card> getCardsDiscardedThisTurn() {
        return cardsDiscardedThisTurn;
    }

    public void newTurn() {
        cardsDiscardedThisTurn.clear();
    }

    protected Card getTopCard() {
    	if (drawingPile.isEmpty()) {
            drawingPile.addAll(trashPile);
            trashPile.clear();
            Collections.shuffle(drawingPile, random); 
        }
        return drawingPile.pop();
    }
}
        
        
