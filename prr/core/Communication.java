 package prr.core;

import java.io.Serializable;
import java.util.List;
import prr.core.exception.DuplTerminalKeyException;

public abstract class Communication implements Serializable{
	private int _id;
	private Terminal _from;
	private Terminal _to;
	private int _units;
	double _cost;
	boolean _isOngoing;
	String _type;
	String _message;
	String _status;

	//id comes from position in array of Communications in Network.java
	public Communication(int id, Terminal from, Terminal to, String type, int units){
		_id = id;
		_from = from;
		_to = to;
		_type = type;
		_status = "FINISHED";
		_units = units;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public Communication(){

	}

	public boolean isOngoing(){
		return _status == "ONGOING";
	}

	public String getType(){
		return _type;
	}

	public void makeSMS(Terminal to, String message)  throws DuplTerminalKeyException {
		if (to.isBusy())
			throw new DuplTerminalKeyException();
		_message = message;
	}

	protected void acceptSMS(Terminal from){

	}

	public void makeVoiceCall(Terminal to){}

	//public String toString(){}

	public boolean isInteractive(){
		return this instanceof VideoCommunication v || this instanceof VoiceCommunication c;
	}

	public int getId(){
		return _id++;
	}

	public boolean equals(Communication c1){
		return _id == c1.getId();
	}

	public String toString(){
		//type|idCommunication|idSender|idReceiver|units|price|status
		return _type + "|" + _id + "|" + _from.getId() + "|" + _to.getId() + "|" + _units + "|" + (int) _cost + "|" + _status;
	}
	
	public void setCost(double cost) {
		_cost = cost;
	}
	public void setUnits(int unit) {
		_units += unit;
	}

	public Terminal getTerminalFrom() {
		return _from;
	}

	public Terminal getTerminalTo(){
		return _to;
	}

	public void setDuration(int duration) {
		
	}

	public long getCost(){
		return Math.round(_cost);
	}

	public boolean areFriends(Terminal from, Terminal to){
		return from.getOrderedFriends().contains(to);
	}
	
	protected abstract double computeCost(String plan, int duration);

	protected abstract int getSize();
}