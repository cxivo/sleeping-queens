package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public Game(int numberOfPlayers) {
        if ((numberOfPlayers < MIN_PLAYERS) || (numberOfPlayers > MAX_PLAYERS)) {
            throw new IllegalArgumentException("Number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS + " (inclusive)");
        }
        this.numberOfPlayers = numberOfPlayers;

        sleepingQueens = new QueenCollection();
        for (int i = 0; i < SLEEPING_QUEENS; i++) {
            sleepingQueens.addQueen(new Queen(i, 10));
        }

        players = new ArrayList<>();

        gameFinishedStrategy = new GameFinished(this);

        pile = new DrawingAndTrashPile(getFullCardList(), new Random());

        for (int i = 0; i < numberOfPlayers; i++) {
            List<Card> cardsForPlayer = new ArrayList<>();
            for (int j = 0; j < CARDS_PER_PLAYER; j++) {
                cardsForPlayer.add(pile.getTopCard());
            }
            players.add(new Player(cardsForPlayer, pile));
        }
    }

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
