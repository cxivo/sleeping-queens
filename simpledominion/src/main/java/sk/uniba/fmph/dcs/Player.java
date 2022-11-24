package sk.uniba.fmph.dcs;

import java.util.List;

public class Player {
    String name;
    Hand hand;
    PlayerState state;
    AwokenQueens awokenQueens;

    public Player(String name, List<Card> cards, DrawingAndTrashPile pile) {
        this.name = name;
        hand = new Hand(cards, pile);

    }
    
}
