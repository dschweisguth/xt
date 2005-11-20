package org.schweisguth.xt.client.rackimpl;

import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.observable.ObserverManager;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.util.contract.Assert;

class OtherPlayerRackModel implements PlayerRackModel {
    // Fields
    private Rack mRack;
    private final ObserverManager mObserverManager = new ObserverManager();

    // Constructors

    public OtherPlayerRackModel() {
        setRack(new Rack());
    }

    // Methods: implements PlayerRackModel

    public Rack getRack() {
        return mRack;
    }

    public final void setRack(Rack pRack) {
        Assert.assertNotNull(pRack);

        mRack = pRack;
        mObserverManager.update();

    }

    // Methods: implements Observable

    public void addObserver(Observer pObserver) {
        mObserverManager.addObserver(pObserver);
    }

}
