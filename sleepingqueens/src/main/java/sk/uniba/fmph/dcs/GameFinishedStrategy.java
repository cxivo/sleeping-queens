package sk.uniba.fmph.dcs;

import java.util.Optional;

/**
 * This interface is used by the classes responsible for checking the win condition.
 */
public interface GameFinishedStrategy {
    public Optional<Integer> isFinished();
}
