package higorn.commandline.args;

import java.util.function.Consumer;

public class Parser implements TokenCollector {
  private final Builder builder;
  private ParserState state = ParserState.NO_OPTION;

  public Parser(Builder builder) {
    this.builder = builder;
  }

  @Override
  public void dash(int pos) {
    handleEvent(ParserEvent.DASH, pos);
  }

  @Override
  public void name(String name, int pos) {
    builder.setName(name);
    handleEvent(ParserEvent.NAME, pos);
  }

  @Override
  public void error(String token, int pos) {
    builder.syntaxError(token, pos);
  }

  static class Transition {
    private final ParserState givenState;
    private final ParserEvent event;
    private final ParserState       newState;
    private final Consumer<Builder> action;

    Transition(ParserState givenState, ParserEvent event, ParserState newState, Consumer<Builder> action) {
      this.givenState = givenState;
      this.event = event;
      this.newState = newState;
      this.action = action;
    }
  }

  private final Transition[] transitions = new Transition[] {
    new Transition(ParserState.NO_OPTION, ParserEvent.DASH, ParserState.OPTION, null),
    new Transition(ParserState.NO_OPTION, ParserEvent.NAME, ParserState.ARGUMENT, Builder::addArgument),
    new Transition(ParserState.NO_OPTION, ParserEvent.EOF, ParserState.END, Builder::done),
    new Transition(ParserState.OPTION, ParserEvent.NAME, ParserState.OPTION_NAME, Builder::addOptionWithName),
    new Transition(ParserState.OPTION_NAME, ParserEvent.DASH, ParserState.OPTION, null),
    new Transition(ParserState.OPTION_NAME, ParserEvent.NAME, ParserState.OPTION_VALUE, Builder::setOptionValue),
    new Transition(ParserState.OPTION_NAME, ParserEvent.EOF, ParserState.END, Builder::done),
    new Transition(ParserState.OPTION_VALUE, ParserEvent.DASH, ParserState.OPTION, null),
    new Transition(ParserState.OPTION_VALUE, ParserEvent.NAME, ParserState.ARGUMENT, Builder::addArgument),
    new Transition(ParserState.OPTION_VALUE, ParserEvent.EOF, ParserState.END, Builder::done),
    new Transition(ParserState.ARGUMENT, ParserEvent.DASH, ParserState.OPTION, null),
    new Transition(ParserState.ARGUMENT, ParserEvent.NAME, ParserState.ARGUMENT, Builder::addArgument),
    new Transition(ParserState.ARGUMENT, ParserEvent.EOF, ParserState.END, Builder::done),
  };

  public void handleEvent(ParserEvent event, int pos) {
    for (Transition t : transitions) {
      if (t.givenState == state && t.event == event) {
        state = t.newState;
        if (t.action != null)
          t.action.accept(builder);
        return;
      }
    }
    builder.optionError(state, event, pos);
  }
}
