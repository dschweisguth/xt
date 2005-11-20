package org.schweisguth.xt.client;

import java.awt.Frame;
import org.schweisguth.xt.client.boxlid.BoxLidController;
import org.schweisguth.xt.client.chat.display.ChatDisplayController;
import org.schweisguth.xt.client.chat.send.ChatSendController;
import org.schweisguth.xt.client.score.ScoreController;
import org.schweisguth.xt.client.seating.SeatingController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.toolbar.ToolBarController;
import org.schweisguth.xt.common.util.contract.Assert;

// TODO undo

class ClientController {
    // Fields
    private final ClientView mView;

    // Constructors

    public ClientController(Client pClient) {
        Assert.assertNotNull(pClient);

        SeatingController seatingController = new SeatingController(pClient);
        ToolBarController toolBarController = new ToolBarController(
            pClient, seatingController.getClientPlayerSeatController());
        BoxLidController boxLidController = new BoxLidController(pClient);
        ScoreController scoreController = new ScoreController(pClient);
        ChatDisplayController chatDisplayController =
            new ChatDisplayController(pClient);
        ChatSendController chatSendController =
            new ChatSendController(pClient);
        mView = new ClientView(toolBarController.getView(),
            seatingController.getView(), boxLidController.getView(),
            scoreController.getView(), chatDisplayController.getView(),
            chatSendController.getView(), new ExitAction(pClient));

    }

    // Methods: other

    public Frame getView() {
        return mView;
    }

}
