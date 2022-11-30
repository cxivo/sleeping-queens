package sk.uniba.fmph.dcs;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for representing the drawing pile and the trash pile of the game.
 * Contains the logic for drawing and shuffling cards
 */
public class DrawingAndTrashPile {
    private Stack<Card> drawingPile;
    private Stack<Card> trashPile;
    private List<Card> cardsDiscardedThisTurn;
    private Random random;
    private EmptyPileStrategy emptyPileStrategy;

    public DrawingAndTrashPile(List<Card> cards, Random random) {
        this.random = random;
        this.emptyPileStrategy = new ShuffleBeforeDrawingStrategy(this);

        drawingPile = new Stack<Card>();
        drawingPile.addAll(cards);

        trashPile = new Stack<Card>();
        cardsDiscardedThisTurn = new ArrayList<>();

        shuffleTrashPileToDrawingPile();
    }
        
    /** 
     * Uses the strategy to deal with this
     */
    public List<Card> discardAndDraw(List<Card> toDiscard) {
        return emptyPileStrategy.discardAndDraw(toDiscard);
    }

    // no idea what this method is for, but it's in the design outline
    public List<Card> getCardsDiscardedThisTurn() {
        return cardsDiscardedThisTurn;
    }

    /**
     * Call this method when the next player is on turn
     */
    public void newTurn() {
        cardsDiscardedThisTurn.clear();
    }

    protected Card getTopCard() {
    	if (drawingPile.isEmpty()) {
            shuffleTrashPileToDrawingPile();
        }
        return drawingPile.pop();
    }

    protected void throwCard(Card card) {
        trashPile.add(card);
        cardsDiscardedThisTurn.add(card);
    }

    protected void shuffleTrashPileToDrawingPile() {
        drawingPile.addAll(trashPile);
        trashPile.clear();
        shuffleDrawingPile();
    }

    protected void shuffleDrawingPile() {
        Collections.shuffle(drawingPile, random); 
    }

    protected int getDrawingPileCardCount() {
        return drawingPile.size();
    }
    
}
        
        
