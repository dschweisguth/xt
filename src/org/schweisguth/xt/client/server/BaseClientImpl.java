package org.schweisguth.xt.client.server;

import org.schweisguth.xt.common.game.ListenerManager;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.domain.Player;

public abstract class BaseClientImpl implements Client {
    // Fields
    private String mPlayer;
    private final ListenerManager mListenerManager = new ListenerManager();

    // Methods: for subclasses

    protected BaseClientImpl(String pPlayer) {
        setPlayer(pPlayer);
    }

    protected final void setPlayer(String pPlayer) {
        Player.assertIsValid(pPlayer);
        mPlayer = pPlayer;
    }

    // Methods: for clients

    public String getPlayer() {
        return mPlayer;
    }

    public boolean playerIs(String pPlayer) {
        return mPlayer.equals(pPlayer);
    }

    public void addListener(ListenerOperations pListener) {
        mListenerManager.addListener(pListener);
    }

    public void removeListener(ListenerOperations pListener) {
        mListenerManager.removeListener(pListener);
    }

    protected ListenerManager getListenerManager() {
        return mListenerManager;
    }

}
