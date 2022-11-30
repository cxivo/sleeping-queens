package sk.uniba.fmph.dcs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class for representing the action of using a Knight or a Sleeping Potion on a player's queen
 */
public class AttackAction implements TurnAction {
    private int cardPosition;
    private int playerIndex;
    private int queenPosition;

    public AttackAction(int cardPosition, int playerIndex, int queenPosition) {
        this.cardPosition = cardPosition;
        this.playerIndex = playerIndex;
        this.queenPosition = queenPosition;
    }

    @Override
    public boolean doAction(Player player, Game game) {
        // pick card from player's hand
        List<Card> picked = player.getHand().pickCards(Collections.singletonList(cardPosition));
        if (picked.size() != 1) {
            return false;
        }

        CardType cardType = picked.get(0).getType();

        // if the card is a Knight or a Sleeping potion
        if (cardType == CardType.Knight || cardType == CardType.SleepingPotion) {
            // check if playerIndex is correct and if the targeted player is different from the current player
            if (playerIndex < 0 || playerIndex >= game.getPlayerCount() || game.getPlayers().get(playerIndex) == player) {
                return false;
            }

            // check if the targeted player has the corresponding defence card
            Player targetedPlayer = game.getPlayers().get(playerIndex);
            int defenceCardPosition = targetedPlayer.getHand().hasCardOfType(defenceCard(cardType));
            
            if (defenceCardPosition != -1) {
                // targeted player has the defence card - throw both the attacking and defence card
                player.getHand().removePickedCardsAndRedraw();

                targetedPlayer.getHand().pickCards(Collections.singletonList(defenceCardPosition));
                targetedPlayer.getHand().removePickedCardsAndRedraw();
                return true;
            } else {
                // targeted player doesn't have the defence card - yeet the Queen away!
                Optional<Queen> queen = targetedPlayer.getAwokenQueens().removeQueen(queenPosition);

                if (queen.isPresent()) {
                    // put the stolen Queen into the correct Queen collection
                    // either to player's collection or back to sleep
                    queenEndsUpIn(cardType, player, game).addQueen(queen.get());

                    // throw away used card
                    player.getHand().removePickedCardsAndRedraw();
                    return true;
                } else {
                    // invalid Queen position
                    return false;
                }
            }
        }
        return false;
    }
    
    CardType defenceCard(CardType cardType) {
        if (cardType == CardType.Knight) {
            return CardType.Dragon;
        } else {
            return CardType.MagicWand;
        }
    }

    QueenCollection queenEndsUpIn(CardType cardType, Player player, Game game) {
        if (cardType == CardType.Knight) {
            return player.getAwokenQueens();
        } else {
            return game.getSleepingQueens();
        }
    }
}
