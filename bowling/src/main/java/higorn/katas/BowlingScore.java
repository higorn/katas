package higorn.katas;

public class BowlingScore {
  private static final int NUMBER_OF_FRAMES = 10;

  private final String[] scoreFrames;
  private int totalPoints;

  public BowlingScore(String[] scoreFrames) {
    this.scoreFrames = scoreFrames;
    calculatePoints();
  }

  private void calculatePoints() {
    for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
      var frame = scoreFrames[i];
      if (isStrike(frame))
        totalPoints += 10 + getStrikeBonus(i+1);
      else if (isSpare(frame))
        totalPoints += 10 + getSpareBonus(i+1);
      else
        totalPoints += getFramePoints(i);
    }
  }

  private int getStrikeBonus(int frameIdx) {
    var frame = scoreFrames[frameIdx];
    if (isStrike(frame))
      return 10 + getFramePoints(frameIdx+1);
    if (isSpare(frame))
      return 10;

    return getFramePoints(frameIdx);
  }

  private int getFramePoints(int frameIdx) {
    var frame = scoreFrames[frameIdx];
    if (isStrike(frame) || isSpare(frame))
      return 10;

    var roll1 = frame.substring(0, 1);
    var roll2 = frame.substring(1, 2);
    return Integer.parseInt(roll1) + (roll2.equals("-") ? 0 : Integer.parseInt(roll2));
  }

  private int getSpareBonus(int frameIdx) {
    if (frameIdx >= NUMBER_OF_FRAMES)
      return getLastSpareBonus(scoreFrames[frameIdx - 1]);

    var frame = scoreFrames[frameIdx];
    if (isStrike(frame))
      return 10;

    return Integer.parseInt(frame.substring(0, 1));
  }

  private int getLastSpareBonus(String scoreFrame) {
    var frame = scoreFrame;
    var nextRoll = frame.substring(2, 3);
    return isStrike(nextRoll) ? 10 : Integer.parseInt(nextRoll);
  }

  private boolean isSpare(String frame) {
    return frame.contains("/");
  }

  private boolean isStrike(String frame) {
    return frame.equals("X");
  }

  public int getTotalPoints() {
    return totalPoints;
  }
}
