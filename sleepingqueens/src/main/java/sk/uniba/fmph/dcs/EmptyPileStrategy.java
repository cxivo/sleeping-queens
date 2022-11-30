package sk.uniba.fmph.dcs;

import java.util.List;

/**
 * Strategy pattern used in the DrawingAndTrashPile class to change the way the pile gets shuffled.
 */
interface EmptyPileStrategy {
    /**
     * This method puts the given cards into the trash pile and returns the same number of cards from the drawing pile
     * @param toDiscard list of cards to be discarded
     * @return list of cards from the top of the drawing pile
     */
    public List<Card> discardAndDraw(List<Card> toDiscard);
}