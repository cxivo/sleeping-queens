package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class ShuffleBeforeDrawingStrategy implements EmptyPileStrategy {
    private DrawingAndTrashPile pile;

    public ShuffleBeforeDrawingStrategy(DrawingAndTrashPile pile) {
        this.pile = pile;
    }

    @Override
    public List<Card> discardAndDraw(List<Card> toDiscard) {
        for (Card card : toDiscard) {
            pile.throwCard(card);
        }

        List<Card> toDraw = new ArrayList<>();

        for (int i = 0; i < toDiscard.size(); i++) {
            toDraw.add(pile.getTopCard());
        }
        
        return toDraw;
    }
    
}
