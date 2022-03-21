package higorn.commandline.args;

public enum ParserState {
  OPTION, OPTION_NAME, OPTION_VALUE, END, ARGUMENT, NO_OPTION
}
