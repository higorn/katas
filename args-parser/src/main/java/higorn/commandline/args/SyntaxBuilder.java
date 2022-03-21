package higorn.commandline.args;

import higorn.commandline.args.ArgsSyntax.Argument;
import higorn.commandline.args.ArgsSyntax.Option;
import higorn.commandline.args.ArgsSyntax.SyntaxError;

import static higorn.commandline.args.ArgsSyntax.SyntaxError.Type.OPTION;
import static higorn.commandline.args.ArgsSyntax.SyntaxError.Type.SYNTAX;

public class SyntaxBuilder implements Builder {
  private final ArgsSyntax optSyntax = new ArgsSyntax();
  private       Option     option;
  private       String     parsedName;

  public ArgsSyntax getArgsSyntax() {
    return optSyntax;
  }

  public void setName(String name) {
    parsedName = name;
  }

  public void addOptionWithName() {
    option = new Option(parsedName, null);
    optSyntax.opts.put(parsedName, option);
  }

  @Override
  public void setOptionValue() {
    option.value = parsedName.equals(option.name) ? null : parsedName;
  }

  @Override
  public void addArgument() {
    optSyntax.args.add(new Argument(parsedName));
  }

  @Override
  public void done() {
    optSyntax.done = true;
  }

  @Override
  public void syntaxError(String token, int pos) {
    optSyntax.errors.add(new SyntaxError(SYNTAX, "Invalid symbol '" + token + "'", pos));
    done();
  }

  @Override
  public void optionError(ParserState state, ParserEvent event, int pos) {
    optSyntax.errors.add(new SyntaxError(OPTION, state + "|" + event, pos));
    done();
  }
}
