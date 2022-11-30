package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all of the logic necessary for *hand*ling cards (no pun intended)
 */
public class Hand {
    private List<Card> cards;
    private List<Card> pickedCards;
    private List<Card> remainingCards;
    private DrawingAndTrashPile pile;
    
    /**
     * @param cards cards which the player receives before the game starts
     * @param pile the game's drawing and trash pile 
     */
    public Hand(List<Card> cards, DrawingAndTrashPile pile) {
        this.cards = cards;
        this.pickedCards = new ArrayList<>();
        this.remainingCards = new ArrayList<>();
        this.remainingCards.addAll(cards);
        this.pile = pile;
    }

    /**
     * The hand remembers which positions were picked so they can be removed using removePickedCardsAndRedraw
     * @param positions positions of the cards on hand to pick
     * @return the list of picked cards
     */
    public List<Card> pickCards(List<Integer> positions) {
        pickedCards.clear();
        remainingCards.clear();

        for (int i = 0; i < cards.size(); i++) {
            if (positions.contains(i)) {
                pickedCards.add(cards.get(i));
            } else {
                remainingCards.add(cards.get(i));
            }
        }

        return pickedCards;
    }

    /**
     * Removes the cards marked as picked, draws the equivalent number of new cards and resets the pick
     * @return the new situation on hand
     */
    public List<Card> removePickedCardsAndRedraw() {
        cards.clear();
        cards.addAll(remainingCards);
        cards.addAll(pile.discardAndDraw(pickedCards));
        pickCards(new ArrayList<Integer>());

        return cards;
    }

    /**
     * Returns the index of the specified card type on hand
     * @param type the type of card
     * @return index of the first occurence of a card that type, -1 if none are on hand
     */
    public int hasCardOfType(CardType type) {
        for (int i = 0; i < cards.size(); i++) {
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
