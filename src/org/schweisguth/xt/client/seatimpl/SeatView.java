package org.schweisguth.xt.client.seatimpl;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import org.schweisguth.xt.client.util.JBox;
import org.schweisguth.xt.common.util.contract.Assert;

class SeatView extends JBox {
    public SeatView(Component pPlayerView, Component pRackView) {
        super(BoxLayout.Y_AXIS);
        Assert.assertNotNull(pRackView);

        add(pPlayerView);
        add(Box.createRigidArea(new Dimension(5, 5)));
        add(pRackView);

    }

    public void setRackView(Component pRackView) {
        Assert.assertNotNull(pRackView);
        remove(2);
        add(pRackView);
    }

}
