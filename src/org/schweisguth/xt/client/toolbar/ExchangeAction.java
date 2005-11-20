package org.schweisguth.xt.client.toolbar;

import java.rmi.RemoteException;
import javax.swing.event.ListSelectionEvent;
import org.schweisguth.xt.client.action.BaseDisableableAction;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.BaseTableColumnModelListener;
import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.util.contract.Assert;

public class ExchangeAction extends BaseDisableableAction {
    // Fields
    private final SeatController mSeatController;

    // Constructors

    public ExchangeAction(Client pClient, SeatController pSeatController) {
        super("Exchange tiles", pClient);
        Assert.assertNotNull(pSeatController);

        mSeatController = pSeatController;
        pSeatController.addClientPlayerRackListener(
            new ExchangeActionClientPlayerRackListener());

    }

    private class ExchangeActionClientPlayerRackListener
        extends BaseTableColumnModelListener {
        public void columnSelectionChanged(ListSelectionEvent pEvent) {
            if (! pEvent.getValueIsAdjusting()) {
                updateEnabled();
            }
        }
    }

    // Methods

    protected boolean shouldBeEnabled() throws RemoteException {
        return mSeatController.clientPlayerRackViewHasSelection() &&
            getClient().canExecute(new ExchangeCommand(
                mSeatController.getClientPlayerRackView().getSelectedColumns()));
    }

    public void execute() throws RemoteException {
        getClient().execute(new ExchangeCommand(
            mSeatController.getClientPlayerRackView().getSelectedColumns()));
    }

}
