package org.schweisguth.xt.client.boxlid;

import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.observable.ObserverManager;
import org.schweisguth.xt.common.domain.BoxLid;

public class BoxLidModel {
    // Fields
    private final ObserverManager mObserverManager = new ObserverManager();
    private int mTileCount;

    // Constructors

    public BoxLidModel(int pTileCount) {
        setTileCount(pTileCount);
    }

    // Methods: implements Observable

    public void addObserver(Observer pObserver) {
        mObserverManager.addObserver(pObserver);
    }

    // Methods: other

    public int getTileCount() {
        return mTileCount;
    }

    public final void setTileCount(int pTileCount) {
        BoxLid.assertTileCountIsValid(pTileCount);
        mTileCount = pTileCount;
        mObserverManager.update();
    }

}
