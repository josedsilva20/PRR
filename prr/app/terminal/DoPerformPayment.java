package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("comKey", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    int comKey = integerField("comKey");
    try {
      _network.payCommunication(_receiver, comKey);
    }
    catch (InvalidIdException iie){
      _display.addLine(Message.invalidCommunication());
      _display.display();
    }
  }
}

