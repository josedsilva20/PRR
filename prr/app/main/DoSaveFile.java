package prr.app.main;

import prr.core.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed
import java.io.IOException;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.menus.CommandException;
import prr.app.exception.FileOpenFailedException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {
  private String filename;

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command and create a local Form
    if (!(_receiver.hasFile())) {

      Form request = new Form();
      request.addStringField("answer", Message.newSaveAs());
      filename = request.parse().stringField("answer");

      try {
        _receiver.saveAs(filename);
      } catch (MissingFileAssociationException mfae) {
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    else{
      try {
        _receiver.save();
      } catch (MissingFileAssociationException mfae) {

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
