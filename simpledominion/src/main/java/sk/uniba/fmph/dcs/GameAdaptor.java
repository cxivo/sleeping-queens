package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameAdaptor implements GamePlayerInterface {
    private GameObservable gameObservable;
    private StoringObserver[] observers;
    private String[] names;
    private Game game;

    public GameAdaptor(String... names) {
        this.names = names;
        this.game = new Game(names.length);
        this.gameObservable = new GameObservable(names);
        this.observers = new StoringObserver[names.length];

        for (int i = 0; i < names.length; i++) {
            observers[i] = new StoringObserver();
            gameObservable.addPlayer(i, observers[i]);
        }
    }

    /* Performs the action specified in cards for the player 
     * Input format:
     * - throw card x: "hx" where x is card on hand (e.g. "h1")
     * - throw equation made of cards xyz: "hxyz" (e.g. "h12", "h2541")
     * - wake up queen: "hx ay" where x is card on hand (King) and y is the position of the sleeping Queen
     * - attack queen: "hx ayz" where x is card on hand (Knight, Sleeping potion), 
     *   y is the position of the targeted player and z is the position of their queen
     * @see sk.uniba.fmph.dcs.GamePlayerInterface#play(java.lang.String, java.lang.String)
     */
    @Override
    public String play(String player, String cards) {
        // check if player is on turn
        int playerIndex = findPlayerIndex(player);
        if (game.getOnTurn() != playerIndex) {
            throw new IllegalArgumentException("You are not on turn!");
        }

        // decode the input
        // any Exceptions get forwarded to the caller
        int cardOnHand = cards.charAt(1) - '0';
        TurnAction action;

        if (cards.length() == 2) {  // "hx", card exchange
            action = new ExchangeCardAction(cardOnHand);
        } else if (cards.charAt(2) == ' ') {  // either "hx ay" or "hx ayz"
            if (cards.length() == 5) {  // "hx ay", queen wake up
                int sleepingQueenPos = cards.charAt(4) - '0';
                action = new QueenWakeupAction(cardOnHand, sleepingQueenPos);
            } else {  // "hx ayz", queen theft
                int targetedPlayerPos = cards.charAt(4) - '0';
                int queenPos = cards.charAt(5) - '0';
                action = new AttackAction(cardOnHand, targetedPlayerPos, queenPos);
            }
        } else {  // "hxyz", equation exchange
            List<Integer> cardIndeces = new ArrayList<>();
            for (int i = 1; i < cards.length(); i++) {
                cardIndeces.add(cards.charAt(i) - '0');
            }
            action = new EquationExchangeAction(cardIndeces);
        }
        
        // try performing the action
        Optional<GameState> state = game.play(action);
        if (state.isPresent()) {
            gameObservable.notifyAll(state.get());
            return observers[game.getOnTurn()].getMessage();
        } else {
            return "Illegal action, check format\n";
        }

    }

    private int findPlayerIndex(String player) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }
    
}
