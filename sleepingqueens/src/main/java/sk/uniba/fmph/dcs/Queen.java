package sk.uniba.fmph.dcs;

/**
 * Class representing the Queen card
 * Note: this class is not related to the Card class, as it never enters the drawing/trash pile or hand
 */
public class Queen {
    private int points;

    public Queen(int points) {
        this.points = points;
    }

    int getPoints() {
        return points;
    }
}
