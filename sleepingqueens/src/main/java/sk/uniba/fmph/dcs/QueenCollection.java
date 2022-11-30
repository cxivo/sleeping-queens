package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A common class used to represent a collection of Queens, either Sleeping queens of the game
 * or player's awoken queens. Making more classes is unnecessary, as they work in exactly the same way
 */
public class QueenCollection {
    List<Queen> queens = new ArrayList<>();

    public void addQueen(Queen queen) {
        queens.add(queen);
    }

    public List<Queen> getQueens() {
        return queens;
    }

    /**
     * Removes the queen from the specified position and returns it
     * @param position position of the queen
     * @return the queen removed from the collection
     */
    public Optional<Queen> removeQueen(int position) {
        if (position >= queens.size() || position < 0) {
            return Optional.empty();
        } else {
            return Optional.of(queens.remove(position));
        }
    }

}
