package sk.uniba.fmph.dcs;

import java.util.List;

/**
 * Class representing the player
 */
public class Player {
    private Hand hand;
    private QueenCollection awokenQueens;

    /**
     * Constructs the player
     * @param cards list of cards the player has at the beginning of the game
     * @param pile the game's drawing and trash pile
     */
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
