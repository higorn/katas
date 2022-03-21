package higorn.katas;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingScoreTest {

  @ParameterizedTest
  @MethodSource("getTestCases")
  void givenAScoreFrames_thenReturnTotalPoints(String[] scoreFrames, int expectedTotalPoints) {
    var bowlingScore = new BowlingScore(scoreFrames);
    assertEquals(expectedTotalPoints, bowlingScore.getTotalPoints());
  }

  public static Stream<Arguments> getTestCases() {
    return Stream.of(
        Arguments.of(new String[] {"X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"}, 300),
        Arguments.of(new String[] {"9-", "9-", "9-", "9-", "9-", "9-", "9-", "9-", "9-", "9-"}, 90),
        Arguments.of(new String[] {"5/", "5/", "5/", "5/", "5/", "5/", "5/", "5/", "5/", "5/5"}, 150),
        Arguments.of(new String[] {"7/", "6-", "8/", "2/", "9-", "X", "4/", "7/", "5-", "8/9"}, 138),
        Arguments.of(new String[] {"7/", "6-", "8/", "2/", "9-", "X", "4/", "7/", "5-", "8/X"}, 139)
    );
  }
}
