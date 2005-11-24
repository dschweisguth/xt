package org.schweisguth.xttest.common.gameimpl.ended;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.ended.StartedNewGameEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class EndedStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        assertIsSerializable(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
    }

    public void testCreate() {
        EndedState state = new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        CanExecuteTester tester = new CanExecuteTester();
        tester.addTrue(new StartNewGameCommand());
        tester.doAssert(state, "player1");
        tester.doAssert(state, "player2");
        tester.doAssert(state, "observer");
    }

    public void testStartNewGame() {
        ListenableGame game =
            new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new StartNewGameCommand();
        client1.execute(command);

        Game expectedGame = new GameImpl(new JoiningState());
        assertEquals(expectedGame, game);

        Event event = new StartedNewGameEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }
}
