package org.schweisguth.xt.common.server;

import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.game.Request;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    String NAME = "Game";

    Game getGame() throws RemoteException;

    void execute(Request pRequest) throws RemoteException;

    void addListener(String pPlayer, ListenerOperations pListener)
        throws RemoteException;

    void removeListener(ListenerOperations mListener) throws RemoteException;

}
