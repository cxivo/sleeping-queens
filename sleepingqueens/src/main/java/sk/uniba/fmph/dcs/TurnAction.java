package sk.uniba.fmph.dcs;

/**
 * Common interface for classes representing an action a player can take in a turn.
 * The Game object takes in this an instance of this interface and gives it the information
 * recquired to do everything to simulate the move.
 */
public interface TurnAction {
    /**
     * The method that gets called by the game to perform the action
     * @param player player performing the action
     * @param game game in which the players reside
     * @return if the action was valid
     */
    public boolean doAction(Player player, Game game);
}
