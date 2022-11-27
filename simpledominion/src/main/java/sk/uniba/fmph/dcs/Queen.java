package sk.uniba.fmph.dcs;

public class Queen {
    private int points;
    private int id;

    public Queen(int id ,int points) {
        this.id = id;
        this.points = points;
    }

    int getPoints() {
        return points;
    }

    int getId() {
        return id;
    }
}
