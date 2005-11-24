package org.schweisguth.xttest.common.gameimpl.joining;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.joining.StartedEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class JoiningStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        assertIsSerializable(new JoiningState(new String[] { "player1" }));
    }

    public void testCreateNoPlayers() {
        Game game = new GameImpl(new JoiningState());
        CanExecuteTester observerTester = new CanExecuteTester();
        observerTester.addTrue(new JoinCommand());
        observerTester.doAssert(game, "observer");
    }

    public void testCreateOnePlayer() {
        Game game = new GameImpl(new JoiningState(new String[] { "player1" }));
        new CanExecuteTester().doAssert(game, "player1");
        CanExecuteTester observerTester = new CanExecuteTester();
        observerTester.addTrue(new JoinCommand());
        observerTester.doAssert(game, "observer");
    }

    public void testCreateTwoPlayers() {
        Game game = new GameImpl(new JoiningState(TWO_PLAYERS));
        CanExecuteTester tester12 = new CanExecuteTester();
        tester12.addTrue(new StartCommand());
        tester12.doAssert(game, "player1");
        tester12.doAssert(game, "player2");
        CanExecuteTester observerTester = new CanExecuteTester();
        observerTester.addTrue(new JoinCommand());
        observerTester.doAssert(game, "observer");
    }

    public void testJoin1() {
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

    public void testJoin2() {
        ListenableGame game =
            new GameImpl(new JoiningState(new String[] { "player1" }));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new JoinCommand();
        client2.execute(command);

        Game expectedGame = new GameImpl(new JoiningState(TWO_PLAYERS));
        assertEquals(expectedGame, game);

        Event event =
            new JoinedEvent(expectedGame, new Request("player2", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    // TODO replace these tests with canExecute tests and one test that execute
    // asserts canExecute
    public void testJoinTwice() {
        JoiningState state = new JoiningState(new String[] { "player1" });
        assertFalse(state.canExecute("player1", new JoinCommand()));
    }

    public void testJoinMaxPlusOne() {
        JoiningState state = new JoiningState(
            new String[] { "player1", "player2", "player3", "player4" });
        assertFalse(state.canExecute("player5", new JoinCommand()));
    }

    public void testStart() {
        ListenableGame game = new GameImpl(new JoiningState(TWO_PLAYERS));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new StartCommand();
        client1.execute(command);

        Game expectedGame =
            new GameImpl(new DrawingForFirstState(TWO_PLAYERS));
        assertEquals(expectedGame, game);

        Event event = new StartedEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testStartEarly() {
        JoiningState state = new JoiningState(new String[] { "player1" });
        assertFalse(state.canExecute("player1", new StartCommand()));
    }

}
