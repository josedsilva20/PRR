package prr.app.terminals;
 
import prr.core.Network;
import prr.core.NetworkManager;
import prr.core.Terminal;
import prr.app.terminal.*;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("terminalKey", Message.terminalKey());
    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    //FIXME implement command
    // create an instance of prr.app.terminal.Menu with the
    // selected Terminal and open it
    String terminalKey = stringField("terminalKey");
    try {
      Terminal terminal = _receiver.getTerminalById(terminalKey);
      (new prr.app.terminal.Menu(_receiver, terminal)).open();
    } catch (InvalidIdException iie) {
      throw new UnknownTerminalKeyException(terminalKey);
    }
  }
}
