package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.SendNotificationException;
import prr.core.exception.InvalidIdException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("termKey", Message.terminalKey());
    addStringField("msg", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String msg = stringField("msg");
    String termKey = stringField("termKey");

    try {
      _network.sendTextMessage(_receiver, termKey, msg);
    } catch (InvalidIdException iie) {
        throw new UnknownTerminalKeyException(termKey);
    }
    catch (SendNotificationException sne) {
      _display.addLine(Message.destinationIsOff(termKey));
      _display.display();
  }
  }
} 
