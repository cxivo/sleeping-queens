package sk.uniba.fmph.dcs;

/**
 * Interface for communicating with the Game object in a more user-friendly way
 */
public interface GamePlayerInterface {
    /**
     * Interprets the commands given in the cards parameter and plays the appropriate action for the player
     * @param player player on turn (does nothing, because it's the game's job to check who's on turn)
     * @param cards the command describing the player's action
     * @return description of the resulting state
     */
    public String play(String player, String cards);
}
