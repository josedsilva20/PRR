package prr.core;

import java.io.Serializable;
import java.io.IOException;
import prr.core.exception.InvalidIdException;
import prr.core.exception.UnrecognizedEntryException;
import java.util.*;
import java.io.Serializable;
import prr.core.exception.ExistingTermKeyException;
import prr.core.exception.DuplTerminalKeyException;
import prr.core.exception.ClientNotificationException;
import prr.core.exception.TerminalIsOffException;
import prr.core.exception.TerminalIsBusyException;
import prr.core.exception.UnsopportedFromComException;
import prr.core.exception.UnsopportedComToException;
import prr.core.exception.SendNotificationException;
import prr.core.exception.DestinatioOffException;
import prr.core.exception.TerminalIsSilenteException;


/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

   // FIXME define attributes
  private List<Client> _clients;
  private List<Communication> _communications;
  private List<Terminal> _terminals;
  private Parser _parser;
  private int _commsId;


  // FIXME define contructor(s)
  public Network(){
      _clients = new ArrayList<>();
      _communications = new ArrayList<>();
      _terminals = new ArrayList<>();
      _commsId = 0;
  }

  //________________________________________________________________
  // Client code

  public List<String> viewAllClients(){
    List<String> clients = new ArrayList<>();
    for (Client client : _clients){
      clients.add(client.toString());
    }
    return clients;
  }

  public String viewClient(String id) throws InvalidIdException{
    Client client = getClientById(id);
    if (client.getKey() == null)
      throw new InvalidIdException();
    return client.toString();
  }

  public List<String> clientNotifications(String id) {
    Client client = getClientById(id);
    List<String> notifications = new ArrayList<>();
    for (Notification not : client.getNotifications()){
      notifications.add(not.toString());
    }
    client.removeNotifications();
    return notifications;

  }
  

  public void registerClient(String key, String name, int taxNumber) throws ExistingTermKeyException{
    Client cl = new Client(key, name, taxNumber);
    for (Client client : _clients)
      if (client.equals(cl))
        throw new ExistingTermKeyException();
    _clients.add(cl);
  }

  public void disableClientNotifications(String id) throws ClientNotificationException, InvalidIdException{
    Client cl = getClientById(id);
    if (cl.getKey() == null) 
      throw new InvalidIdException();
    if (!cl.isReceiverActive())
      throw new ClientNotificationException();
    cl.disableNotifications();
  }

  public void activateClientNotifications(String id) throws InvalidIdException, ClientNotificationException{
    Client cl = getClientById(id);
    if (cl.getKey() == null) 
      throw new InvalidIdException();
    if (cl.isReceiverActive())
      throw new ClientNotificationException();
    cl.activateNotifications();
  }
  public Client getClientById(String id) {
    Client cl = new Client();
    for (Client client : _clients){
      if (client.getKey().equals(id))
        return client;
    }
    return cl;
  }
  
  public long getClientPayments(String id) throws InvalidIdException{
    Client cl =  getClientById(id);
    if (cl.getKey() == null)
      throw new InvalidIdException();
    return cl.getPayments();
  }

  public long getClientDebts(String id) throws InvalidIdException{
    Client cl =  getClientById(id);
    if (cl.getKey() == null)
      throw new InvalidIdException();
    return cl.getDebts();
  }

  public List<String> getClientsBalanceStatus(String type){
    List<String> clients = new ArrayList<>();
    for(Client c : _clients){
      if (type.equals("negative") && (c.getPayments() - c.getDebts()) < 0)
        clients.add(c.toString());
      if(type.equals("positive") && (c.getPayments() - c.getDebts() >= 0))
        clients.add(c.toString());
    } 

    return clients;
  }


  //________________________________________________________________
  // Terminals/Terminal code

  /*public List<Terminal> getTerminals(){
    List<Terminal> ret = new ArrayList<>(_terminals);
    return ret;
  }*/

  public List<String> showAllTerminals(){
    List<String> terminals = new ArrayList<>();
    for (Terminal t : _terminals){
      terminals.add(t.toString());
    }
    return terminals;
  }


  public Terminal getTerminalByIdAux(String id) {
    Terminal aux = new Terminal();
    for (Terminal t : _terminals){
      if (t.getId().equals(id))
        return t;
    }
    return aux;
  }

  public Terminal getTerminalById(String id) throws InvalidIdException{
    if (getTerminalByIdAux(id).getId() == null)
      throw new InvalidIdException();
    return getTerminalByIdAux(id);
  }

  public void registerTerminal(String id, String type, String ownerId) //
  throws InvalidIdException, IllegalArgumentException, //
  ExistingTermKeyException, DuplTerminalKeyException{
    Client owner = getClientById(ownerId);
    Terminal terminal = new Terminal(id, owner, type);

    for(Terminal t : _terminals)
      if (t.equals(terminal)) 
        throw new ExistingTermKeyException();

    if (getClientById(ownerId).getKey() == null)
      throw new IllegalArgumentException();

    else if (id.length() != 6)
      throw new InvalidIdException();

    _terminals.add(terminal);
    owner.addTerminal(terminal);
  }

  public void addFriend(Terminal terminal, String id)  throws InvalidIdException{
    if (!_terminals.contains(getTerminalById(id)))
      throw new InvalidIdException();
    if (!terminal.getOrderedFriends().contains(getTerminalById(id)))
      terminal.addFriendTerminal(getTerminalById(id));
  }

  public void removeFriend(Terminal terminal, String id)  throws InvalidIdException{
    if (!_terminals.contains(getTerminalById(id)))
      throw new InvalidIdException();
    terminal.removeFriendTerminal(getTerminalById(id));
  }

  public List<String> getTerminalsWithPositiveBalance(){
    List<String> terminals = new ArrayList<>();
    for(Terminal t : _terminals){
      if ((t.getPayments() - t.getDebts()) > 0)
        terminals.add(t.toString());
    }
    return terminals;
  }

  public List<String> getUnusedTerminals(){
    List<String> terminals = new ArrayList<>();
    for(Terminal t : _terminals){
      if (t.getMadeCommunications() == 0 && t.getReceivedCommunications() == 0)
        terminals.add(t.toString());
    }
    return terminals;
  }
  public void silenceTerminal(Terminal terminal) throws InvalidIdException{
    if (terminal.isSilente())
      throw new InvalidIdException();
    terminal.setSilence();
      //end communication

  }

  public void turnOffTerminal(Terminal terminal) throws InvalidIdException{
    if (terminal.isOff())
      throw new InvalidIdException();
    
    if (terminal.isIdle()) {
      terminal.setOff();
    }
    if (terminal.isSilente()) {
      terminal.setOff();
    }
  }

  public void turnOnTerminal(Terminal terminal) throws InvalidIdException{
    if (terminal.isOn())
      throw new InvalidIdException();
    terminal.setOn();
    //terminal.setIdle();
  }

  public void idleTerminal(Terminal terminal) throws InvalidIdException{
    if (terminal.isIdle())
      throw new InvalidIdException();
    terminal.setIdle();
  }
  
  public void sendTextMessage(Terminal from, String to, String msg) throws InvalidIdException, SendNotificationException {
    Communication com;
    try {
      Terminal t = getTerminalById(to);
      com = from.sendTextCommunication(t, msg, _communications.size()+1);
    }
    catch(InvalidIdException iie){
      throw iie;
    }
    //-------
    catch(SendNotificationException sne){
      if (from.getClient().isReceiverActive())
        getTerminalById(to).registerObserver(from.getClient());
      throw sne;
    }
    _communications.add(com);
  }

  public List<String> showAllCommunications(){
    List<String> lst = new ArrayList<String>();
    for (Communication c : _communications){
      lst.add(c.toString());
    }
    return lst;
  }

  public void startInteractiveCommunication(String type,String from, String to)throws InvalidIdException, TerminalIsSilenteException,//
  TerminalIsBusyException, UnsopportedComToException, UnsopportedFromComException, InvalidIdException,
  DestinatioOffException{
    
    try{
      Terminal fr = getTerminalById(from);
      Terminal t = getTerminalById(to);
    }
    
    catch(InvalidIdException iie){
      throw iie;
    }

    if(getTerminalById(to).isBusy() && getTerminalById(from).getClient().isReceiverActive()) {
      getTerminalById(to).registerObserver(getTerminalById(from).getClient());
      throw new TerminalIsBusyException();
    }
    
    else if(getTerminalById(to).isOff() && getTerminalById(from).getClient().isReceiverActive()) {
      getTerminalById(to).registerObserver(getTerminalById(from).getClient());
      throw new DestinatioOffException();
    }

    else if(getTerminalById(to).isSilente() && getTerminalById(from).getClient().isReceiverActive()){
      getTerminalById(to).registerObserver(getTerminalById(from).getClient());
      throw new TerminalIsSilenteException();
    }

    if (type.equals("VOICE"))
      startVoiceCall(getTerminalById(from), getTerminalById(to));
    else{
      if(!(getTerminalById(to).getType().equals("FANCY")))
        throw new UnsopportedComToException();
        if (!(getTerminalById(from).getType().equals("FANCY")))
          throw new UnsopportedFromComException();
      startVideoCall(getTerminalById(from), getTerminalById(to));
    }
  }


  public void startVoiceCall(Terminal from, Terminal to) {
    from.saveMode();
    to.saveMode();
    from.setBusy();
    to.setBusy();
    VoiceCommunication v = new VoiceCommunication(_communications.size()+1, from, to);
    v.setStatus("ONGOING");
    from.addMadeCommunication(v);
    from.activateMadeCom();
    to.addReceivedCommunication(v);
    _communications.add(v);
  }

  public void startVideoCall(Terminal from, Terminal to) {
    from.saveMode();
    to.saveMode();
    from.setBusy();
    to.setBusy();
    VideoCommunication v = new VideoCommunication(_communications.size()+1, from, to);
    v.setStatus("ONGOING");
    from.addMadeCommunication(v);
    from.activateMadeCom();
    to.addReceivedCommunication(v);
    _communications.add(v);
    from.getClient().iterateVideoCount();
  }

  /*public Communication getCommunicationByIdAux(int id) {
    VideoCommunication  aux = new VideoCommunication();
    for (Communication t : _communications){
      if (t.getId() == id)
        return t;
    }
    return aux;
  }

  public Communication getCommunicationById(int id) throws InvalidIdException{
    if (getCommunicationByIdAux(id).getId() == 0)
      throw new InvalidIdException();
    return getCommunicationByIdAux(id);
  }*/

  public boolean hasOngoing(Terminal t){
    for (Communication c : _communications){
      if (c.getTerminalFrom().equals(t) && c.isOngoing())
        return true;
    }
    return false;
  }

  public String getOngoingCommunication(Terminal t) throws InvalidIdException 
  {
    if (!hasOngoing(t))
      throw new InvalidIdException();
    String aux = "";
    for (Communication c : _communications){
      if (c.getTerminalFrom().equals(t) && c.isOngoing()){
        aux += c.toString();
        break;
      }
    }
    return aux;
  }
  public int getOngoingCommunicationId(Terminal t)
  {
    int aux = 0;
    for (Communication c : _communications){
      if (c.getTerminalFrom().equals(t) && c.isOngoing()){
        aux = c.getId();
        break;
      }
    }
    return aux;
  }

  public Communication getOngoingCommunicationFromTerm(Terminal t)
  {
    for (Communication c : _communications){
      if (c.getTerminalFrom().equals(t) && c.isOngoing()){
        return c;
      }
    }
    return null;
  }

  public long endOngoingCommunication(Terminal from, int duration) 
  {
    boolean aux = false;
    long cost = 0;
    for (Communication c : _communications){
      //cost = 10;
      if (c.getTerminalFrom() == from && c.isOngoing()){
        aux = true;
        c.setDuration(duration);
        c.setStatus("FINISHED");
        c.setUnits(duration);
        if (c.areFriends(from, c.getTerminalTo()))
          cost = Math.round(c.computeCost(c.getTerminalFrom().getClient().getClientLevel(), duration) / 2);
        else
          cost = Math.round(c.computeCost(c.getTerminalFrom().getClient().getClientLevel(), duration));
        from.loadMode();
        from.addDebt(cost);
        c.setCost(cost);
        from.disableMadeCom();
          /*for (Terminal t : _terminals){
            if (t == from || t == c.getTerminalTo()) {
              //cost += 10;
              idleTerminal(t);
            }
          }*/
          c.getTerminalTo().loadMode();//catch (InvalidIdException iie) {}
      }  
    }
    //if (!aux)
      //throw new InvalidIdException();
    return cost;
  }

  /*________________________________________________________________________*/
        //  GLOBAL STATUS

  public long showGlobalPayments(){
    long balance = 0;
    for (Client client : _clients){
      balance += client.getPayments();
    }
    return balance;
  }

  public long showGlobalDebts(){
    long balance = 0;
    for (Client client : _clients){
      balance += client.getDebts();
    }
    return balance;
  }

  public String showClientMadeCommunications(String id) throws InvalidIdException {
    String aux = "";
    Client client = getClientById(id);
    if (client.getKey() == null)
      throw new InvalidIdException();
    List<String> madeCommunications = client.getMadeCommunications();
    for (String communication : madeCommunications) {
      aux += communication;
    }
    return aux;
  }

  public String showClientReceivedCommunications(String id) throws InvalidIdException {
    String aux = "";
    Client client = getClientById(id);
    if (client.getKey() == null)
      throw new InvalidIdException();
    List<String> receivedCommunications = client.getReceivedCommunications();
    for (String communication : receivedCommunications) {
      aux += communication;
    }
    return aux;
  }

  public void payCommunication(Terminal from, int commId)  throws InvalidIdException {
    boolean hasCommunication = false;
    Communication communication = new VoiceCommunication();
    for (Communication c : _communications){
      if (c.getId() == commId)
        hasCommunication = true;
        communication = c;
    }
    if (!hasCommunication)
      throw new InvalidIdException();
    try {
      from.pay(communication);
    } catch (InvalidIdException iie) {
        throw iie;
    }
  }


  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    Parser parse = new Parser(this);
    parse.parseFile(filename);
  }

}