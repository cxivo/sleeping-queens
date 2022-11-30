package sk.uniba.fmph.dcs;

import java.util.List;

/**
 * This structure is returned by the game object as a nice wrap for all the goodies one would like to know about the game
 */
public class GameState {
    private int numberOfPlayers;
    private int onTurn;
    private QueenCollection sleepingQueens;
    private List<PlayerState> playerStates;  // cards + awokenQueens are already there
    private List<Card> cardsDiscardedLastTurn;  // I have no ide why anyone would want to know this
    private boolean gameWon;
    private int winnerIndex = -1;

    /**
     * Constructor for when nobody has won yet
     */
    public GameState(int numberOfPlayers, int onTurn, QueenCollection sleepingQueens, List<PlayerState> playerStates, List<Card> cardsDiscardedLastTurn) {
        this.numberOfPlayers = numberOfPlayers;
        this.onTurn = onTurn;
        this.sleepingQueens = sleepingQueens;
        this.playerStates = playerStates;
        this.cardsDiscardedLastTurn = cardsDiscardedLastTurn;
        this.gameWon = false;
    }
    /**
     * Constructor for when somebody has won
    */
    public GameState(int numberOfPlayers, int onTurn, QueenCollection sleepingQueens, List<PlayerState> playerStates, List<Card> cardsDiscardedLastTurn, int winnerIndex) {
        this(numberOfPlayers, onTurn, sleepingQueens, playerStates, cardsDiscardedLastTurn);
        this.gameWon = true;
        this.winnerIndex = winnerIndex;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getOnTurn() {
        return onTurn;
    }

    public QueenCollection getSleepingQueens() {
        return sleepingQueens;
    }

    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    public List<Card> getCardsDiscardedLastTurn() {
        return cardsDiscardedLastTurn;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getWinnerIndex() {
        return winnerIndex;
    }
    
}
