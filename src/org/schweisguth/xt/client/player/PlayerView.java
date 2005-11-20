package org.schweisguth.xt.client.player;

import java.awt.Color;
import javax.swing.BorderFactory;
import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.util.CenteredLabel;
import org.schweisguth.xt.common.util.contract.Assert;

class PlayerView extends CenteredLabel implements Observer {
    // Fields
    private PlayerModel mModel = null;

    // Constructors

    public PlayerView(PlayerModel pModel) {
        Assert.assertNotNull(pModel);
        setModel(pModel);
    }

    // Methods: other

    public final void setModel(PlayerModel pModel) {
        Assert.assertNotNull(pModel);
        if (mModel != null) {
            mModel.removeObserver(this);
        }
        mModel = pModel;
        mModel.addObserver(this);
        update();
    }

    public PlayerModel getModel() {
        return mModel;
    }

    // Methods: helper

    public void update() {
        setText(mModel.getPlayer());
        if (mModel.getIsTurn()) {
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        } else {
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

}
