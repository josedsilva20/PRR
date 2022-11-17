package prr.core;

import java.util.*;

public class FriendTerminalByIdComparator implements Comparator<Terminal>{
    public int compare(Terminal t1, Terminal t2) {
        return t1.getId().compareTo(t2.getId());
    }
}
