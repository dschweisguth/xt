package org.schweisguth.xttest.common.gameimpl.stateimpl;

import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.stateimpl.ChatEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class StateImplTest extends BaseTest {
    // TODO separate tests of GameImpl from tests of StateImpl

    public void testExecute() {
        ListenableGame game = new GameImpl(new JoiningState());
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new JoinCommand();
        client1.execute(command);

        Game expectedGame =
            new GameImpl(new JoiningState(new String[] { "player1" }));
        assertEquals(expectedGame, game);

        Event event =
            new JoinedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());
    }

    public void testExecuteCanExecuteFalse() {
        try {
            ListenableGame game = new GameImpl(new JoiningState(new String[] { "player1" }));
            LocalClient client = new LocalClient(game, "player1");
            client.execute(new JoinCommand());
        } catch (AssertionFailedError e) {
            return;
        }
        fail();
    }

    public void testExecuteCanExecuteWrongState() {
        try {
            ListenableGame game = new GameImpl(new JoiningState());
            LocalClient client = new LocalClient(game, "player1");
            client.execute(new EndGameCommand());
        } catch (AssertionFailedError e) {
            return;
        }
        fail();
    }

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
