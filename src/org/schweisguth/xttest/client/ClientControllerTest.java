package org.schweisguth.xttest.client;

import junit.framework.TestCase;
import org.schweisguth.xt.client.ClientController;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;

public class ClientControllerTest extends TestCase {
    public void testCreate() {
        ClientController controller =
            new ClientController(new LocalClient(new GameImpl(), "player1"));
        assertNotNull(controller.getView());
    }
}
