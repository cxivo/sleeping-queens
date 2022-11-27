package sk.uniba.fmph.dcs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QueenWakeupAction implements TurnAction {
    private int cardPosition;
    private int queenPosition;

    public QueenWakeupAction(int cardPosition, int queenPosition) {
        this.cardPosition = cardPosition;
        this.queenPosition = queenPosition;
    }    

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
