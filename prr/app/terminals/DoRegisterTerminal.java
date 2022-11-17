package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.core.exception.ExistingTermKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.exception.InvalidIdException;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.DuplTerminalKeyException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    //FIXME add command fields
    addStringField("terminalId", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "FANCY", "BASIC");
    addStringField("owner", Message.clientKey());
  }

  @Override
  protected final void execute() throws IllegalArgumentException,//
        CommandException, DuplicateTerminalKeyException {
    //FIXME implement command
    String terminalId = stringField("terminalId");
    String type = optionField("type");
    String owner = stringField("owner");

    try {
      _receiver.registerTerminal(terminalId, type, owner);
    }
    catch (ExistingTermKeyException | DuplTerminalKeyException etke){
      throw new DuplicateTerminalKeyException(terminalId);
    }
    catch (InvalidIdException iie){
      throw new InvalidTerminalKeyException(terminalId);
    }
    catch (IllegalArgumentException iae){
      throw new UnknownClientKeyException(owner);
    }
  }
}
