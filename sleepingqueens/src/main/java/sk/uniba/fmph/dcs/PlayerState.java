package sk.uniba.fmph.dcs;

import java.util.List;

/**
 * Structure holding everything one would like to know about the player (at least everything we know)
 */
public class PlayerState {
    private List<Card> cards;
    private QueenCollection awokenQueens;

    public PlayerState(List<Card> cards, QueenCollection awokenQueens) {
        this.cards = cards;
        this.awokenQueens = awokenQueens;
    }

    public List<Card> getCards() {
        return cards;
    }

    public QueenCollection getAwokenQueens() {
        return awokenQueens;
    }
}
