package org.schweisguth.xttest.client.rackimpl;

import java.util.Arrays;
import org.schweisguth.xt.client.boardimpl.BoardViewImpl;
import org.schweisguth.xt.client.rackimpl.ClientPlayerRackController;
import org.schweisguth.xt.client.rackimpl.PlayerRackController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;

public class ClientPlayerRackControllerTest extends PlayerRackControllerTest {
    protected PlayerRackController getPlayerRackController(Client pClient) {
        return new ClientPlayerRackController(pClient, new BoardViewImpl());
    }

    public void testEventWithUnchangedRack() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        LocalClient client = new LocalClient(new GameImpl(state), "player1");
        ClientPlayerRackController controller =
            new ClientPlayerRackController(client, new BoardViewImpl());
        client.sendRefreshEvent();
        assertEquals(0, controller.getSelectedColumns().length);
        controller.selectAll();
        assertTrue(
            Arrays.equals(getFullSelection(), controller.getSelectedColumns()));
        client.execute(new ChatCommand(""));
        assertTrue(
            Arrays.equals(getFullSelection(), controller.getSelectedColumns()));
    }

    private static int[] getFullSelection() {
        int[] selection = new int[Rack.MAX_TILE_COUNT];
        for (int column = 0; column < selection.length; column++) {
            selection[column] = column;
        }
        return selection;
    }

}
