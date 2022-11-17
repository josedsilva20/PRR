package prr.core;

import prr.core.NotificationType;
import java.io.Serializable;

public class Notification implements Serializable{
	private NotificationType _type;
	private Terminal _notifyingTerminal;

	public Notification(Terminal notifying, NotificationType type){
		_type = type;
		_notifyingTerminal = notifying;
	}

	public NotificationType getTypeNotification(){
		return _type;
	}

	@Override
	public String toString() {
		return _type + "|" + _notifyingTerminal.getId();
	}
}