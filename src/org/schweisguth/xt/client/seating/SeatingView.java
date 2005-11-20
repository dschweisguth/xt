package org.schweisguth.xt.client.seating;

import java.awt.Component;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.schweisguth.xt.client.util.JBox;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.Assert;

class SeatingView extends JBox {
    public SeatingView(Component pCenterView, List pSeatViews) {
        super(BoxLayout.Y_AXIS);
        Assert.assertNotNull(pSeatViews);
        Assert.assertEquals(4, pSeatViews.size());
        Assert.assertTrue(
            CollectionUtil.containsOnlyInstancesOf(pSeatViews, Component.class));
        Assert.assertNotNull(pCenterView);

        JPanel boardAndSides = JBox.createHorizontalBox();
        boardAndSides.add((Component) pSeatViews.get(1));
        boardAndSides.add(pCenterView);
        boardAndSides.add((Component) pSeatViews.get(3));

        add((Component) pSeatViews.get(2));
        add(boardAndSides);
        add((Component) pSeatViews.get(0));

    }
}
