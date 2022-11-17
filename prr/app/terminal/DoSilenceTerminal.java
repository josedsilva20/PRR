package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

import prr.core.exception.InvalidIdException;
import prr.core.exception.SendNotificationException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {
  DoSilenceTerminal(Network context, Terminal terminal) {
    super(Label.MUTE_TERMINAL, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.silenceTerminal(_receiver);
    } catch (InvalidIdException iie){
      _display.addLine(Message.alreadySilent());
      _display.display();
    }
  }
}