package org.schweisguth.xt.client.server;

import java.rmi.RemoteException;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.util.collection.SetList;

public interface Client {
    String getPlayer();

    boolean playerIs(String pPlayer);

    Game getGame() throws RemoteException;

    SetList getPlayers() throws RemoteException;

    boolean canExecute(Command pCommand) throws RemoteException;

    void execute(Command pCommand) throws RemoteException;

    void addListener(ListenerOperations pListener);

    void removeListener(ListenerOperations pListener);

    void disconnect() throws RemoteException;

    void sendRefreshEvent() throws RemoteException;
}
