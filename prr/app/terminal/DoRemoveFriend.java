package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownTerminalKeyException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {
  Terminal term;
  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    term = terminal;
    addStringField("friendTerminal", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String id = stringField("friendTerminal");
    try {
      _network.removeFriend(_receiver, id);
    } 
    catch (InvalidIdException iie) {
      throw new UnknownTerminalKeyException(id);
    }
  }
}
