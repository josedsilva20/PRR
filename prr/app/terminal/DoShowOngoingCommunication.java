package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.core.exception.UnrecognizedEntryException;
//FIXME add more imports if needed

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _display.addLine(_network.getOngoingCommunication(_receiver));
    }
    catch (InvalidIdException iie){
      _display.addLine(Message.noOngoingCommunication());
    }
    _display.display();
  }
}
