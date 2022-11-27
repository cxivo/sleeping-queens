package sk.uniba.fmph.dcs;

import java.util.List;

public class Player {
    String name;
    Hand hand;
    QueenCollection awokenQueens;

    public Player(List<Card> cards, DrawingAndTrashPile pile) {
        hand = new Hand(cards, pile);
    }

    public PlayerState getPlayerState() {
        return new PlayerState(hand.getCards(), awokenQueens);
    }
    
    public void play(TurnAction action) {

    }
}
