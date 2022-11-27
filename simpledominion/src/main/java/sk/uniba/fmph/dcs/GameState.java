package sk.uniba.fmph.dcs;

import java.util.List;

public class GameState {
    public int numberOfPlayers;
    public int onTurn;
    public QueenCollection sleepingQueens;
    public List<PlayerState> playerStates;  // cards + awokenQueens are already there
    public List<Card> cardsDiscardedLastTurn;  // I have no ide why anyone would want to know this
}
