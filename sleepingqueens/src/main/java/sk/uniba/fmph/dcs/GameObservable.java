package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class notifies all subscribed observers when any change to the GameState occurs.
 */
public class GameObservable {
    private List<GameObserver> generalObservers;
    private Map<Integer, List<GameObserver>> playerObservers;
    private int numberOfPlayers;
    private String[] playerNames;

    /**
     * Creates a new observable object with the players' names in the argument
     * @param playerNames names of the players
     */
    public GameObservable(String... playerNames) {
        generalObservers = new ArrayList<>();
        playerObservers = new HashMap<>();
        this.numberOfPlayers = playerNames.length;
        this.playerNames = playerNames;
    }

    /**
     * Subscribes this observer for the general game info.
     * @param observer the observer that will get notified each time the game state changes
     */
    public void add(GameObserver observer) {
        generalObservers.add(observer);
    }

    /**
     * Subscribes this observer to the game and provides additinal information about the specified player
     * @param playerIdx index of the player whose info the observer will be receiving
     * @param observer the observer
     */
    public void addPlayer(int playerIdx, GameObserver observer) {
        List<GameObserver> observers = playerObservers.getOrDefault(playerIdx, new ArrayList<GameObserver>());
        observers.add(observer);
        playerObservers.put(playerIdx, observers);
    }

    /**
     * Removes the specified observer from both the general observers list and the player-specific list
     * @param observer
     */
    public void remove(GameObserver observer) {
        // try removing from general observer list
        generalObservers.remove(observer);
        // try removing from player observer list
        for (List<GameObserver> observerList : playerObservers.values()) {
            observerList.remove(observer);
        }
    }

    /**
     * Nofifies all subscribing observers of the change in the GameState
     * @param message the GameState from which the info will be pulled
     */
    public void notifyAll(GameState message) {
        String gameInfo = formatGameInfo(message);

        for (GameObserver observer : generalObservers) {
            observer.notify(gameInfo);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerInfo = formatPlayerInfo(i, message.getPlayerStates().get(i));
            String onTurnInfo = (i == message.getOnTurn()) ? "You are on turn.\n" : "";
            for (GameObserver observer : playerObservers.getOrDefault(i, new ArrayList<GameObserver>())) {
                observer.notify(gameInfo + playerInfo + onTurnInfo);
            }
        }
    }

    /**
     * Returns player-specific info about the new game state
     * @param playerIndex index of the player whose information our hearts desire (sorry, too poetic)
     * @param playerState playerState from which the info is pulled
     * @return text description
     */
    private String formatPlayerInfo(int playerIndex, PlayerState playerState) {
        String info = "You: (Player " + (1 + playerIndex) + ") " + playerNames[playerIndex] + ":\n";

        // awoken queens
        int awokenQueens = playerState.getAwokenQueens().getQueens().size();
        for (int i = 0; i < awokenQueens; i++) {
            info += " #";
        }

        // cards on hand
        info += "\nCards:\n";
        for (int i = 0; i < playerState.getCards().size(); i++) {
            Card card = playerState.getCards().get(i);
            info += "   " + (i + 1) + ") " + card.getType().toString();
            if (card.getType() == CardType.Number) {
                info += " " + card.getValue();
            }
            info += "\n";
        }

        info += "==========================================\n";
        
        return info;
    }

    /**
     * Formats the information about the game from an outsider's perspective. Contains no player-specific info.
     * @param message the GameState from which the info is pulled
     * @return text description of the state
     */
    private String formatGameInfo(GameState message) {
        String info = "Player on turn: " + (message.getOnTurn() + 1) + "/" + message.getNumberOfPlayers() + "\n";

        // sleeping queens
        int sleepingQueensCount = message.getSleepingQueens().getQueens().size();
        info += "Sleeping Queens: " + sleepingQueensCount + "\n";
        for (int i = 0; i < sleepingQueensCount; i++) {
            info += " #";
        }
        info += "\n";

        // each player's awoken queens
        for (int i = 0; i < numberOfPlayers; i++) {
            info += "(Player " + (1 + i) + ") " + playerNames[i] + ":\n";
            int awokenQueens = message.getPlayerStates().get(i).getAwokenQueens().getQueens().size();
            for (int j = 0; j < awokenQueens; j++) {
                info += " #";
            }
            info += "\n";
        }

        if (message.isGameWon()) {
            info += playerNames[message.getWinnerIndex()] + " won the game!\n";
        }

        info += "==========================================\n";
        
        return info;
    }
}
