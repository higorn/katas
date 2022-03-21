package tennis;

public class TennisMatch {
  public static final int PLAYER1 = 0;
  public static final int PLAYER2 = 1;
  private final int[] scores = new int[2];
  private int winner = -1;

  public boolean score(int player) {
    if (gameOver())
      throw new ScoreFinishedMatchException();

    var otherPlayer = player == PLAYER1 ? PLAYER2 : PLAYER1;
    if (scores[otherPlayer] == Points.ADVANTAGE && scores[player] == Points._40) {
      scores[otherPlayer]--;
      return false;
    }
    scores[player]++;
    return isGameOver(player);
  }

  private boolean gameOver() {
    return winner > -1;
  }

  private boolean isGameOver(int player) {
    var isGameOver = Math.abs(scores[0] - scores[1]) >= 2;
    if (isGameOver) {
      scores[player]--;
      winner = player;
    }
    return isGameOver;
  }

  public String[] getScores() {
    return new String[] { Points.getPoint(scores[0]), Points.getPoint(scores[1])};
  }

  public int getWinner() {
    if (gameOver())
      return winner;
    throw new MatchNotEndException();
  }

  static class Points {
    public static final int _40       = 3;
    public static final int ADVANTAGE = 4;

    private static final String[] points = new String[] {"love", "15", "30", "40", "40+"};

    public static String getPoint(int score) {
      return points[score];
    }
  }
  public static class MatchNotEndException extends RuntimeException {
  }

  public static class ScoreFinishedMatchException extends RuntimeException {
  }
}
