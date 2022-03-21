package higorn.bankocr;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountNumberParserTest {

  @ParameterizedTest
  @MethodSource("getAccountNumberCases")
  void shouldParseSevenSegmentAccountNumbers(String[] rawAccountNumber, String expected) {
    var accountNumber = new AccountNumber(rawAccountNumber);
    assertEquals(expected, accountNumber.toString());
  }

  private static Stream<Arguments> getAccountNumberCases() {
    return Stream.of(
        Arguments.of(
            new String[] {
                " _     _  _     _  _  _  _ ",
                "| |  | _| _||_||_ |_   ||_|",
                "|_|  ||_  _|  | _||_|  ||_|"
            }, "012345678"),
        Arguments.of(
            new String[] {
                "    _  _     _  _  _  _  _ ",
                "  | _| _||_||_ |_   ||_||_|",
                "  ||_  _|  | _||_|  ||_| _|"
            }, "123456789"),
        Arguments.of(
            new String[] {
                "    _  _  _  _  _  _  _  _ ",
                "|_||_   ||_ | ||_|| || || |",
                "  | _|  | _||_||_||_||_||_|"
            }, "457508000")
    );
  }
}
