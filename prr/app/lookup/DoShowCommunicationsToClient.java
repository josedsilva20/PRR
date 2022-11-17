package prr.app.lookup;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownClientKeyException;

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    addStringField("clientKey", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    String clientKey = stringField("clientKey");
    try {
      _display.addLine(_receiver.showClientReceivedCommunications(clientKey));
      _display.display();
    } catch (InvalidIdException e) {
        throw new UnknownClientKeyException(clientKey);
    }
  }
}
