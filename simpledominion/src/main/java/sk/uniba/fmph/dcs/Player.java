package sk.uniba.fmph.dcs;

import java.util.List;

public class Player {
    private Hand hand;
    private QueenCollection awokenQueens;

    public Player(List<Card> cards, DrawingAndTrashPile pile) {
        hand = new Hand(cards, pile);
        awokenQueens = new QueenCollection();
    }

    public PlayerState getPlayerState() {
        return new PlayerState(hand.getCards(), awokenQueens);
    }

    public Hand getHand() {
        return hand;
    }

    public QueenCollection getAwokenQueens() {
        return awokenQueens;
    }
}
