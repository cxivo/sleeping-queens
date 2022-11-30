package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The main class of the package, which deals with all the game logic
 */
public class Game {
    public static final int CARDS_PER_PLAYER = 5;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;
    public static final int SLEEPING_QUEENS = 12;
    
    private int numberOfPlayers;
    private int onTurn = 0;
    private QueenCollection sleepingQueens;
    private List<Player> players;
    private DrawingAndTrashPile pile;
    private GameFinishedStrategy gameFinishedStrategy;

    /**
     * Public constructor for the class
     * @param numberOfPlayers number of players in the game
     */
    public Game(int numberOfPlayers) {
        this(numberOfPlayers, new DrawingAndTrashPile(getFullCardList(), new Random()));
    }

    protected Game(int numberOfPlayers, DrawingAndTrashPile pile) {
        if ((numberOfPlayers < MIN_PLAYERS) || (numberOfPlayers > MAX_PLAYERS)) {
            throw new IllegalArgumentException("Number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS + " (inclusive)");
        }
        this.numberOfPlayers = numberOfPlayers;

        // add sleeping queens
        sleepingQueens = new QueenCollection();
        for (int i = 0; i < SLEEPING_QUEENS; i++) {
            sleepingQueens.addQueen(new Queen(10));
        }

        players = new ArrayList<>();

        gameFinishedStrategy = new GameFinished(this);

        this.pile = pile;

        // give players cards
        for (int i = 0; i < numberOfPlayers; i++) {
            List<Card> cardsForPlayer = new ArrayList<>();
            for (int j = 0; j < CARDS_PER_PLAYER; j++) {
                cardsForPlayer.add(this.pile.getTopCard());
            }
            players.add(new Player(cardsForPlayer, this.pile));
        }
    }

    /**
     * This method performs the action for the player on turn. If successful, the next player is on turn.
     * @param action the action the player wants to take
     * @return the new GameState after this turn. If empty, the action was invalid and the same player is still on turn.
     */
    public Optional<GameState> play(TurnAction action) {
        if (action.doAction(players.get(onTurn), this)) {
            // success
            onTurn = (onTurn + 1) % numberOfPlayers;
            pile.newTurn();
            return Optional.of(getGameState());
        } else {
            // failure
            return Optional.empty();
        }
    }

    /**
     * @return the current state of the game
     */
    public GameState getGameState() {
        // get player states
        List<PlayerState> playerStates = new ArrayList<>();
        for (Player player : players) {
            playerStates.add(player.getPlayerState());
        }
        
        // is game won yet?
        Optional<Integer> winnerIndex = gameFinishedStrategy.isFinished();
        if (winnerIndex.isPresent()) {
            return new GameState(numberOfPlayers, onTurn, sleepingQueens, playerStates, pile.getCardsDiscardedThisTurn(), winnerIndex.get());
        } else {
            return new GameState(numberOfPlayers, onTurn, sleepingQueens, playerStates, pile.getCardsDiscardedThisTurn());
        }
    }

    public QueenCollection getSleepingQueens() {
        return sleepingQueens;
    }

    public int getPlayerCount() {
        return numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getOnTurn() {
        return onTurn;
    }

    /**
     * @return list of all cards used in this version of the game
     */
    protected static List<Card> getFullCardList() {
        List<Card> cards = new ArrayList<>();

        // 8 Kings
        for (int i = 0; i < 8; i++) {
            cards.add(new Card(CardType.King, 0));
        }

        // 4 Knights
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(CardType.Knight, 0));
        }

        // 4 Sleeping potions
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(CardType.SleepingPotion, 0));
        }

        // 3 Wands
        for (int i = 0; i < 3; i++) {
            cards.add(new Card(CardType.MagicWand, 0));
        }

        // 3 Dragons
        for (int i = 0; i < 3; i++) {
            cards.add(new Card(CardType.Dragon, 0));
        }

        // 4 of each number 1 - 10
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 10; j++) {
                cards.add(new Card(CardType.Number, j));
            }
        }

        // 62 cards
        return cards;
    }


    
}
