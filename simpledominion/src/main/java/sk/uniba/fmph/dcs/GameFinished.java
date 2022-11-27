package sk.uniba.fmph.dcs;

import java.util.Optional;

public class GameFinished implements GameFinishedStrategy {
    private Game game;

    public GameFinished(Game game) {
        this.game = game;
    }

    @Override
    public Optional<Integer> isFinished() {
        for (int i = 0; i < game.getPlayerCount(); i++) {
            int score = 0, queens = 0;
            for (Queen queen : game.getPlayers().get(i).getAwokenQueens().getQueens()) {
                score += queen.getPoints();
                queens++;
            }

            if (score >= requiredPointsToWin() || queens >= requiredQueensToWin()) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    private int requiredPointsToWin() {
        if (game.getPlayerCount() <= 3) {
            return 50;
        } else {
            return 40;
        }
    }

    private int requiredQueensToWin() {
        if (game.getPlayerCount() <= 3) {
            return 5;
        } else {
            return 4;
        }
    }
}
