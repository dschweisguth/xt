package org.schweisguth.xt.client.seatimpl;

import java.awt.Component;
import javax.swing.event.TableColumnModelListener;
import org.schweisguth.xt.client.board.BoardView;
import org.schweisguth.xt.client.player.PlayerController;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;
import org.schweisguth.xt.client.rackimpl.ClientPlayerRackController;
import org.schweisguth.xt.client.rackimpl.NoPlayerRackController;
import org.schweisguth.xt.client.rackimpl.OtherPlayerRackController;
import org.schweisguth.xt.client.rackimpl.PlayerRackController;
import org.schweisguth.xt.client.rackimpl.RackController;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.util.contract.Assert;

public class SeatControllerImpl implements SeatController {
    // Fields
    private final Client mClient;
    private final BoardView mBoardView;
    private final PlayerController mPlayerController;
    private RackController mRackController;
    private final SeatView mView;
    private TableColumnModelListener mClientPlayerRackListener = null;

    // Constructors

    public SeatControllerImpl(Client pClient, BoardView pBoardView) {
        Assert.assertNotNull(pClient);
        Assert.assertNotNull(pBoardView);

        mClient = pClient;
        mBoardView = pBoardView;
        mPlayerController = new PlayerController(pClient);
        mRackController = new NoPlayerRackController();
        mView =
            new SeatView(mPlayerController.getView(), mRackController.getView());

    }

    // Methods: implements SeatController

    public void addClientPlayerRackListener(TableColumnModelListener pListener) {
        Assert.assertNotNull(pListener);
        mClientPlayerRackListener = pListener;
    }

    public ClientPlayerRackView getClientPlayerRackView() {
        return (ClientPlayerRackView) mRackController.getView();
    }

    public boolean clientPlayerRackViewHasSelection() {
        return mRackController.getView() instanceof ClientPlayerRackView &&
            getClientPlayerRackView().getSelectedColumnCount() > 0;
    }

    // Methods: other

    public Component getView() {
        return mView;
    }

    public String getPlayer() {
        return mPlayerController.getPlayer();
    }

    public RackController getRackController() {
        return mRackController;
    }

    public void setPlayer(String pPlayer, Game pGame) {
        Assert.assertNotNull(pPlayer);

        mPlayerController.setPlayer(pPlayer);
        if (mClient.playerIs(pPlayer)) {
            if (! (mRackController instanceof ClientPlayerRackController)) {
                setRackController(
                    new ClientPlayerRackController(mClient, mBoardView));
                setRack(pPlayer, pGame);
                getClientPlayerRackView().addRackViewListener(
                    mClientPlayerRackListener);
            }
        } else if (pPlayer.equals("")) {
            if (! (mRackController instanceof NoPlayerRackController)) {
                setRackController(new NoPlayerRackController());
            }
        } else {
            setRackController(new OtherPlayerRackController(mClient, pPlayer));
            setRack(pPlayer, pGame);
        }

    }

    private void setRackController(RackController pRackController) {
        mRackController.dispose();
        mRackController = pRackController;
        mView.setRackView(mRackController.getView());
    }

    private void setRack(String pPlayer, Game pGame) {
        ((PlayerRackController) mRackController).setRack(pGame.getRack(pPlayer));
    }

}
