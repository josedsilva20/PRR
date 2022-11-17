package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.exception.InvalidIdException;
import prr.core.exception.SendNotificationException;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    try {
      _network.idleTerminal(_receiver);
    } catch (InvalidIdException iie){
      _display.addLine(Message.alreadyOn());
      _display.display();
    }
  }
}