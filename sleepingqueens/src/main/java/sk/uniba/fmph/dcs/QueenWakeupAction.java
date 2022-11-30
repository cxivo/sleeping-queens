package sk.uniba.fmph.dcs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class for representing the action of using a King to wake up a sleeping queen
 */
public class QueenWakeupAction implements TurnAction {
    private int cardPosition;
    private int queenPosition;

    /**
     * @param cardPosition position of the King on hand
     * @param queenPosition position of the sleeping queen on the game's board
     */
    public QueenWakeupAction(int cardPosition, int queenPosition) {
        this.cardPosition = cardPosition;
        this.queenPosition = queenPosition;
    }    

    /**
     * Takes a queen from the game's sleeping queens collection and puts it into player's
     * awoken queens collection
     */
    @Override
    public boolean doAction(Player player, Game game) {
        // pick card from player's hand
        List<Card> picked = player.getHand().pickCards(Collections.singletonList(cardPosition));
        if (picked.size() != 1) {
            return false;
        }

        // if the card is a King
        if (picked.get(0).getType() == CardType.King) {
            // get the Queen from the Sleeping Queens collection
            Optional<Queen> queen = game.getSleepingQueens().removeQueen(queenPosition);
            if (queen.isPresent()) {
                // add the Queen to the player's collection
                player.getAwokenQueens().addQueen(queen.get());
                player.getHand().removePickedCardsAndRedraw();
                return true;
            } else {
                // invalid Queen position
                return false;
            }
        }
        return false;
    }
    
}
