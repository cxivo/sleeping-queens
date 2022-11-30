package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides a more user-friendly way to interact with the Game class.
 */
public class GameAdaptor implements GamePlayerInterface {
    private GameObservable gameObservable;
    private StoringObserver[] observers;
    private String[] names;
    private Game game;

    /**
     * Creates a new game with players with the provided names (in that order)
     * @param names names of the players
     */
    public GameAdaptor(String... names) {
        this.names = names;
        this.game = new Game(names.length);
        this.gameObservable = new GameObservable(names);
        this.observers = new StoringObserver[names.length];

        // add an observer to each player - this will make printing information easier
        for (int i = 0; i < names.length; i++) {
            observers[i] = new StoringObserver();
            gameObservable.addPlayer(i, observers[i]);
        }

    }

    public GameObservable getGameObservable() {
        return gameObservable;
    }

    public int getOnTurn() {
        return game.getOnTurn();
    }

    public void notifyPlayers() {
        gameObservable.notifyAll(game.getGameState());
    }

    /** Performs the action specified in cards for the player 
     * Input format:
     * - throw card x: "hx" where x is card on hand (e.g. "h1")
     * - throw equation made of cards xyz: "hxyz" (e.g. "h12", "h2541")
     * - wake up queen: "hx ay" where x is card on hand (King) and y is the position of the sleeping Queen
     * - attack queen: "hx ayz" where x is card on hand (Knight, Sleeping potion), 
     *   y is the position of the targeted player and z is the position of their queen
     * @see sk.uniba.fmph.dcs.GamePlayerInterface#play(java.lang.String, java.lang.String)
     * @return message about how the turn failed or text description if successful
     */
    @Override
    public String play(String player, String cards) {
        // check if player is on turn
        int playerIndex = findPlayerIndex(player);
        if (game.getOnTurn() != playerIndex) {
            return "You are not on turn!";
        }

        // decode the input
        TurnAction action;
        try {
            int cardOnHand = cards.charAt(1) - '0' - 1;

            if (cards.length() == 2) {  // "hx", card exchange
                action = new ExchangeCardAction(cardOnHand);
            } else if (cards.charAt(2) == ' ') {  // either "hx ay" or "hx ayz"
                if (cards.length() == 5) {  // "hx ay", queen wake up
                    int sleepingQueenPos = cards.charAt(4) - '0' - 1;
                    action = new QueenWakeupAction(cardOnHand, sleepingQueenPos);
                } else {  // "hx ayz", queen theft
                    int targetedPlayerPos = cards.charAt(4) - '0' - 1;
                    int queenPos = cards.charAt(5) - '0' - 1;
                    action = new AttackAction(cardOnHand, targetedPlayerPos, queenPos);
                }
            } else {  // "hxyz", equation exchange
                List<Integer> cardIndeces = new ArrayList<>();
                for (int i = 1; i < cards.length(); i++) {
                    cardIndeces.add(cards.charAt(i) - '0' - 1);
                }
                action = new EquationExchangeAction(cardIndeces);
            }
        } catch (Exception e) {
            return "Unknown action, check format\n";
        }
        
        // try performing the action
        Optional<GameState> state = game.play(action);
        if (state.isPresent()) {
            gameObservable.notifyAll(state.get());
            return observers[game.getOnTurn()].getMessage();
        } else {
            return "Incorrect arguments, check format\n";
        }

    }

    /**
     * Returns the index of the player in the game.
     * @param player name of the player
     * @return index of the player in the player list, -1 if not present
     */
    private int findPlayerIndex(String player) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }
    
}
