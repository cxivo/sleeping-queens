package sk.uniba.fmph.dcs;

/**
 * Classes implementing this interface can subscribe to the GameObservable and be notified when the GameState changes.
 */
public interface GameObserver {
    public void notify(String message);
}
