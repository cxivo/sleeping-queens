package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObservable {
    private List<GameObserver> generalObservers;
    private Map<Integer, List<GameObserver>> playerObservers;
    private int numberOfPlayers;
    private String[] playerNames;

    public GameObservable(String[] playerNames) {
        generalObservers = new ArrayList<>();
        playerObservers = new HashMap<>();
        this.numberOfPlayers = playerNames.length;
        this.playerNames = playerNames;
    }

    public void add(GameObserver observer) {
        generalObservers.add(observer);
    }

    public void addPlayer(int playerIdx, GameObserver observer) {
        List<GameObserver> observers = playerObservers.getOrDefault(playerIdx, new ArrayList<GameObserver>());
        observers.add(observer);
        playerObservers.put(playerIdx, observers);
    }

    public void remove(GameObserver observer) {
        // try removing from general observer list
        generalObservers.remove(observer);
        // try removing from player observer list
        for (List<GameObserver> observerList : playerObservers.values()) {
            observerList.remove(observer);
        }
    }

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

    private String formatPlayerInfo(int playerIndex, PlayerState playerState) {
        String info = "You: (Player " + (1 + playerIndex) + ") " + playerNames[playerIndex] + ":\n";

        int awokenQueens = playerState.getAwokenQueens().getQueens().size();
        for (int i = 0; i < awokenQueens; i++) {
            info += " #";
        }
        info += "\nCards:\n";
        for (int i = 0; i < playerState.getCards().size(); i++) {
            Card card = playerState.getCards().get(i);
            info += "   " + i + ") " + card.getType().toString();
            if (card.getType() == CardType.Number) {
                info += " " + card.getValue();
            }
            info += "\n";
        }

        return info;
    }

    private String formatGameInfo(GameState message) {
        String info = "Player on turn: " + (message.getOnTurn() + 1) + "/" + message.getNumberOfPlayers() + "\n";

        int sleepingQueensCount = message.getSleepingQueens().getQueens().size();
        info += "Sleeping Queens: " + sleepingQueensCount + "\n";
        for (int i = 0; i < sleepingQueensCount; i++) {
            info += " #";
        }
        info += "\n";

        for (int i = 0; i < numberOfPlayers; i++) {
            info += "(Player " + (1 + i) + ") " + playerNames[i] + ":\n   ";
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
