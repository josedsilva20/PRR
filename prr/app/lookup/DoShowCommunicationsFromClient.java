package prr.app.lookup;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownClientKeyException;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

  DoShowCommunicationsFromClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
    addStringField("client", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException {
    String clientId = stringField("client");
    try {
      _display.addLine( _receiver.showClientMadeCommunications(clientId));
      _display.display();
    }
    catch (InvalidIdException iie){
      throw new UnknownClientKeyException(clientId);
    }
  }
}
