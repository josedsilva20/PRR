package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import prr.core.Client;

import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownClientKeyException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("clientKey", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException {
    String clientKey = stringField("clientKey");
    try{
      _display.addLine(Message.clientPaymentsAndDebts(clientKey, //
                                                    _receiver.getClientPayments(clientKey), //
                                                    _receiver.getClientDebts(clientKey))
                                                    );
      _display.display();
    }
    catch(InvalidIdException iie){
      throw new UnknownClientKeyException(clientKey);
    }
  }
}
