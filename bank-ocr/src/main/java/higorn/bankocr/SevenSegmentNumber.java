package higorn.bankocr;

import java.util.Arrays;

public class SevenSegmentNumber {
  private final String[] sevenSegmentNumber;
  private final Integer number;

  public SevenSegmentNumber(String[] sevenSegmentNumber) {
    this.sevenSegmentNumber = sevenSegmentNumber;
    number = parseNumber(sevenSegmentNumber);
  }

  private int parseNumber(String[] number) {
    for (int n = 0; n < numbers.length; n++)
      if (Arrays.compare(numbers[n], number) == 0)
        return n;
    throw new IllegibleNumberException();
  }

  public Integer getInt() {
    return number;
  }

  public final static String[][] numbers = {
      {
          " _ ",
          "| |",
          "|_|"
      },
      {
          "   ",
          "  |",
          "  |"
      },
      {
          " _ ",
          " _|",
          "|_ "
      },
      {
          " _ ",
          " _|",
          " _|"
      },
      {
          "   ",
          "|_|",
          "  |"
      },
      {
          " _ ",
          "|_ ",
          " _|"
      },
      {
          " _ ",
          "|_ ",
          "|_|"
      },
      {
          " _ ",
          "  |",
          "  |"
      },
      {
          " _ ",
          "|_|",
          "|_|"
      },
      {
          " _ ",
          "|_|",
          " _|"
      },
  };

  public static class IllegibleNumberException extends RuntimeException {
  }
}
