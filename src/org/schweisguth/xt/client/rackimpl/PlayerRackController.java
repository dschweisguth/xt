package org.schweisguth.xt.client.rackimpl;

import java.awt.Component;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.domain.Player;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.util.contract.Assert;

public abstract class PlayerRackController implements RackController {
    // Fields
    private final PlayerRackView mView;
    private final PlayerRackListener mListener;

    // Constructors

    protected PlayerRackController(PlayerRackView pView, Client pClient,
        String pPlayer) {
        Assert.assertNotNull(pView);
        Assert.assertNotNull(pClient);
        Player.assertIsValid(pPlayer);

        mView = pView;
        mListener = new PlayerRackListener(pClient, pPlayer);

    }

    private class PlayerRackListener implements Listener {
        private final Client mClient;
        private final String mPlayer;

        private PlayerRackListener(Client pClient, String pPlayer) {
            Assert.assertNotNull(pClient);

            mClient = pClient;
            pClient.addListener(this);
            mPlayer = pPlayer;

        }

        public void send(Event pEvent) {
            Game game = pEvent.getGame();
            if (game.getPlayers().contains(mPlayer)) {
                mView.getPlayerRackModel().setRack(game.getRack(mPlayer));
            }
        }

        private void dispose() {
            mClient.removeListener(this);
        }

    }

    // Methods: implements RackController

    public Component getView() {
        return (Component) mView;
    }

    public void dispose() {
        mListener.dispose();
    }

    // Methods: other

    public Rack getRack() {
        return mView.getPlayerRackModel().getRack();
    }

    public void setRack(Rack pRack) {
        mView.getPlayerRackModel().setRack(pRack);
    }

}
