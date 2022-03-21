package tennis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TennisMatchTest {
  private TennisMatch match;

  @BeforeEach
  void setUp() {
    match = new TennisMatch();
  }

  private void givenOnePlayerHas40AndOtherHas30(int player1, int player2) {
    match.score(player1); // 15
    match.score(player2); // 15
    match.score(player1); // 30
    match.score(player2); // 30
    match.score(player1); // 40
  }

  private void givenDuce() {
    givenOnePlayerHas40AndOtherHas30(TennisMatch.PLAYER1, TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER2); // 40
  }

  @Test
  void givenNoScores_thenTheScoreBoardShowsLoveForBothPlayers() {
    assertThat(match.getScores()).containsExactly("love", "love");
  }

  @Test
  void givenNoScores_whenPlayer1Scores_thenTheScoreBoardShows15ForPlayer1AndLoveForPlayer2() {
    match.score(TennisMatch.PLAYER1);
    assertThat(match.getScores()).containsExactly("15", "love");
  }

  @Test
  void givenNoScores_whenPlayer2Scores_thenTheScoreBoardShowsLoveForPlayer1And15ForPlayer2() {
    match.score(TennisMatch.PLAYER2);
    assertThat(match.getScores()).containsExactly("love", "15");
  }

  @Test
  void givenPlayer1Has40_andPlayer2Has30_whenPlayer1Scores_thenPlayer1Wins() {
    givenOnePlayerHas40AndOtherHas30(TennisMatch.PLAYER1, TennisMatch.PLAYER2);

    assertTrue(match.score(TennisMatch.PLAYER1));
    assertEquals(TennisMatch.PLAYER1, match.getWinner());
  }

  @Test
  void givenPlayer2Has40_andPlayer1Has30_whenPlayer2Scores_thenPlayer2Wins() {
    givenOnePlayerHas40AndOtherHas30(TennisMatch.PLAYER2, TennisMatch.PLAYER1);

    assertTrue(match.score(TennisMatch.PLAYER2));
    assertEquals(TennisMatch.PLAYER2, match.getWinner());
  }

  @Test
  void givenTheMatchDoesntEnd_whenGetWinner_thenThrowsGameNotEndException() {
    assertThrows(TennisMatch.MatchNotEndException.class, () -> match.getWinner());
  }

  @Test
  void givenTheMatchIsInDuce_whenPlayer1Scores_thenPlayer1HasTheAdvantage() {
    givenDuce();

    match.score(TennisMatch.PLAYER1); // 40+

    assertThat(match.getScores()).containsExactly("40+", "40");
  }

  @Test
  void givenTheMatchIsInDuce_whenPlayer2Scores_thenPlayer2HasTheAdvantage() {
    givenDuce();

    match.score(TennisMatch.PLAYER2); // 40+

    assertThat(match.getScores()).containsExactly("40", "40+");
  }

  @Test
  void givenPlayer1HasAdvantage_whenPlayer2Scores_thenTheyAreBackAtDuce() {
    givenDuce();
    match.score(TennisMatch.PLAYER1); // 40+

    match.score(TennisMatch.PLAYER2); // 40+

    assertThat(match.getScores()).containsExactly("40", "40");
  }

  @Test
  void givenPlayer2HasAdvantage_whenPlayer1Scores_thenPlayer1HasTheAdvantage() {
    givenDuce();
    match.score(TennisMatch.PLAYER2); // 40+

    match.score(TennisMatch.PLAYER1); // 40+

    assertThat(match.getScores()).containsExactly("40", "40");
  }

  @Test
  void givenPlayer1HasAdvantage_whenPlayer1Scores_thenPlayer1Wins() {
    givenDuce();
    match.score(TennisMatch.PLAYER1); // 40+

    assertTrue(match.score(TennisMatch.PLAYER1));
    assertEquals(TennisMatch.PLAYER1, match.getWinner());
  }

  @Test
  void givenPlayer2HasAdvantage_whenPlayer2Scores_thenPlayer1Wins() {
    givenDuce();
    match.score(TennisMatch.PLAYER2); // 40+

    assertTrue(match.score(TennisMatch.PLAYER2));
    assertEquals(TennisMatch.PLAYER2, match.getWinner());
  }

  @Test
  void givenManyDuces_andOnePlayerGotAdvantage_whenThisPlayerScores_thenThePlayerWins() {
    givenDuce();
    match.score(TennisMatch.PLAYER1);
    match.score(TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER1);
    match.score(TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER1);
    match.score(TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER1);
    match.score(TennisMatch.PLAYER2);

    match.score(TennisMatch.PLAYER1);

    assertTrue(match.score(TennisMatch.PLAYER1));
    assertEquals(TennisMatch.PLAYER1, match.getWinner());
  }

  @Test
  void givenTheGameOver_whenTryScore_thenThrowsException() {
    givenOnePlayerHas40AndOtherHas30(TennisMatch.PLAYER1, TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER1);

    assertThrows(TennisMatch.ScoreFinishedMatchException.class, () -> match.score(TennisMatch.PLAYER1));
  }

  @Test
  void givenTheGameOver_whenGetScores_thenShowsFinalScores() {
    givenOnePlayerHas40AndOtherHas30(TennisMatch.PLAYER1, TennisMatch.PLAYER2);
    match.score(TennisMatch.PLAYER1);

    assertThat(match.getScores()).containsExactly("40", "30");
  }
}
