package org.schweisguth.xt.client.boardimpl;

import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.event.ListSelectionEvent;
import org.schweisguth.xt.client.board.BoardView;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.BaseTableColumnModelListener;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.command.TransferAnythingCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.util.contract.Assert;

public class BoardController {
    // Fields
    private final BoardViewImpl mView = new BoardViewImpl();
    private final Client mClient;

    // Constructors

    public BoardController(Client pClient) {
        Assert.assertNotNull(pClient);

        mClient = pClient;
        pClient.addListener(new BoardListener());

    }

    private class BoardListener implements Listener {
        public void send(Event pEvent) {
            Game game = pEvent.getGame();
            mView.getBoardModel().setBoard(game.getBoard());
            // Set board selectability -- doesn't stop selection changes, but
            // does reduce the changes in appearance. ?!?
            // The following check works for rearranging the board, too.
            mView.setCellSelectionEnabled(game.canExecute(new Request(
                mClient.getPlayer(),
                new TransferAnythingCommand())));
        }
    }

    // Methods

    public Component getView() {
        return (Component) getBoardView();
    }

    public BoardView getBoardView() {
        return mView;
    }

    public void setSeatController(SeatController pSeatController) {
        Assert.assertNotNull(pSeatController);
        mView.addBoardViewListener(new BoardViewListenerImpl(pSeatController));
    }

    private class BoardViewListenerImpl extends BaseTableColumnModelListener
        implements BoardViewListener {
        // Fields
        private final SeatController mSeatController;
        private boolean mHandlingEvent = false;
        private boolean mSecondEventExpected = false;
        private Position mPreviousSelection = null;
        private Position mLastBoardSelection = null; // TODO redundant?

        // Constructors

        private BoardViewListenerImpl(SeatController pSeatController) {
            Assert.assertNotNull(pSeatController);
            mSeatController = pSeatController;
        }

        // Methods: overrides BaseTableColumnModelListener

        public void columnSelectionChanged(ListSelectionEvent pEvent) {
            //System.err.println("columnSelectionChanged " +
            //   "row selection: " + mView.getSelectionModel().isSelectionEmpty() + " " +
            //   "col selection: " + mView.getColumnModel().isSelectionEmpty());
            handleOnce(pEvent);
        }

        // Methods: implements ListSelectionListener

        public void valueChanged(ListSelectionEvent pEvent) {
            //System.err.println("valueChanged " +
            //   "row selection: " + mView.getSelectionModel().isSelectionEmpty() + " " +
            //   "col selection: " + mView.getColumnModel().isSelectionEmpty());
            handleOnce(pEvent);
        }

        // TODO refactor into a state machine
        // TODO suppress display of focus?
        // TODO figure out why I can't clear rack at the bottom, and (if I can
        // after all) clear the board at the bottom of the rack method).
        private void handleOnce(ListSelectionEvent pEvent) {
            if (pEvent.getValueIsAdjusting()) {
                //System.err.println("value is adjusting");
                return;
            }
            //System.err.println("entering handleOnce: " +
            //   "mSecondEventExpected: " + mSecondEventExpected + " " +
            //   "mPreviousSelection: " + mPreviousSelection + " " +
            //   "mHandlingEvent: " + mHandlingEvent);
            try {
                if (mHandlingEvent) {
                    return;
                }
                if (mSecondEventExpected) {
                    mSecondEventExpected = false;
                    return;
                }
                if (mView.selectionIsEmpty()) {
                    mSecondEventExpected = mPreviousSelection != null;
                } else {
                    Position boardSelection = mView.getSelection();
                    // TODO move to Position
                    mSecondEventExpected = mPreviousSelection == null ||
                        mPreviousSelection.getX() != boardSelection.getX() &&
                            mPreviousSelection.getY() != boardSelection.getY();
                }
                mHandlingEvent = true;
                handleSelection();
                mHandlingEvent = false;
                mPreviousSelection =
                    mView.selectionIsEmpty() ? null : mView.getSelection();
            } finally {
                //System.err.println("exiting handleOnce: " +
                //   "mSecondEventExpected: " + mSecondEventExpected + " " +
                //   "mPreviousSelection: " + mPreviousSelection + " " +
                //   "mHandlingEvent: " + mHandlingEvent);
            }
        }

        private void handleSelection() {
            // Selecting a cell calls this method once if only the row or
            // column selection changes, but calls it twice if both change. The
            // second time it's called it either does nothing (if a tile was
            // moved and the selection was cleared) or updates or clears the
            // last selection a second time, which is harmless.
            if (mView.selectionIsEmpty()) {
                clearBoardSelection();
            } else {
                try {
                    // If the client player isn't in the game, there won't be a
                    // client player rack view, so bail out.
                    if (! mClient.getPlayers().contains(mClient.getPlayer())) {
                        return;
                    }
                    Position boardSelection = mView.getSelection();
                    ClientPlayerRackView rackView =
                        mSeatController.getClientPlayerRackView();
                    int rackSelection = rackView.getSelectedColumn();
                    if (mLastBoardSelection != null &&
                        ! mLastBoardSelection.equals(boardSelection)) {
                        RearrangeBoardCommand command = new RearrangeBoardCommand(
                            mLastBoardSelection, boardSelection);
                        if (mClient.canExecute(command)) {
                            mClient.execute(command);
                        }
                        clearBoardSelection();
                    } else if (rackView.getSelectedColumnCount() == 1) {
                        if (mClient.canExecute(new TransferAnythingCommand()))
                        {
                            TransferCommand command = new TransferCommand(
                                new Transfer(rackSelection, boardSelection));
                            if (mClient.canExecute(command)) {
                                mClient.execute(command);
                                clearBoardSelection();
                            } else {
                                mLastBoardSelection = boardSelection;
                            }
                        } else {
                            clearBoardSelection();
                        }
                        rackView.clearSelection();
                    } else if (rackView.getSelectedColumnCount() > 1) {
                        rackView.clearSelection();
                    } else if (mView.getBoardModel().getBoard().
                        hasUnapprovedTile(boardSelection)) {
                        mLastBoardSelection = boardSelection;
                    } else {
                        clearBoardSelection();
                    }
                } catch (RemoteException e) {
                    new ErrorDialog("Couldn't contact game server", e).show();
                }
            }
        }

        private void clearBoardSelection() {
            mView.clearSelection();
            mLastBoardSelection = null;
        }

    }

}
