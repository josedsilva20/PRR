package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.exception.InvalidIdException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    try {
      _network.turnOffTerminal(_receiver);
    } catch (InvalidIdException iie){
      _display.addLine(Message.alreadyOff());
      _display.display();
    }
  }
}