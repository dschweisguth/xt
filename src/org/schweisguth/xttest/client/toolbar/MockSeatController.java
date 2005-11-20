package org.schweisguth.xttest.client.toolbar;

import javax.swing.event.TableColumnModelListener;
import junit.framework.Assert;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;
import org.schweisguth.xt.client.seat.SeatController;

class MockSeatController implements SeatController {
    // Fields
    private final MockClientPlayerRackView mClientPlayerRackView =
        new MockClientPlayerRackView();
    private boolean mHasClientPlayerRackView = false;

    // Methods: implements SeatController

    public void addClientPlayerRackListener(
        TableColumnModelListener pListener) {
        mClientPlayerRackView.addRackViewListener(pListener);
    }

    public ClientPlayerRackView getClientPlayerRackView() {
        Assert.assertTrue(mHasClientPlayerRackView);
        return mClientPlayerRackView;
    }

    public boolean clientPlayerRackViewHasSelection() {
        return mHasClientPlayerRackView &&
            mClientPlayerRackView.getSelectedColumnCount() > 0;
    }

    // Methods: for setting mock state

    public void setClientPlayerRackViewSelectedColumns(
        int[] pSelectedColumns) {
        mHasClientPlayerRackView = true;
        mClientPlayerRackView.setSelectedColumns(pSelectedColumns);
    }

}
