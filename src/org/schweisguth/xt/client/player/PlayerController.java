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
    private PlayerListener mListener;

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
        model.setIsTurn(pGame.hasCurrentPlayer() &&
            pGame.getCurrentPlayer().equals(model.getPlayer()));
        mView.setModel(model);
        addListener();
    }

    private void addListener() {
        mListener = new PlayerListener();
        mClient.addListener(mListener);
    }

    private class PlayerListener implements Listener {
        public void send(Event pEvent) {
            Game game = pEvent.getGame();
            PlayerModel model = mView.getModel();
            model.setIsTurn(game.hasCurrentPlayer() &&
                game.getCurrentPlayer().equals(model.getPlayer()));
        }
    }

}
