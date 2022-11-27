package sk.uniba.fmph.dcs;

import java.util.List;

public class PlayerState {
    public List<Card> cards;
    public QueenCollection awokenQueens;

    public PlayerState(List<Card> cards, QueenCollection awokenQueens) {
        this.cards = cards;
        this.awokenQueens = awokenQueens;
    }
}
