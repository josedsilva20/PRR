package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.Client;
import prr.core.exception.ExistingTermKeyException;
import java.util.*;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxId", Message.taxId());
  }
  
  @Override
  protected final void execute() throws CommandException, DuplicateClientKeyException{
    //FIXME implement command
    String key = stringField("key");
    String name = stringField("name");
    int taxId = integerField("taxId");

    try {
      _receiver.registerClient(key, name, taxId);
    } 
    catch (ExistingTermKeyException dcke) {
      throw new DuplicateClientKeyException(key);
    }
  }
}
