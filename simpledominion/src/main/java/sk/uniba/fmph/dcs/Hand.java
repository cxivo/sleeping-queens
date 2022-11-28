package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private List<Card> pickedCards;
    private List<Card> remainingCards;
    private DrawingAndTrashPile pile;
    
    public Hand(List<Card> cards, DrawingAndTrashPile pile) {
        this.cards = cards;
        this.pickedCards = new ArrayList<>();
        this.remainingCards = new ArrayList<>(cards);
        this.pile = pile;
    }

    public List<Card> pickCards(List<Integer> positions) {
        pickedCards.clear();
        remainingCards.clear();

        for (int i = 0; i < Game.CARDS_PER_PLAYER; i++) {
            if (positions.contains(i)) {
                pickedCards.add(cards.get(i));
            } else {
                remainingCards.add(cards.get(i));
            }
        }

        return pickedCards;
    }

    public List<Card> removePickedCardsAndRedraw() {
        cards = remainingCards;
        cards.addAll(pile.discardAndDraw(pickedCards));
        pickCards(new ArrayList<Integer>());
        return cards;
    }

    public int hasCardOfType(CardType type) {
        for (int i = 0; i < Game.CARDS_PER_PLAYER; i++) {
            if (cards.get(i).getType() == type) {
                return i;
            }
        }
        return -1;
    }

    public List<Card> getCards() {
        return cards;
    }

}
