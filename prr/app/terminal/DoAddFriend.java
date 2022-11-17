package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownTerminalKeyException;
//FIXME add more imports if needed

/**
  * Add a friend.
  */
class DoAddFriend extends TerminalCommand {
  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("friendTerminal", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    //FIXME implement command
    String id = stringField("friendTerminal");
    try {
      _network.addFriend(_receiver, id);
    } 
    catch (InvalidIdException iie) {
      throw new UnknownTerminalKeyException(id);
    }
  }
}
