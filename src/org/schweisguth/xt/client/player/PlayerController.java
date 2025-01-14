package org.schweisguth.xt.client.player;

import java.awt.Component;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;

public class PlayerController {
    // Fields
    private final Client mClient;
    private final PlayerView mView;
    private PlayerListener mListener = new PlayerListener();

    // Constructors

    public PlayerController(Client pClient) {
        mClient = pClient;
        mView = new PlayerView(new PlayerModel());
        addListener();
    }

    // Methods

    public Component getView() {
        return mView;
    }

    public String getPlayer() {
        return mView.getModel().getPlayer();
    }

    public void setPlayer(String pPlayer, Game pGame) {
        mClient.removeListener(mListener);
        PlayerModel model = new PlayerModel(pPlayer);
        mView.setModel(model);
        updateIsTurn(model, pGame);
        addListener();
    }

    private void addListener() {
        mClient.addListener(mListener);
    }

    private class PlayerListener implements Listener {
        public void send(Event pEvent) {
            updateIsTurn(mView.getModel(), pEvent.getGame());
        }

    }

    private static void updateIsTurn(PlayerModel pModel, Game pGame) {
        pModel.setIsTurn(pGame.hasCurrentPlayer() &&
            pGame.getCurrentPlayer().equals(pModel.getPlayer()));
    }

}
