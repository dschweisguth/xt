package org.schweisguth.xt.common.game;

import java.rmi.RemoteException;

public interface ListenerOperations {
    void send(Event pEvent) throws RemoteException;
}
