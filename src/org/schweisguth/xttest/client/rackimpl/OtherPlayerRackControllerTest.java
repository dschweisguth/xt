package org.schweisguth.xttest.client.rackimpl;

import org.schweisguth.xt.client.rackimpl.OtherPlayerRackController;
import org.schweisguth.xt.client.rackimpl.PlayerRackController;
import org.schweisguth.xt.client.server.Client;

public class OtherPlayerRackControllerTest extends PlayerRackControllerTest {
    protected PlayerRackController getPlayerRackController(Client pClient) {
        return new OtherPlayerRackController(pClient, "player2");
    }
}
