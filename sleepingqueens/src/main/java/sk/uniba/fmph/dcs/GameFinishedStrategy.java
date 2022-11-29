package sk.uniba.fmph.dcs;

import java.util.Optional;

public interface GameFinishedStrategy {
    public Optional<Integer> isFinished();
}
