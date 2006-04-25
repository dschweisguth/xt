package org.schweisguth.xt.client.rackimpl;

import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.util.CenteredLabel;
import org.schweisguth.xt.common.util.contract.Assert;

// TODO make a rack view that shows tiles from the back

class OtherPlayerRackView extends CenteredLabel
    implements PlayerRackView, Observer {
    private final OtherPlayerRackModel mModel;

    public OtherPlayerRackView(OtherPlayerRackModel pModel) {
        Assert.assertNotNull(pModel);
        mModel = pModel;
        mModel.addObserver(this);
        update();
    }

    // Methods: implements PlayerRackView

    public PlayerRackModel getPlayerRackModel() {
        return mModel;
    }

    // Methods: implements Observer

    public final void update() {
        int tileCount = mModel.getRack().getTileCount();
        setText(tileCount + " tile" + (tileCount == 1 ? "" : "s"));
    }

}
