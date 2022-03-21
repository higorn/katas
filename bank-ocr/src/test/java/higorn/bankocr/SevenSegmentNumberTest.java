package higorn.bankocr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SevenSegmentNumberTest {

  @ParameterizedTest
  @MethodSource("getNumberCases")
  void shouldParseASevenSegmentNumber(String[] rawNumber, int expected) {
    var number = new SevenSegmentNumber(rawNumber);
    assertEquals(expected, number.getInt());
  }

  @Test
  void whenTheNumberIsIllegible_thenThrowsException() {
    String[] number = { "   ", "| |", "|_|" };
    assertThrows(SevenSegmentNumber.IllegibleNumberException.class, () -> new SevenSegmentNumber(number));
  }

  private static Stream<Arguments> getNumberCases() {
    return Stream.of(
        Arguments.of(
            new String[] {
                " _ ",
                "| |",
                "|_|"
            }, 0),
        Arguments.of(
            new String[] {
                "   ",
                "  |",
                "  |"
            }, 1),
        Arguments.of(
            new String[] {
                " _ ",
                " _|",
                "|_ "
            }, 2),
        Arguments.of(
            new String[] {
                " _ ",
                " _|",
                " _|"
            }, 3),
        Arguments.of(
            new String[] {
                "   ",
                "|_|",
                "  |"
            }, 4),
        Arguments.of(
            new String[] {
                " _ ",
                "|_ ",
                " _|"
            }, 5),
        Arguments.of(
            new String[] {
                " _ ",
                "|_ ",
                "|_|"
            }, 6),
        Arguments.of(
            new String[] {
                " _ ",
                "  |",
                "  |"
            }, 7),
        Arguments.of(
            new String[] {
                " _ ",
                "|_|",
                "|_|"
            }, 8),
        Arguments.of(
            new String[] {
                " _ ",
                "|_|",
                " _|"
            }, 9)
    );
  }
}
