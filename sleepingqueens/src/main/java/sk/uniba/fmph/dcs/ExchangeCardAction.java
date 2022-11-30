package sk.uniba.fmph.dcs;

import java.util.Collections;

/**
 * Class for representing the action of throwing a card and drawing another one
 */
public class ExchangeCardAction implements TurnAction {
    private int cardToExchange;

    /**
     * @param cardToExchange the position of the card on hand
     */
    public ExchangeCardAction(int cardToExchange) {
        this.cardToExchange = cardToExchange;
    }

    public int getCardToExchange() {
        return cardToExchange;
    }

    @Override
    public boolean doAction(Player player, Game game) {
        // exchanges the card
        player.getHand().pickCards(Collections.singletonList(cardToExchange));
        player.getHand().removePickedCardsAndRedraw();
        return true;
    }
    
}
