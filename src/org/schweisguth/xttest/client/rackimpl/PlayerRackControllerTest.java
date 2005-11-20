package org.schweisguth.xttest.client.rackimpl;

import org.schweisguth.xt.client.rackimpl.PlayerRackController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public abstract class PlayerRackControllerTest extends BaseTest {
    // When a player becomes a non-player (as when a new game is started), a
    // PlayerRackController may receive the event (whose Game doesn't have its
    // player or Rack) before it is disposed of. In this case the correct
    // behavior is to not try to update the rack.
    public void testStartNewGame() {
        EndedState state = new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        LocalClient client = new LocalClient(new GameImpl(state), "player1");
        PlayerRackController controller = getPlayerRackController(client);
        Rack startingRack = controller.getRack();
        client.execute(new StartNewGameCommand());
        assertEquals(startingRack, controller.getRack());
    }

    protected abstract PlayerRackController
        getPlayerRackController(Client pClient);

}
