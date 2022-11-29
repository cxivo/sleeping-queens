package sk.uniba.fmph.dcs;

import java.util.List;

interface EmptyPileStrategy {
    public List<Card> discardAndDraw(List<Card> toDiscard);
}