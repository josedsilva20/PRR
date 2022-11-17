package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.Communication;
import prr.core.exception.SendNotificationException;
/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    addIntegerField("duration", Message.duration());
  }
  
  @Override
  protected final void execute() throws CommandException {
    int duration = integerField("duration");
      //_network.getOngoingCommunicationId(_receiver)
      int cost = (int) _network.endOngoingCommunication(_receiver, duration);
      _display.addLine(Message.communicationCost(cost));
    _display.display();
  }
}
