package org.schweisguth.xttest.common.gameimpl.base;

import junit.framework.Assert;
import org.schweisguth.xt.client.server.BaseClientImpl;
import org.schweisguth.xt.client.server.RefreshEvent;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.util.collection.SetList;

public class LocalClient extends BaseClientImpl {
    // Fields
    private final Listener mListener = new ForwardingListener();
    private final ListenableGame mGame;

    // Constructors

    public LocalClient(ListenableGame pGame, String pPlayer) {
        super(pPlayer);
        Assert.assertNotNull(pGame);

        mGame = pGame;
        mGame.addListener(pPlayer, mListener);

    }

    private class ForwardingListener implements Listener {
        public void send(Event pEvent) {
            getListenerManager().send(pEvent);
        }
    }

    // Methods

    public Game getGame() {
        return mGame;
    }

    public SetList getPlayers() {
        return mGame.getPlayers();
    }

    public boolean canExecute(Command pCommand) {
        return mGame.canExecute(new Request(getPlayer(), pCommand));
    }

    public void execute(Command pCommand) {
        mGame.execute(new Request(getPlayer(), pCommand));
    }

    public void disconnect() {
        mGame.removeListener(mListener);
    }

    public void sendRefreshEvent() {
        getListenerManager().send(new RefreshEvent(getGame()));
    }

}
