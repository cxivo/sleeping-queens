package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueenCollection {
    List<Queen> queens = new ArrayList<>();

    public void addQueen(Queen queen) {
        queens.add(queen);
    }

    public List<Queen> getQueens() {
        return queens;
    }

    public Optional<Queen> removeQueen(int position) {
        if (position >= queens.size() || position < 0) {
            return Optional.empty();
        } else {
            return Optional.of(queens.remove(position));
        }
    }

}
