package org.schweisguth.xt.client.rackimpl;

import org.schweisguth.xt.client.server.Client;

public class OtherPlayerRackController extends PlayerRackController {
    public OtherPlayerRackController(Client pClient, String pPlayer) {
        super(new OtherPlayerRackView(new OtherPlayerRackModel()), pClient,
            pPlayer);
    }
}
