package org.schweisguth.xt.client.rackimpl;

import java.rmi.RemoteException;
import javax.swing.event.ListSelectionEvent;
import org.schweisguth.xt.client.board.BoardView;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.BaseTableColumnModelListener;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.command.TakeBackCommand;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.util.contract.Assert;

public class ClientPlayerRackController extends PlayerRackController {
    // Constructors

    public ClientPlayerRackController(Client pClient, BoardView pBoardView) {
        super(createView(pClient, pBoardView), pClient, pClient.getPlayer());
    }

    private static ClientPlayerRackViewImpl createView(Client pClient,
        BoardView pBoardView) {
        ClientPlayerRackViewImpl view =
            new ClientPlayerRackViewImpl(new ClientPlayerRackModel());
        view.addRackViewListener(
            new ClientPlayerRackViewListener(pClient, view, pBoardView));
        return view;
    }

    private static class ClientPlayerRackViewListener
        extends BaseTableColumnModelListener {
        private final Client mClient;
        private final ClientPlayerRackViewImpl mView;
        private final BoardView mBoardView;
        private int mLastRackSelection = -1;

        private ClientPlayerRackViewListener(Client pClient,
            ClientPlayerRackViewImpl pView, BoardView pBoardView) {
            Assert.assertNotNull(pClient);
            Assert.assertNotNull(pView);
            Assert.assertNotNull(pBoardView);
            mClient = pClient;
            mView = pView;
            mBoardView = pBoardView;
        }

        // TODO refactor into a state machine
        public void columnSelectionChanged(ListSelectionEvent pEvent) {
            if (pEvent.getValueIsAdjusting()) {
                return;
            }
            int rackSelection = mView.getSelectedColumn();
            try {
                if (rackSelection == -1) {
                    clearRackSelection();
                } else if (mView.getSelectedColumnCount() > 1) {
                    // User is extending the selection. Clear the last selection so
                    // that a subsequent single selection won't result in a
                    // rearrangement.
                    mLastRackSelection = -1;
                } else if (mLastRackSelection != -1) {
                    mClient.execute(
                        new RearrangeRackCommand(mLastRackSelection, rackSelection));
                    clearRackSelection();
                } else if (! mBoardView.selectionIsEmpty()) {
                    if (occupied(rackSelection)) {
                        mLastRackSelection = rackSelection;
                        mBoardView.clearSelection();
                    } else {
                        TakeBackCommand command = new TakeBackCommand(
                            new Transfer(rackSelection, mBoardView.getSelection()));
                        if (mClient.canExecute(command)) {
                            mClient.execute(command);
                            clearRackSelection();
                        } else {
                            selectIfOccupied(rackSelection);
                        }
                    }
                } else {
                    selectIfOccupied(rackSelection);
                }
            }
            catch (RemoteException e) {
                new ErrorDialog("Couldn't contact game server", e).show();
            }
        }

        private void clearRackSelection() {
            mView.clearSelection();
            mLastRackSelection = -1;
        }

        private boolean occupied(int pRackSelection) {
            return mView.getPlayerRackModel().getRack().contains(pRackSelection);
        }

        private void selectIfOccupied(int pRackSelection) {
            if (occupied(pRackSelection)) {
                mLastRackSelection = pRackSelection;
            } else {
                clearRackSelection();
            }
        }

    }

    public int[] getSelectedColumns() {
        return ((ClientPlayerRackViewImpl) getView()).getSelectedColumns();
    }

    public void selectAll() {
        ((ClientPlayerRackViewImpl) getView()).selectAll();
    }

}
