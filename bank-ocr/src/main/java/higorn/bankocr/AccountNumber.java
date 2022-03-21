package higorn.bankocr;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AccountNumber {
  private final static int           ACCOUNT_NUMBER_LENGTH = 9;
  private final static int           NUMBER_LENGTH = 3;
  private final SevenSegmentNumber[] accountNumber;

  public AccountNumber(String[] rawAccountNumber) {
    accountNumber = parseNumbers(rawAccountNumber);
  }

  private SevenSegmentNumber[] parseNumbers(String[] rawAccountNumber) {
    var accountNumber = new SevenSegmentNumber[ACCOUNT_NUMBER_LENGTH];
    var start = 0;
    var end = NUMBER_LENGTH;
    for (var i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
      var number = new String[NUMBER_LENGTH];
      for (var j = 0; j < NUMBER_LENGTH; j++)
        number[j] = rawAccountNumber[j].substring(start, end);
      accountNumber[i] = new SevenSegmentNumber(number);
      start += 3;
      end += 3;
    }
    return accountNumber;
  }

  @Override
  public String toString() {
    return Arrays.stream(accountNumber)
        .map(SevenSegmentNumber::getInt)
        .map(Object::toString)
        .collect(Collectors.joining());
  }
}
