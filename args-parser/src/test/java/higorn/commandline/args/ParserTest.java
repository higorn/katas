package higorn.commandline.args;

import higorn.commandline.args.ArgsSyntax.Argument;
import higorn.commandline.args.ArgsSyntax.Option;
import higorn.commandline.args.ArgsSyntax.SyntaxError;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static higorn.commandline.args.ArgsSyntax.SyntaxError.Type.OPTION;
import static higorn.commandline.args.ArgsSyntax.SyntaxError.Type.SYNTAX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

  private SyntaxBuilder builder;
  private Parser parser;
  private Lexer lexer;

  @BeforeEach
  void setUp() {
    builder = new SyntaxBuilder();
    parser = new Parser(builder);
    lexer = new Lexer(parser);
  }

  @Test
  void parseAnEmptyValueOption() {
    var opt = new Option();
    opt.name = "d";
    assertParseSingleOption("-d", opt);
  }

  @Test
  void parseAnOptionWithValue() {
    var opt = new Option();
    opt.name = "d";
    opt.value = "abc";
    assertParseSingleOption("-d abc", opt);
  }

  @Test
  void parseMultipleOptions() {
    assertParseMultipleOptions("-a -b cde -f AAa_234", Arrays.asList(
        new Option("a", null),
        new Option("b", "cde"),
        new Option("f", "AAa_234")
    ));
  }

  @Test
  void parseASingleArgument() {
    var arg = new Argument("abcd");
    assertParseSingleArg("abcd", arg);
  }

  @Test
  void parseMultipleArguments() {
    assertParseMultipleArgs("a ab abc abcd", new Argument[] {
        new Argument("a"),
        new Argument("ab"),
        new Argument("abc"),
        new Argument("abcd")
    });
  }

  @Test
  void parseMultipleOptionsAndArgs() {
    assertParseMultipleOptionsAndArgs("-a -b cde -f AAa_234 arg1 arg2", Arrays.asList(
        new Option("a", null),
        new Option("b", "cde"),
        new Option("f", "AAa_234")
    ), new Argument[] {
        new Argument("arg1"),
        new Argument("arg2")
    });
  }

  @Test
  void parseEmptyInput() {
    parse("");
    assertTrue(builder.getArgsSyntax().opts.isEmpty());
    assertTrue(builder.getArgsSyntax().args.isEmpty());
    assertTrue(builder.getArgsSyntax().errors.isEmpty());
    assertTrue(builder.getArgsSyntax().done);

    String[] strings = { "-a", "abc", "-b", "-c", "ddd" };
    System.out.println(Strings.join(strings).with(" "));
  }

  @Test
  void optionError() {
    assertParseError("-", new SyntaxError(OPTION, "OPTION|EOF", -1));
  }

  @Test
  void syntaxError() {
    assertParseError(".", new SyntaxError(SYNTAX, "Invalid symbol '.'", 1));
  }

  private void assertParseSingleOption(String input, Option expected) {
    parse(input);
    assertEquals(1, builder.getArgsSyntax().opts.size());
    assertEquals(expected.name, builder.getArgsSyntax().opts.get(expected.name).name);
    assertEquals(expected.value, builder.getArgsSyntax().opts.get(expected.name).value);
    assertTrue(builder.getArgsSyntax().done);
  }

  private void assertParseMultipleOptions(String input, List<Option> expected) {
    parse(input);
    assertEquals(expected.size(), builder.getArgsSyntax().opts.size());
    assertThat(expected.stream()
        .map(o -> o.name + ":" + o.value)
        .sorted()
        .collect(Collectors.toList())
        .toArray(String[]::new)
    ).containsExactly(builder.getArgsSyntax().opts.values().stream()
        .map(o -> o.name + ":" + o.value)
        .sorted()
        .collect(Collectors.toList())
        .toArray(String[]::new));
    assertTrue(builder.getArgsSyntax().done);
  }

  private void assertParseSingleArg(String input, Argument expected) {
    parse(input);
    assertEquals(1, builder.getArgsSyntax().args.size());
    assertTrue(builder.getArgsSyntax().args.contains(expected));
    assertTrue(builder.getArgsSyntax().done);
  }

  private void assertParseMultipleArgs(String input, Argument[] expected) {
    parse(input);
    assertEquals(expected.length, builder.getArgsSyntax().args.size());
    Arrays.sort(expected, Comparator.comparing(a -> a.name));
    Argument[] arguments = builder.getArgsSyntax().args.toArray(Argument[]::new);
    Arrays.sort(arguments, Comparator.comparing(a -> a.name));
    assertThat(expected).containsExactly(arguments);
    assertTrue(builder.getArgsSyntax().done);
  }

  private void assertParseMultipleOptionsAndArgs(String input, List<Option> expectedOptions,
      Argument[] expectedArgs) {
    assertParseMultipleOptions(input, expectedOptions);
    assertParseMultipleArgs(input, expectedArgs);
    assertTrue(builder.getArgsSyntax().done);
  }

  private void assertParseError(String input, SyntaxError expected) {
    parse(input);
    assertEquals(1, builder.getArgsSyntax().errors.size());
    var error = builder.getArgsSyntax().errors.get(0);
    assertEquals(expected.type, error.type);
    assertEquals(expected.msg, error.msg);
    assertEquals(expected.position, error.position);
    assertTrue(builder.getArgsSyntax().done);
  }

  private void parse(String input) {
    lexer.lex(input);
    parser.handleEvent(ParserEvent.EOF, -1);
  }
}
