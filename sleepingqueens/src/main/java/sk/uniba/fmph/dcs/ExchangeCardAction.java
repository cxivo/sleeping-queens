package sk.uniba.fmph.dcs;

import java.util.Collections;

public class ExchangeCardAction implements TurnAction {
    private int cardToExchange;

    public ExchangeCardAction(int cardToExchange) {
        this.cardToExchange = cardToExchange;
    }

    public int getCardToExchange() {
        return cardToExchange;
    }

    @Override
    public boolean doAction(Player player, Game game) {
        player.getHand().pickCards(Collections.singletonList(cardToExchange));
        player.getHand().removePickedCardsAndRedraw();
        return true;
    }
    
}
