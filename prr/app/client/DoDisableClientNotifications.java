package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.exception.ClientNotificationException;
import prr.core.exception.InvalidIdException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException {
    //FIXME implement command
    String key = stringField("key");

    try {
      _receiver.disableClientNotifications(key);
    } catch(ClientNotificationException cne) {
      _display.addLine(Message.clientNotificationsAlreadyDisabled());
      _display.display();
    }
    catch(InvalidIdException iie){
      throw new UnknownClientKeyException(key);
    }
  }
}