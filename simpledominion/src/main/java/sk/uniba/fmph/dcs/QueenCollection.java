package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class QueenCollection {
    List<Queen> queens = new ArrayList<>();

    public void addQueen(Queen queen) {
        queens.add(queen);
    }

    public List<Queen> getQueens() {
        return queens;
    }

    public Queen removeQueen(int position) {
        return queens.remove(position);
    }

}
