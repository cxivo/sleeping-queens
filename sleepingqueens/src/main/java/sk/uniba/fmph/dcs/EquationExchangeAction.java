package sk.uniba.fmph.dcs;

import java.util.Comparator;
import java.util.List;

/**
 * Class for representing the action of throwing multiple numeric cards which make up an additive equation.
 * Also represents throwing two number cards of the same value (since x = x is a valid additive equation)
 */
public class EquationExchangeAction implements TurnAction {
    private List<Integer> equation;

    /**
     * @param equation the positions of cards on player's hand which make up an additive equation
     */
    public EquationExchangeAction(List<Integer> equation) {
        this.equation = equation;
    }

    public List<Integer> getEquation() {
        return equation;
    }

    @Override
    public boolean doAction(Player player, Game game) {
        // check whether there are at least 2 cards
        if (equation.size() < 2) {
            return false;
        }

        List<Card> cards = player.getHand().pickCards(equation);

        // check whether the indeces were correct
        if (cards.size() != equation.size()) {
            return false;
        }

        // sort them from lowest to highest
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card left, Card right) {
                return Integer.compare(left.getValue(), right.getValue());
            }   
        });

        // left side of the equation
        int leftSide = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getType() == CardType.Number) {
                leftSide += cards.get(i).getValue();
            } else {
                // non-numeric card picked
                return false;
            }
        }

        // right side of the equation
        if (cards.get(cards.size() - 1).getType() == CardType.Number) {
            // if left and right side equal
            if (cards.get(cards.size() - 1).getValue() == leftSide) {
                player.getHand().removePickedCardsAndRedraw();
                return true;
            }
        } 

        // equation is incorrect or the last card is non-numeric
        return false;
    }
    
}
