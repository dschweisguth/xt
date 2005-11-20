package org.schweisguth.xt.client.boxlid;

import java.awt.Component;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.CardListener;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.util.contract.Assert;

public class BoxLidController {
    // Fields
    private final BoxLidView mView;

    // Constructors

    public BoxLidController(Client pClient) {
        Assert.assertNotNull(pClient);

        BoxLidModel model = new BoxLidModel(BoxLid.getMaxTileCount());
        pClient.addListener(new BoxLidModelListener(model));
        mView = new BoxLidView(
            model,
            new CommandAction(pClient, new DrawForFirstCommand()),
            new CommandAction(pClient, new DrawStartingTilesCommand()),
            new CommandAction(pClient, new DrawNewTilesCommand()));
        pClient.addListener(new CardListener(mView, new String[]
            {
                BoxLidView.NO_BUTTON,
                BoxLidView.DRAW_FOR_FIRST,
                BoxLidView.DRAW_STARTING_TILES,
                BoxLidView.DRAW_NEW_TILES,
                BoxLidView.DRAW_NEW_TILES,
                BoxLidView.DRAW_NEW_TILES,
                BoxLidView.DRAW_NEW_TILES,
                BoxLidView.NO_BUTTON
            }));

    }

    private static class BoxLidModelListener implements Listener {
        private final BoxLidModel mModel;

        private BoxLidModelListener(BoxLidModel pModel) {
            Assert.assertNotNull(pModel);
            mModel = pModel;
        }

        public void send(Event pEvent) {
            mModel.setTileCount(pEvent.getGame().getBoxLid().getTileCount());
        }

    }

    // Methods

    public Component getView() {
        return mView;
    }

    public BoxLidView getBoxLidView() {
        return mView;
    }

}
