package org.schweisguth.xttest.common.gameimpl.stateimpl;

import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.stateimpl.ChatEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.BaseGameStateTest;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;

public class StateImplTest extends BaseGameStateTest {
    public void testChat() {
        // Set up
        ListenableGame game = new GameImpl(new JoiningState());
        TestClient client1 = new TestClient(game, "player1");
        Command command = new ChatCommand("x");
        client1.execute(command);

        // Test game state
        Game expectedGame = new GameImpl(new JoiningState());
        assertEquals(expectedGame, game);

        // Test events
        Event event =
            new ChatEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }
}
