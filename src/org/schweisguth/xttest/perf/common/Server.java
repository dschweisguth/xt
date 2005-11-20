package org.schweisguth.xttest.perf.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void send(Object object) throws RemoteException;
}
