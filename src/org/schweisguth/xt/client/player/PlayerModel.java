package org.schweisguth.xt.client.player;

import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.observable.ObserverManager;
import org.schweisguth.xt.common.util.contract.Assert;

class PlayerModel {
    // Fields
    private final String mPlayer;
    private boolean mIsTurn = false;
    private final ObserverManager mObserverManager = new ObserverManager();

    // Constructors

    public PlayerModel() {
        this("");
    }

    public PlayerModel(String pPlayer) {
        Assert.assertNotNull(pPlayer); // Can be ""
        mPlayer = pPlayer;
    }

    // Methods: implements Observable

    public void addObserver(Observer pObserver) {
        mObserverManager.addObserver(pObserver);
    }

    public void removeObserver(Observer pObserver) {
        mObserverManager.removeObserver(pObserver);
    }

    // Methods: other

    public String getPlayer() {
        return mPlayer;
    }

    public boolean getIsTurn() {
        return mIsTurn;
    }

    public void setIsTurn(boolean pIsTurn) {
        if (mIsTurn != pIsTurn) {
            mIsTurn = pIsTurn;
            mObserverManager.update();
        }
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        PlayerModel other = (PlayerModel) pOther;
        return other.getPlayer().equals(getPlayer()) &&
            other.getIsTurn() == getIsTurn();
    }

    public int hashCode() {
        return 3 * getPlayer().hashCode() + new Boolean(getIsTurn()).hashCode();
    }

    public String toString() {
        return
            "PlayerModel(" + mPlayer + ", " + (mIsTurn ? "true" : "false") + ")";
    }

}
