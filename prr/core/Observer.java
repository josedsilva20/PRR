package prr.core;
import prr.core.exception.SendNotificationException;

public interface Observer {
    void update(Notification notification);
}
