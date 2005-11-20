package org.schweisguth.xt.client.score;

import java.awt.Component;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Listener;

public class ScoreController {
    private final ScoreView mView = new ScoreView();

    public ScoreController(Client pClient) {
        pClient.addListener(new ScoreListener());
    }

    private class ScoreListener implements Listener {
        public void send(Event pEvent) {
            mView.getScoreModel().setScoreSheet(pEvent.getGame().getScoreSheet());
        }
    }

    public Component getView() {
        return mView;
    }

}
