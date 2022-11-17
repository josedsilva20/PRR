package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {
  Terminal term;
  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
    term = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    _display.addLine(Message.terminalPaymentsAndDebts(term.getId(),//
    term.getPayments(), term.getDebts()));
    _display.display();
  }
}
