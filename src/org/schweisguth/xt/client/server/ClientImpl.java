package org.schweisguth.xt.client.server;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.game.*;
import org.schweisguth.xt.common.server.Server;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// TODO ensure that every command has a listener?
// TODO Measure speed and latency to Grandma's machine
// TODO look for a Java update for Grandma's computer
// TODO investigate lease period
// TODO attempt to reconnect after errors
// TODO reduce size of serialized objects
// TODO local message path?
// TODO selective message delivery?
// TODO time out stuck events
// TODO thread events?
// TODO batch events, which might mean separating state changes and messages
// TODO consider sequence-numbering events (but they're ordered anyway)
// TODO optimize game serialization; consider a diff object
// TODO optimize hashCode calculation

public class ClientImpl extends BaseClientImpl {
    // Fields
    private final ListenerOperations mListener;
    private Server mServer;
    private Game mGame = null;
    private final Object mGameLock = new Object();

    // Constructors

    public ClientImpl() throws RemoteException {
        super("not logged in yet");
        mListener = new ForwardingListener();
    }

    private class ForwardingListener extends UnicastRemoteObject
        implements RemoteListener {
        private ForwardingListener() throws RemoteException {
        }

        public void send(Event pEvent) {
            synchronized (mGameLock) {
                mGame = pEvent.getGame();
                try {
                    getListenerManager().send(pEvent);
                } catch (Error e) {
                    logSendError(e);
                    throw e;
                } catch (RuntimeException e) {
                    logSendError(e);
                    throw e;
                }
            }
        }

        private void logSendError(Throwable e) {
            Logger.global.log(Level.SEVERE, "Couldn't send event", e);
        }

    }

    // Methods

    public void logIn(Server pServer, String pPlayer) throws RemoteException {
        Assert.assertNotNull(pServer);
        setPlayer(pPlayer);
        mServer = pServer;
        pServer.addListener(getPlayer(), mListener);
    }

    public Game getGame() throws RemoteException {
        synchronized (mGameLock) {
            if (mGame == null) {
                mGame = mServer.getGame();
            }
            return mGame;
        }
    }

    public SetList getPlayers() throws RemoteException {
        return getGame().getPlayers();
    }

    public boolean canExecute(Command pCommand) throws RemoteException {
        return getGame().canExecute(new Request(getPlayer(), pCommand));
    }

    public void execute(Command pCommand) throws RemoteException {
        mServer.execute(new Request(getPlayer(), pCommand));
    }

    public void disconnect() throws RemoteException {
        mServer.removeListener(mListener);
    }

    public void sendRefreshEvent() throws RemoteException {
        getListenerManager().send(new RefreshEvent(getGame()));
    }

}
